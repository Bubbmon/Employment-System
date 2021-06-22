<template>
    <!-- 普通用户登录后的Header -->
    <div style="width:100%; height:60px;" class="whole">
        <div class="logo" @click="backToHome"></div>
        <div class="self-center" style="width:150px;height:100%; float:right; display:flex">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-submenu index="1">
                    <template slot="title">个人中心</template>
                    <el-menu-item index="/selfCenterC" @select="handleSelect">账户信息</el-menu-item>
                    <el-menu-item index="/selfResume" @select="handleSelect">简历信息</el-menu-item>
                    <el-menu-item index="/chatListC" @select="handleSelect">我的聊天</el-menu-item>
                </el-submenu>
            </el-menu>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            activeIndex: '1',
            key: ""
        };
    },
    methods: {
        handleSelect(key) {
            // console.log(key);
            this.$router.push(key);
        },
        backToHome(){
            this.$router.push("/searchC");
        }
    }
}
</script>
<style scoped>
.self-center
    >>> .el-menu--horizontal
    > .el-submenu.is-active
    .el-submenu__title {
    border-bottom: none;
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
.self-center >>> .el-submenu__title{
    color: #F2F2F2;
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