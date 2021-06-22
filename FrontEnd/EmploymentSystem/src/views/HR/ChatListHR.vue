<template>
    <div>
        <HeaderHR></HeaderHR>
        <ChatList></ChatList>
    </div>
</template>
<script>
import HeaderHR from "../../components/HR/HeaderHR.vue"
import ChatList from "../../components/ChatList.vue"

export default {
    components: {
        HeaderHR,
        ChatList
    },
}
</script>
<style scoped>
.list {
    width: 30%;
}
.item {
    height: 50px;
    border-bottom: 1px solid #e9e9e9;
    line-height: 50px;
    margin-left: 20px;
    cursor: pointer;
}
.chat {
    border: 1px solid #e9e9e9;
    height: 700px;
    width: 70%;
}
.chat-item {
    margin-left: 20px;
    display: flex;
    line-height: 30px;
    padding: 10px;
}
.chatbox {
    width: 600px;
    height: 700px;
    border: 1px solid black;
    margin: 30px auto;
    padding: 10px;
}
.message {
    height: 550px;
}
.inputbox {
    display: flex;
}
input {
    width: 500px;
    height: 30px;
    border: 1px solid #e9e9e9;
}
button {
    border: none;
    background-color: black;
    color: #fff;
    width: 60px;
    cursor: pointer;
}
</style>