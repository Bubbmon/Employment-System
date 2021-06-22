<template>
    <div>
        <div class="detail-pane">
            <h1 style="margin:30px;">{{infoDetail.title}}</h1>
            <div class="content">
               <div  style="white-space: pre-wrap;">{{infoDetail.content}}</div>  
            </div>
            
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            infoDetail: null
        }
    },
    methods: {
        async getData() {
            let infoId = decodeURI(this.$route.query.id);
            const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/info/" + infoId);
            this.infoDetail = ret;
        }
    },
    created() {
        this.getData();
    }
}
</script>
<style scoped>
.detail-pane{
    width: 75%;
    margin: 0 auto;
    margin-top: 100px;
    background-color: #f6f6f6;
    overflow: hidden;
}
.content{
    margin: 30px;
    line-height: 30px;
}
.content textarea{
    margin:10px auto;
    background-color: none;
    border: none;
    width: 600px;
    height: 500px;
}
</style>