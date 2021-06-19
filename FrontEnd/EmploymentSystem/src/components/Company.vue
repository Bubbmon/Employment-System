<template>
    <div>
        <div class="company-pane">
            <h1 style="margin:30px;">{{companyInfo.name}}</h1>
            <div style="font-weight:700;margin-bottom:20px;margin-left:30px;">{{companyInfo.address}}</div>
            <div style="line-height:30px;margin:30px">{{companyInfo.introduction}}</div>
            
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            enterpriseId: decodeURI(this.$route.query.enterpriseId),
            companyInfo: null
        }
    },
    methods: {
        async getData() {
            const {data:ret} = await this.$http.get("http://1.117.44.227:8088/employment/enterprise/info/" + this.enterpriseId);
            // const { data: ret } = await this.$http.get("/data/company.json");
            this.companyInfo = ret;
        }
    },
    mounted() {
        this.getData();
    }

}
</script>
<style scoped>
.company-pane{
    width:75%;
    margin: 0 auto;
    margin-top: 100px;
    overflow: hidden;
    background-color: #f6f6f6;
}
</style>