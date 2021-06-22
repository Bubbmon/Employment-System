<template>
    <div class="banner" style="">
        <div class="logo" @click="backToHome"></div>
        <div class="log_sign" style="width:150px;height:100%; float:right; display:flex">
            <div class="log_in"  @click="login" >登录</div>
            <div class="sign_up" @click="signUp">注册</div>
        </div>
    </div>
</template>

<script>
export default {
    methods:{
        login(){
            this.$router.push("/login");
        },
        signUp(){
            this.$router.push("/signUp");
        },
        backToHome(){
            this.$router.push("/search");
        }
    }
}
</script>
<style scoped>
.banner{
    background-color: #2f2f2f;
    color: #fff;
    height: 60px;
}
.log_in{
    cursor:pointer;
    height: 60px;
    line-height: 60px;
}
.sign_up{
    cursor : pointer;
    height: 60px;
    line-height: 60px;
    margin-left: 30px;
}
.logo{
    margin-top: 10px;
    cursor: pointer;
    width: 60px;
    height: 30px;
    float: left;
    background-image: url("../assets/images/pin.jpg");
    background-size: cover;
}
</style>