<template>
    <div>
        <HeaderHR></HeaderHR>
        <div class="selfcenter-pane">
            <h2 style="margin-bottom:30px;border-bottom:1px solid black">
                个人信息
                <span style="float:right;color:grey;cursor:pointer" @click="modify">[编辑]</span>
            </h2>
            <div>账户名：
                <!-- <input type="text" v-model="id" disabled="disabled"> -->
                {{id}}
            </div>
            <div>姓名：
                <input type="text" v-model="name" disabled="disabled">
            </div>
            <div>企业名称
                <input type="text" v-model="enterpriseName" disabled="disabled">
            </div>
            <div>手机号：
                <input type="text" v-model="phone" disabled="disabled">
            </div>
            <div>邮箱：
                <input type="text" v-model="email" disabled="disabled">
            </div>
            <div>企业id：
                <input type="text" v-model="enterpriseId" disabled="disabled">
            </div>
            <div>部门：
                <input type="text" v-model="department" disabled="disabled">
            </div>
            
            <div>修改信息请重新输入密码:
                <input type="text" v-model="password" disabled="disabled">
            </div>
            <button @click="save" style="margin-top:40px">保存修改</button>
        </div>
    </div>
</template>
<script>
import HeaderHR from "../../components/HR/HeaderHR.vue"
export default {
    components: {
        HeaderHR
    },
    data(){
        return{
            id: null,
            name: null,
            enterpriseName: null,
            phone: null,
            email: null,
            enterpriseId: null,
            password: null,
            modifyResult: null,
            department:null
        }
    },
    methods:{
        async getData() {
            console.log(this.$store.state.id.value);
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/account/hr/info/" + this.$store.state.id.value);
            this.id = ret.id;
            this.name = ret.name;
            this.enterpriseName = ret.enterpriseName;
            this.phone = ret.phone;
            this.email = ret.email;
            this.enterpriseId = ret.enterpriseId;
            this.department = ret.department;
        },
        modify() {
            var inputs = document.querySelectorAll('.selfcenter-pane input');
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].style.borderBottom = '1px solid black';
                inputs[i].disabled='';
            }
        },
        save() {
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/account/hr/modify",
                headers: {
                    // "id": this.id,
                    "password": this.password,
                    "name": encodeURIComponent(this.name),
                    // "enterpriseName": encodeURIComponent(this.enterpriseName),
                    "phone": this.phone,
                    "email": this.email,
                    "token":this.$store.state.token.value,
                    "department": encodeURIComponent(this.department)
                }
            }).then(res => {
                this.modifyResult = res.data.modifyResult;
                if (this.modifyResult == 0) {
                    window.alert("修改成功");
                    var inputs = document.querySelectorAll('.selfcenter-pane input');
                    for (var i = 0; i < inputs.length; i++) {
                        inputs[i].style.border = 'none';
                        inputs[i].disabled='disabled';
                    }
                } else {
                    window.alert("修改失败");
                }
            })
        }
    },
    mounted(){
        this.getData();
    }
}
</script>
<style scoped>
.selfcenter-pane {
    width: 50%;
    margin: 0 auto;
    margin-top: 50px;
}
.selfcenter-pane div {
    padding: 15px;
}
input {
    ime-mode: disabled;
    height: 20px;
    border: none;
}
button {
    height: 40px;
    width: 70px;
    background-color: black;
    color: white;
    cursor: pointer;
}
</style>