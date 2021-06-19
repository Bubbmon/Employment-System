<template>
    <div>
        <Header></Header>
        <Retrieval></Retrieval>
    </div>
</template>
<script>
import Header from "../components/Header.vue"
import Retrieval from "../components/Retrieval.vue"
export default {
    components:{
        Header,
        Retrieval
    },
    data() {
        return {
            resultList: [],
            page: 1,
            keyword:decodeURI(this.$route.query.keyword)
        }
    },
    methods: {
        async getData() {
            // const keyword = decodeURI(this.$route.query.keyword);
            // const position = decodeURI(this.$route.query.position);
            // const degree = decodeURI(this.$route.query.degree);
            // const {data:ret} = await this.$http.get('/employment/search'+'?keyword=' + keyword + '&position=' + position + '&degree=' + degree);
            //TODO: 将测试数据换成真实URL
            const { data: ret } = await this.$http.get('/data/retrieval.json');
            this.resultList = ret;
        },
        jump() {
            //TODO: 修改router
            if (this.keyword.length > 30 || this.keyword.length == 1) {
                window.alert('请输入长度大于1且小于30的字符串！')
            } else {
                this.$router.push("/retrievalT" + "?keyword=" + this.keyword + "&position=" + this.position + "&degree=" + this.degree);
            }
        },
    },
    mounted() {
        this.getData();
    }
}
</script>
<style scoped>
a {
    text-decoration: none;
    color: #1f00a0;
}
a:visited {
    color: #50008d;
}
a:hover {
    text-decoration: underline;
}
.content {
    margin: 30px auto;
    width: 800px;
}
.item {
    width: 100%;
    padding-top: 30px;
}
span {
    margin-right: 20px;
}
.search-box1 {
    margin-top: 20px;
    display: flex;
    border: 1px solid #dadce0;
    border-radius: 22.5px;
    overflow: hidden;
    background-color: white;
    margin-left: auto;
    margin-right: auto;
    width: 650px;
    /* z-index: 30; */
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
</style>