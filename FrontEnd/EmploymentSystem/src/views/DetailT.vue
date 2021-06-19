<template>
    <div>
        <Header></Header>
        <Detail></Detail>
    </div>
</template>
<script>
import Header from "../components/Header.vue";
import Detail from "../components/Detail.vue";
export default {
    components:{
        Header,
        Detail
    },
    data() {
        return {
            info: {},
            content: ""
        }
    },
    methods: {
        async getData() {
            // const infoId = decodeURI(this.$route.query.id);
            // const {data:ret} = await this.$http.get('/employment/position/'+ infoId);
            //TODO: 将测试数据换成真实URL
            const { data: ret } = await this.$http.get('/data/detail.json');
            this.info = ret;
            this.content = ret.content;
        },
    },
    mounted() {
        this.getData();
    }
}
</script>
<style scoped>
.content {
    width: 75%;
    margin: 0 auto;
}
</style>