<template>
    <div>
        <HeaderC></HeaderC>
        <ChatList></ChatList>
    </div>
</template>
<script>
import HeaderC from "../../components/Consumer/HeaderC.vue"
import ChatList from "../../components/ChatList.vue";

export default {
    components: {
        HeaderC,
        ChatList
    },
    
}
</script>
<style scoped>
.list {
    /* margin-top: 50px; */
    width: 30%;
    /* border: 1px solid black; */
}
.item {
    height: 50px;
    border-bottom: 1px solid #e9e9e9;
    line-height: 50px;
    margin-left: 20px;
    cursor:pointer;
    color: #2667B2;
}
.chat {
    border: 1px solid #e9e9e9;
    height: 700px;
    width: 70%;
}
.chat-item {
    margin-left:20px;
    display: flex;
    line-height: 30px;
    padding: 10px;
}
</style>