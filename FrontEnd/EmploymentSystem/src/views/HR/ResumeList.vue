<template>
    <div class="whole">
        <HeaderHR></HeaderHR>
        <div style="width:75%; margin:0 auto;">
            <el-table :data="resumeList" style="width: 100%">
                <el-table-column label="投递者ID" width="180">
                    <template slot-scope="scope">
                        <!-- <i class="el-icon-time"></i> -->
                        <span style="margin-left: 10px">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="投递者姓名" width="180">
                    <template slot-scope="scope">
                        <el-popover trigger="hover" placement="top">
                            <p>姓名: {{ scope.row.name }}</p>
                            <p>联系方式: {{ scope.row.phone }}</p>
                            <div slot="reference" class="name-wrapper">
                                <el-tag size="medium">{{ scope.row.name }}</el-tag>
                            </div>
                        </el-popover>
                    </template>
                </el-table-column>
                <el-table-column label="状态" width="180">
                    <template slot-scope="scope">
                        <el-tag size="medium">{{ scope.row.dealed }}</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="下载">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleDownload(scope.$index, scope.row)">下载</el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑状态</el-button>
                        <!-- <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script>
import HeaderHR from '../../components/HR/HeaderHR.vue';
export default {
    components: {
        HeaderHR,

    },
    data() {
        return {
            positionId: decodeURI(this.$route.query.positionId),
            resumeList: [],
        }
    },
    mounted() {
        this.getData();
    },
    methods: {
        async getData() {
            // const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/send/hr/" + this.positionId + "?token=" + this.$store.state.token);
            // this.resumeList = ret;
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/send/hr/" + this.positionId,
                headers: {
                    token: this.$store.state.token.value,
                }
            }).then(res => {
                this.resumeList = res.data.map(item => {
                    if (item.isDealed == true) {
                        item['isDealed'] = true;
                    } else {
                        item['isDealed'] = false;
                    }
                    return item;
                })
            })
        },
        handleEdit(index, row) {
            // console.log(index, row);
            // var isDealed2 = false;
            // if (row.isDealed == false) {
            //     isDealed2 = true;
            // }
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/send/hr/deal/" + this.positionId + "/" + row.id,
                headers: {
                    token: this.$store.state.token.value,
                    isDealed:!row.dealed
                }
            }).then(res => {
                var dealResult = res.data.dealResult;
                if (dealResult == 0) {
                    window.alert("修改成功");
                    row.dealed = !row.dealed;
                } else {
                    window.alert("修改失败");
                }
            })

            // if (row.isDealed == true) {
            //     row.isDealed = false;
            // } else {
            //     row.isDealed = true;
            // }
        },
        async handleDownload(index, row) {
            // const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/send/hr/" + this.positionId + "/" + row.id + "?token=" + this.$store.state.token);
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/send/hr/" + this.positionId + "/" + row.id,
                headers: {
                    token: this.$store.state.token.value,
                },
                responseType: "blob"
            }).then(res => {
                console.log(res.data);
                var resumeTitle = res.headers['content-disposition'].substring(20);
                resumeTitle = decodeURI(resumeTitle);
                // console.log(resumeTitle.substring(20))
                let blob = new Blob([res.data], {
                    type: 'application/pdf'
                })
                let fileName = decodeURI(resumeTitle);
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
        }
    }
}
</script>
<style scoped>
.content >>> .el-table {
    width: 75%;
}
</style>