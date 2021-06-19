<template>
    <div>
        <HeaderHR></HeaderHR>
        <Detail></Detail>
    </div>
</template>
<script>
import HeaderHR from '../../components/HR/HeaderHR.vue'
import Detail from '../../components/Detail.vue'
export default {
    components: {
        HeaderHR,
        Detail
    },
    data() {
        return {
            info: {},
            content: ""
        }
    },
}
</script>
<style scoped>
.content {
    width: 75%;
    margin: 0 auto;
}
</style>