<template>
    <!-- HR登录后的Header -->
    <div class="whole">
        <div class="logo" @click="backToHome"></div>
        <div class="self-center" style="width:150px;height:100%; float:right; display:flex">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-submenu index="1">
                    <template slot="title">个人中心</template>
                    <el-menu-item index="selfcenter" @select="handleSelect">账户信息</el-menu-item>
                    <el-menu-item index="issueResume" @select="handleSelect">发布招聘信息</el-menu-item>
                    <el-menu-item index="recruimentList" @select="handleSelect">查看已发布的信息</el-menu-item>
                    <el-menu-item index="chatListHR" @select="handleSelect">我的聊天</el-menu-item>
                </el-submenu>
            </el-menu>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            activeIndex:"1"
        }
    },
    methods:{
        //key参数的值为el-menu-item的index
        handleSelect(key){
            if(key == "selfcenter"){
                this.$router.push("/selfCenterHR");
            }else if(key == "issueResume"){
                this.$router.push("/issueRecruimentInfo");
            }else if(key == "recruimentList"){
                this.$router.push("/recruimentList");
            }else if(key == "chatListHR"){
                this.$router.push("/chatListHR");
            }
        },
        backToHome(){
            this.$router.push("/searchHR");
        }
    }
    
}
</script>
<style scoped>
.whole{
    background-color: #2f2f2f;
    color: #fff;
    height: 60px;
}
.self-center >>> .el-menu--horizontal>.el-submenu .el-submenu__title:hover{
    background-color: #464646;
}
.self-center >>> .el-menu--horizontal>.el-submenu:hover .el-submenu__title{
    color: #F2F2F2;
}
.self-center >>> .el-submenu .el-menu-item{
    background-color: #464646;
}
.whole{
    background-color: #2f2f2f;
}
.self-center{
    background-color: #2f2f2f;
}
.self-center >>> .el-submenu{
    background-color: #2f2f2f;
}
.logo{
    margin-top: 10px;
    cursor: pointer;
    width: 60px;
    height: 30px;
    float: left;
    background-image: url("../../assets/images/pin.jpg");
    background-size: cover;
}
</style>