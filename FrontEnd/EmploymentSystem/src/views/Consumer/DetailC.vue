<template>
    <div>
        <HeaderC></HeaderC>
        <Detail></Detail>
    </div>
</template>
<script>
import HeaderC from '../../components/Consumer/HeaderC.vue'
import Detail from '../../components/Detail.vue'

export default {
    components: {
        HeaderC,
        Detail
    },
}
</script>
<style scoped>
.content {
    width: 75%;
    margin: 0 auto;
}
</style>