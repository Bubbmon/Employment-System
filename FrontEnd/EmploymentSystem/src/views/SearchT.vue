<template>
    <div>
        <Header></Header>
        <Search></Search>
        <!-- <div class="search">
            <div class="search-box1">
                <input class="search-input1" @keyup.enter="jump" v-model="keyword" placeholder="请输入检索词">
                <div class="search-btn1" @click="jump"></div>
            </div>
            <div style="">
                <div class="position" style="font-size:.8rem;padding:10px 30px;margin:10px auto;width:800px">
                    职位：
                    <el-radio v-model="position" label="0" style="margin:10px">技术</el-radio>
                    <el-radio v-model="position" label="1" style="margin:10px">产品</el-radio>
                    <el-radio v-model="position" label="2" style="margin:10px">设计</el-radio>
                    <el-radio v-model="position" label="3" style="margin:10px">运营</el-radio>
                    <el-radio v-model="position" label="4" style="margin:10px">市场</el-radio>
                    <el-radio v-model="position" label="5" style="margin:10px">人事</el-radio>
                    <el-radio v-model="position" label="6" style="margin:10px">财务</el-radio>
                    <el-radio v-model="position" label="7" style="margin:10px">行政</el-radio>
                    <el-radio v-model="position" label="8" style="margin:10px">高级管理</el-radio>
                </div>
                <div class="degree" style="font-size:.8rem;padding:10px 30px;margin:0 auto;width:800px">
                    学历：
                    <el-radio v-model="degree" label="0" style="margin:10px;">不限</el-radio>
                    <el-radio v-model="degree" label="1" style="margin:10px;">高中</el-radio>
                    <el-radio v-model="degree" label="2" style="margin:10px;">专科</el-radio>
                    <el-radio v-model="degree" label="3" style="margin:10px;">本科</el-radio>
                    <el-radio v-model="degree" label="4" style="margin:10px;">硕士</el-radio>
                    <el-radio v-model="degree" label="5" style="margin:10px;">博士</el-radio>
                </div>
            </div>
            <div class="graph">


            </div>
        </div> -->
    </div>
</template>
<script>
import Header from '../components/Header.vue';
import Search from '../components/Search.vue';
export default {
    components: {
        Header,
        Search
    },
    data(){
        return{
            keyword:"",
            position:"0",
            degree:"0"
        }
    },
    methods: {
        jump() {
            //TODO: 修改router
            if (this.keyword.length > 30 || this.keyword.length == 1) {
                window.alert('请输入长度大于1且小于30的字符串！')
            } else {
                this.$router.push("/retrievalT" + "?keyword=" + this.keyword + "&position=" + this.position + "&degree=" + this.degree);
            }
        },
    }
}
</script>
<style scoped>
.search-box1 {
    margin-top: 70px;
    display: flex;
    border: 1px solid #dadce0;
    border-radius: 22.5px;
    overflow: hidden;
    background-color: white;
    margin-left: auto;
    margin-right: auto;
    width: 650px;
    z-index: 30;
}
input {
    border: hidden;
    border-right: 1px solid #dadce0;
    margin: 5px;
    padding-left: 10px;
}

.search-input1 {
    height: 45px;
    width: 600px;
    line-height: 45px;
}
.search-btn1 {
    background-image: url('../assets/images/search.png');
    background-size: 70%;
    background-position-y: 10px;
    background-repeat: no-repeat;
    height: 45px;
    width: 45px;
    cursor: pointer;
}
.graph{
    margin: 50px auto;
    height: 350px;
    width: 700px;
    background-size: cover;
    background-image: url("../assets/images/bg_1.jpg");
}
</style>