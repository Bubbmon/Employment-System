<template>
    <div class="whole">
        <div class="login-pane">
            <!-- <el-col :xs="24">
                <el-row class="main-area"> -->
            <h1 style="margin-bottom:30px;">登录</h1>
            <input v-model="id" placeholder="账号" type="text" style="margin-bottom:20px;">
            <input v-model="password" placeholder="密码" type="password" style="margin-bottom:20px;">
            <div class="status" style="font-size:.8rem;width:400px;margin-top:10px;margin-bottom:30px;">
                <el-radio v-model="status" label="0" style="margin-right:20px">普通用户</el-radio>
                <el-radio v-model="status" label="1" style="">HR</el-radio>
            </div>
            <div style="display:flex;position:relative">
                <el-button type="primary" @click="jump">登录</el-button>
            </div>
            <!-- </el-row>
            </el-col> -->
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            id: null,
            password: null,
            status: "0",
            signInResult: null,
            token: null
        }
    },
    methods: {
        async jump() {
            if (this.id == null || this.password == null) {
                window.alert("请完整填写信息！");
            }
            let isRecruiter = false;
            if (this.status == 0) {
                isRecruiter = true;
            }
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/account/signIn",
                // data: {
                //     id: this.id,
                //     pswd: this.password,
                //     isRecruiter: isRecruiter
                // }
                headers: {
                    "id": this.id,//此处放置请求到的用户token
                    "password": this.password,
                    "isRecruiter": isRecruiter
                },
            }).then(res => {
                this.signInResult = res.data.signInResult;
                this.token = res.data.token;
                var identity = 0;
                if(!isRecruiter){//如果是hr
                    identity = 1;
                }
                // console.log("this.token" + res.data.token);
                this.$store.commit('getToken', {
                    value: this.token
                });
                this.$store.commit('getId', {
                    value: this.id
                });
                this.$store.commit('getIdentity', {
                    value: identity
                });
                if (this.signInResult == 0) {
                    if (isRecruiter) {
                        this.$router.push("/searchC");
                    } else {
                        this.$router.push("/searchHR");
                    }
                } else if (this.signInResult == 1) {
                    window.alert("登录失败，请检查用户名或密码是否填写正确");
                }
            })
        },
    }
}
</script>
<style scoped>
.whole {
    width: 100%;
    height: 100vh;
    background-size: cover;
    background-color: #f6f6f6;
    overflow: hidden;
}
.login-pane {
    background-color: #fff;
    width: 500px;
    margin: 100px auto;
    overflow: hidden;
    padding: 50px;
    height: 500px;
}
.login-pane input{
    height: 40px;
    line-height: 40px;
    border: 1px solid #E9E9E9;
    width: 300px;
    padding: 10px;
}
.whole >>> .el-button {
    background-color: black;
    color: white;
    border: 1px solid white;
    border-radius: 0;
    width: 100px;
}
.whole >>> .el-button:hover {
    transform: translateY(2px);
}
.whole >>> .el-radio__input.is-checked .el-radio__inner {
    border-color: black;
    background: black;
}
.whole >>> .el-radio__input.is-checked + .el-radio__label {
    color: black;
}
.whole >>> .el-radio__label {
    color: #666666;
}
.whole >>> .el-input:active {
    border: none;
}
</style>