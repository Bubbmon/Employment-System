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
                        <el-tag size="medium">{{ scope.row.isDealed }}</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="下载">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleDownload(scope.$index, scope.row)">下载</el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
            tableData: [{
                id: '001',
                name: '龚之琳1',
                phone: '13566870180',
                isDealed: true
            }, {
                id: '002',
                name: '王小虎',
                phone: '111111111111',
                isDealed: true
            }, {
                id: '003',
                name: '王小',
                phone: '2222222222',
                isDealed: false
            }, {
                id: '0044',
                name: '王虎',
                phone: '3333333333333',
                isDealed: true
            }]
        }
    },
    methods: {
        async getData() {
            // const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/send/hr/" + this.positionId + "?token=" + this.$store.state.token);
            // this.resumeList = ret;
            this.$http({
                        method: "get",
                        url: "/employment/send/hr/" + this.positionId,
                        headers: {
                            token: this.$store.state.token.value,
                        }
                    }).then(res => {
                       this.resumeList = res.data;
                    })
        },
        handleEdit(index, row){
            console.log(index, row);
            
            if(row.isDealed == true){
                row.isDealed = false;
            }else{
                row.isDealed = true;
            }
        },
        handleDelete(index, row){
            //TODO：是否需要添加删除简历的功能
            console.log(index, row);
        },
        async handleDownload(index, row){
            const {data:ret} = await this.$http.get("/employment/send/hr/" + this.positionId + "/"+row.id + "?token="+this.$store.state.token);
            //TODO：如何读取stream的文件并下载
            console.log(ret);
        }
    }
}
</script>
<style scoped>
.content >>> .el-table {
    width: 75%;
}
</style>