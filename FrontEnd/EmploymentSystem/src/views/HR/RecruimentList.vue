<template>
    <div>
        <HeaderHR></HeaderHR>
        <el-table :data="recruimentList" stripe style="width: 75%; margin:0 auto;margin-top:50px;">
            <el-table-column prop="id" label="招聘信息ID" width="100">
                <!-- TODO:不太确定，在表格中使用链接 -->
                <template slot-scope="recruimentList">
                    <!-- <a href="'/resumeList?posionId='+recruimentList.row.id">{{recruimentList.row.id}}</a> -->
                    <div @click="jumpToResumeList(recruimentList.row.id)" style="cursor:pointer">{{recruimentList.row.id}}</div>
                </template>
            </el-table-column>
            <el-table-column prop="position" label="岗位类型" width="100">
            </el-table-column>
            <el-table-column prop="title" label="标题" width="400">
            </el-table-column>
            <el-table-column prop="salary" label="薪酬" width="100">
            </el-table-column>
            <el-table-column prop="degree" label="学历" width="100">
            </el-table-column>
            <el-table-column prop="views" label="浏览量" width="100">
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
import HeaderHR from '../../components/HR/HeaderHR.vue';
export default {
    components: {
        HeaderHR
    },
    data() {
        return {
            enterpriseId: null,
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
        jumpToResumeList(val){
            this.$router.push("/resumeList" + "?positionId=" + val);
        },
        async getEnterpriseId() {
            console.log("state.id=" + this.$store.state.id.value);
            let hrId = this.$store.state.id.value;
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/account/hr/info/" + hrId);
            this.enterpriseId = ret.enterpriseId;
            this.getAllRecruimentInfo()
        },
        async getAllRecruimentInfo() {
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/position/enterprise/" + this.enterpriseId);
            this.recruimentList = ret;
        }
    },
    mounted() {
        this.getEnterpriseId();
    }
}
</script>
<style>
</style>