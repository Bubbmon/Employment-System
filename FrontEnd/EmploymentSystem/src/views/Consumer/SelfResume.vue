<template>
    <div>
        <HeaderC></HeaderC>
        <div class="resume-pane">
            <div class="my-resume">
                <h2>我的简历</h2>
                <input type="file" id="newResume" name="newResume" accept=".jpg, .png, .pdf" multiple="false" style="margin-top:30px;">
                <button style="display:block" @click="upload">上传我的简历</button>
                <button style="display:block" @click="download">下载我的简历</button>
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
            newResume:null
        }
    },
    methods: {
        async getData() {
            var { data: res } = await this.$http.get('http://1.117.44.227:8088/employment/send/recruiter/all' + '?token=' + this.$store.state.token);
            this.tableData = res;
            //TODO：数据类型转换，对于每一个对象加一个下载字段，内容是对应的下载链接
            res = res.map(item => {
                let obj = {};
                obj['positionId'] = item.positionId;
                obj['enterpriseName'] = item.enterpriseName;
                obj['positionTitle'] = item.positionTitle;
                obj['isDealed'] = item.isDealed;
                //TODO:更新URL
                obj['download'] = '下载';
                return obj;
            })
        },
        upload() {
            const input = document.querySelector("input");
            this.newResume = input.files[0];
            console.log(this.newResume);
            console.log(this.$store.state.token)
            //将简历上传
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/account/resume"+"?resume=" + this.newResume,
                headers: {
                    "token": this.$store.state.token.value,
                    // "resume": this.newResume
                }
            }).then(res => {
                var resumeResult = res.data.resumeResult;
                if (resumeResult == 0) {
                    window.alert("上传成功！");
                } else if (resumeResult == 1) {
                    window.alert("上传失败");
                }
            })
        },
        async download(){
            const{data:ret} = await this.$http.get("/employment/account/resume" + "?token=" + this.$store.state.token);
            console.log(ret);
            //TODO：返回会有一个stream？
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
</style>