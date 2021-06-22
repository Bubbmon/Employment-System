<template>
    <div>
        <HeaderC></HeaderC>
        <div class="resume-pane">
            <div class="my-resume">
                <h2>我的简历</h2>
                <input type="file" id="newResume" name="newResume" accept=".pdf" multiple="false" style="margin-top:30px;">
                <p style="font-size:.8rem;margin-top:10px">仅限pdf</p>
                <button style="display:block" @click="upload">上传我的简历</button>
                <!-- <div>{{this.resumeTitle}}</div> -->
                <button style="display:block" @click="download">下载我的简历</button>
                <!-- <img :src="resumeStream" class="user-avatar" style="height:500px;width:800px"> -->
            </div>
            <div class="resume-list" style="margin-top:30px">
                <h2 style="margin-bottom:30px">简历投递记录</h2>
                <el-table :data="tableData" stripe style="width: 100%">
                    <el-table-column prop="positionId" label="招聘职位id" width="180">
                    </el-table-column>
                    <el-table-column prop="enterpriseName" label="公司名称" width="180">
                    </el-table-column>
                    <el-table-column prop="positionTitle" label="招聘信息" width="180">
                    </el-table-column>
                    <el-table-column prop="isDealed" label="处理状态" width="180">
                    </el-table-column>
                    <el-table-column prop="download" label="下载">
                        <template slot-scope="scope">
                        <el-button size="mini" @click="handleDownload(scope.$index, scope.row)">下载</el-button>
                    </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
import HeaderC from "../../components/Consumer/HeaderC.vue"
export default {
    components: {
        HeaderC
    },
    data() {
        return {
            tableData: [],
            newResume: null,
            resumeStream: null,
            resumeTitle: null
        }
    },
    mounted() {
        this.getData()
    },
    methods: {
        async getData() {
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/send/recruiter/all",
                headers: {
                    token: this.$store.state.token.value,
                }
            }).then(res => {
                this.tableData = res.data.map(item => {
                    let obj = {};
                    obj['positionId'] = item.positionId;
                    obj['enterpriseName'] = item.enterpriseName;
                    obj['positionTitle'] = item.positionTitle;
                    if (item.dealed == true) {
                        obj['isDealed'] = "已处理";
                    } else {
                        obj['isDealed'] = "未处理";
                    }
                    obj['download'] = '下载';
                    return obj;
                })
            })
        },
        upload() {
            const input = document.querySelector("input");
            this.newResume = input.files[0];
            let fd = new FormData();
            fd.append("resume", this.newResume);
            console.log(this.newResume);
            // console.log(fd.get("file"));
            console.log(this.$store.state.token)
            //将简历上传
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/account/resume",
                headers: {
                    "Content-Type": "multipart/form-data;boundary=ebf9f03029db4c2799ae16b5428b06bd",
                    "token": this.$store.state.token.value,
                    // "resume": this.newResume
                },

                data: fd
            }).then(res => {
                var resumeResult = res.data.resumeResult;
                if (resumeResult == 0) {
                    window.alert("上传成功！");
                } else if (resumeResult == 1) {
                    window.alert("上传失败");
                }
            })
        },
        async download() {
            // const{data:ret} = await this.$http.get("http://1.117.44.227:8088/employment/account/resume");
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/account/resume",
                headers: {
                    token: this.$store.state.token.value,
                },
                responseType: "blob"
            }).
                then(res => {
                    // console.log(decodeURI(res.headers['content-disposition']));
                    this.resumeTitle = decodeURI(res.headers['content-disposition']).substring(20);
                    this.resumeStream = res.data;
                    
                    let blob = new Blob([res.data], {
                        type: 'application/pdf'
                    })
                    let fileName = decodeURI(this.resumeTitle);
                    if (window.navigator.msSaveOrOpenBlob) {
                        // console.log(2)
                        navigator.msSaveBlob(blob, fileName)
                    } else {
                        // console.log(3)
                        var link = document.createElement('a')
                        link.href = window.URL.createObjectURL(blob)
                        link.download = fileName
                        link.click()
                        //释放内存
                        window.URL.revokeObjectURL(link.href)
                    }
                })
            //TODO：返回会有一个stream？
        },
        handleDownload(index, row){
            console.log(row);
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/send/recruiter/download",
                headers: {
                    token: this.$store.state.token.value,
                    positionId:row.positionId
                },
                responseType: "blob"
            }).
                then(res => {
                    // this.resumeTitle = URLDecoder.decode(res.headers['content-disposition'].substring(20), "utf-8");
                    this.resumeTitle = decodeURI(res.headers['content-disposition'].substring(20));
                    this.resumeTitle = decodeURI(this.resumeTitle);
                    let blob = new Blob([res.data], {
                        type: 'application/pdf'
                    })
                    let fileName = this.resumeTitle;
                    if (window.navigator.msSaveOrOpenBlob) {
                        navigator.msSaveBlob(blob, fileName)
                    } else {
                        var link = document.createElement('a')
                        link.href = window.URL.createObjectURL(blob)
                        link.download = fileName
                        link.click()
                        //释放内存
                        window.URL.revokeObjectURL(link.href)
                    }
                })

        }

    }
}
</script>
<style scoped>
.resume-pane {
    width: 75%;
    margin: 0 auto;
    margin-top: 50px;
}
button {
    margin-top: 15px;
    width: 100px;
    height: 40px;
    border: none;
    background-color: black;
    color: white;
    cursor: pointer;
}
button:hover{
    background-color: black;
    color: white;
}
</style>