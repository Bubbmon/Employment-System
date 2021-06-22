<template>
    <div class="whole">
        <div class="banner" style="height:50px">

        </div>
        <div class="signup-pane">
            <h2>账号注册</h2>
            <div style="margin-top:50px">
                <el-radio v-model="radio" label="1" border size="medium">我是应聘者</el-radio>
                <el-radio v-model="radio" label="2" border size="medium">我是HR</el-radio>
            </div>
            <div>
                <input type="text" placeholder="请输入id" v-model="id">
            </div>
            <div>
                <input type="password" placeholder="请输入密码" v-model="password">
            </div>
            <div>
                <input type="text" placeholder="请输入姓名" v-model="name">
            </div>
            <div class="IDNO">
                <input type="text" placeholder="请输入身份证号" v-model="IDNO">
            </div>
            <div class="enterpriseName">
                <input type="text" placeholder="请输入企业名称" v-model="enterpriseName">
            </div>
            <div>
                <input type="text" placeholder="请输入手机号" v-model="phone">
            </div>
            <div class="code">
                <input type="text" placeholder="请输入企业专属code用于验证" v-model="code">
            </div>
            <div>
                <button @click="signup">注册</button>
            </div>

        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            radio: "1",
            id: null,
            password: null,
            name: null,
            IDNO: null,
            enterpriseName: null,
            phone: null,
            code: null,
            signUpResult: null
        }
    },
    watch: {
        radio: val => {
            var IDNO = document.querySelector(".IDNO");
            var enterprise = document.querySelector(".enterpriseName");
            var code = document.querySelector(".code");

            if (val == "2") {
                IDNO.style.display = 'none';
                enterprise.style.display = 'block';
                code.style.display = 'block';
            } else if (val == "1") {
                IDNO.style.display = 'block';
                enterprise.style.display = 'none';
                code.style.display = 'none';
            }
        }
    },
    methods: {
        signup() {
            let isRecruiter = false; //是否是求职者，true：是求职者
            if (this.radio == "1") {
                isRecruiter = true;
            } else if (this.radio == "2") {
                isRecruiter = false;
            }

            // 当注册者是普通应聘者时
            if (isRecruiter) {
                this.$http({
                    method: "post",
                    url: "http://1.117.44.227:8088/employment/account/recruiter/signUp",
                    // data: {
                    //     id: this.id,
                    //     pswd: this.pswd,
                    //     name: this.name,
                    //     IDNO: this.IDNO,
                    //     phone: this.phone
                    // }
                    headers: {
                        "id": this.id,
                        "password": this.password,
                        "name": encodeURIComponent(this.name),
                        "IDNO": this.IDNO,
                        "phone": this.phone
                    },
                }).then(res => {
                    this.signUpResult = res.data.signUpResult;

                    if (this.signUpResult == 0) {
                        window.alert("注册成功！");
                        this.$router.push("/login");
                    } else if (this.signUpResult == 1) {
                        window.alert("账号已存在");
                    } else if (this.signUpResult == 2) {
                        window.alert("注册失败");
                    }
                })
            }//当注册者是HR时
            else {
                this.$http({
                    method: "post",
                    url: "http://1.117.44.227:8088/employment/account/hr/signUp",
                    // data: {
                    //     id: this.id,
                    //     pswd: this.pswd,
                    //     name: this.name,
                    //     enterpriseName: this.enterpriseName,
                    //     phone: this.phone,
                    //     code: this.code
                    // }
                    headers:{
                        "id": this.id,
                        "password": this.password,
                        "name":  encodeURIComponent(this.name) ,
                        "enterpriseName": encodeURIComponent(this.enterpriseName),
                        "phone": this.phone,
                        "code": this.code
                    }
                }).then(res => {
                    this.signUpResult = res.data.signUpResult;

                    if (this.signUpResult == 0) {
                        window.alert("注册成功！");
                        this.$router.push("/login");
                    } else if (this.signUpResult == 1) {
                        window.alert("账号已存在");
                    } else if (this.signUpResult == 2) {
                        window.alert("注册失败");
                    }
                })
            }
        }
    }
}
</script>
<style scoped>
.whole {
    width: 100%;
    height: 100vh;
    background-color: #f6f6f6;
}
.signup-pane {
    width: 60%;
    margin: 0px auto;
    border: 1px solid #dee2e6;
    height: 700px;
    background-color: white;
    opacity: 0.9;
}
.signup-pane >>> .el-radio {
    width: 200px;
}
.signup-pane div {
    width: 75%;
    margin: 0px auto;
    margin-top: 30px;
}
h2 {
    padding-left: 30px;
    padding-top: 50px;
}
button {
    width: 70px;
    height: 40px;
    margin: 0 auto;
    background-color: black;
    color: white;
    cursor: pointer;
    border: none;
}
button:active {
    transform: translateY(2px);
}
button:hover {
    transform: translateY(1px);
}
input {
    text-decoration: none;
    padding: 10px;
    border: 1px solid #dee2e6;
    width: 400px;
    height: 40px;
}
.code {
    display: none;
}
.enterpriseName {
    display: none;
}
</style>