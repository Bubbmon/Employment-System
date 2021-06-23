<template>
    <div>
        <div class="company-pane">
            <h1 style="margin:30px;">{{companyInfo.name}}</h1>
            <div style="font-weight:700;margin-bottom:20px;margin-left:30px;">{{companyInfo.address}}</div>
            <div style="line-height:30px;margin:30px">{{companyInfo.introduction}}</div>
            <h2 style="margin:30px;">招聘信息</h2>
            <div class="recruimentInfo">
                <el-table :data="recruimentList" stripe style="width: 75%; margin:0 auto;margin-top:20px;">
                    <el-table-column prop="id" label="ID" width="50">
                    </el-table-column>
                    <el-table-column prop="position" label="岗位类型" width="100">
                    </el-table-column>
                    <el-table-column prop="title" label="标题" width="150">
                    </el-table-column>
                    <el-table-column prop="salary" label="薪酬" width="50">
                    </el-table-column>
                    <el-table-column prop="degree" label="学历" width="50">
                    </el-table-column>
                    <el-table-column prop="views" label="浏览量" width="70">
                    </el-table-column>
                    <el-table-column prop="detail" label="详情" width="80">
                        <template slot-scope="scope">
                            <el-button size="mini" @click="jumpToDetail(scope.row.id)">详情</el-button>
                        </template>
                    </el-table-column>
                </el-table>

            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            enterpriseId: decodeURI(this.$route.query.id),
            companyInfo: null,
            recruimentList: [],
            id: null,
            position: '',
            title: '',
            salary: '',
            degree: '',
            views: '',
        }
    },
    methods: {
        async getData() {
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/enterprise/info/" + this.enterpriseId);
            this.companyInfo = ret;
        },
        async getAllRecruimentInfo() {
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/position/enterprise/" + this.enterpriseId);
            this.recruimentList = ret;
        },
        jumpToDetail(id) {
            if (this.$store.state.identity.value == 0) {
                this.$router.push("/detailC" + "?id=" + id);
            } else if (this.$store.state.identity.value == 1) {
                this.$router.push("/detailHR" + "?id=" + id);
            } else {
                this.$router.push("/detailT" + "?id=" + id);
            }
        }
    },
    mounted() {
        this.getData();
        this.getAllRecruimentInfo();
    }

}
</script>
<style scoped>
.company-pane {
    width: 75%;
    margin: 0 auto;
    margin-top: 100px;
    overflow: hidden;
    background-color: #f6f6f6;
    margin-bottom: 100px;
    padding-bottom: 50px;
}
</style>