<template>
    <div>
        <HeaderC></HeaderC>
        <div class="selfcenter-pane">
            <h2 style="margin-bottom:30px;border-bottom:1px solid black">
                个人信息
                <span style="float:right;color:grey;cursor:pointer" @click="modify">[编辑]</span>
            </h2>
            <div>账户名：
                <input type="text" v-model="id">
            </div>
            <div>姓名：
                <input type="text" v-model="name">
            </div>
            <div>身份证号：
                <input type="text" v-model="IDNO">
            </div>
            <div>手机号：
                <input type="text" v-model="phone">
            </div>
            <div>邮箱：
                <input type="text" v-model="email">
            </div>
            <div>年龄：
                <input type="text" v-model="age">
            </div>
            <div>兴趣：
                <el-select v-model="interest" placeholder="请选择岗位">
                    <el-option v-for="item in positions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </div>
            <div>修改信息请重新输入密码:
                <input type="text" v-model="password">
            </div>
            <button @click="save" style="margin-top:40px">保存修改</button>
        </div>
    </div>
</template>
<script>
import HeaderC from "../../components/Consumer/HeaderC.vue"
export default {
    components: {
        HeaderC
    },
    data() {
        return {
            id: "",
            name: "",
            IDNO: "",
            phone: "",
            email: "",
            age: "",
            interest: "",
            password: "",
            modifyResult: null,
            positions: [
                {
                    value: '0',
                    label: "技术"
                },
                {
                    value: '1',
                    label: "产品"
                },
                {
                    value: '2',
                    label: "设计"
                },
                {
                    value: '3',
                    label: "运营"
                },
                {
                    value: '4',
                    label: "市场"
                }, {
                    value: '5',
                    label: "人事"
                }, {
                    value: '6',
                    label: "财务"
                }, {
                    value: '7',
                    label: "行政"
                }, {
                    value: '8',
                    label: "高级管理"
                }
            ],
        }
    },
    methods: {
        async getData() {
            // console.log(this.$store.state.id)
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/account/recruiter/info/" + this.$store.state.id.value);
            this.id = ret.id;
            this.name = ret.name;
            this.IDNO = ret.iDNO;
            this.phone = ret.phone;
            this.email = ret.email;
            this.age = ret.age;
            this.interest = ret.interest;
        },
        modify() {
            var inputs = document.querySelectorAll('.selfcenter-pane input');
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].style.borderBottom = '1px solid black';
            }
        },
        save() {
            console.log("save")
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/account/recruiter/modify",
                headers: {
                    "id": this.id,
                    "password": this.password,
                    "name": encodeURIComponent(this.name),
                    "IDNO": this.IDNO,
                    "phone": this.phone,
                    "email": this.email,
                    "age": this.age,
                    "interest": this.interest,
                    "token":this.$store.state.token.value
                }
            }).then(res => {
                this.modifyResult = res.data.modifyResult;
                if (this.modifyResult == 0) {
                    window.alert("修改成功");
                    var inputs = document.querySelectorAll('.selfcenter-pane input');
                    for (var i = 0; i < inputs.length; i++) {
                        inputs[i].style.border = 'none';
                    }
                } else {
                    window.alert("修改失败");
                }
            })
        }
    },
    mounted() {
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