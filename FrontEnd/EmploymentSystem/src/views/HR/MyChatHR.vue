<template>
    <div class="test">
        <HeaderHR></HeaderHR>
        <div class="chatbox">
            <div class="message">
                <div class="item" v-for="(item,index) in messageList" :key="index">
                    <div>{{item.msg}}</div>
                </div>
            </div>
            <div class="inputbox">
                <input type="text" v-model="newMessage" @keyup.enter="send">
                <button @click="send">send</button>
            </div>
        </div>
    </div>
</template>
<script>
import HeaderHR from "../../components/HR/HeaderHR.vue"
export default {
    name: 'test',
    components:{
        HeaderHR
    },
    data() {
        return {
            webSocketObj: null,
            wsHeartflag: false,
            reconnectTime: 0,
            message:"",
            newMessage:"",
            messageList:[]
        }
    },
    created() {
        this.initWebSocket();
    },
    destroyed() {
        // this.websock.close() //离开路由之后断开websocket连接
    },
    methods: {
        send(){
            this.webSocketObj.send(this.newMessage);
            this.newMessage=""
        },
        initWebSocket() {
            this.webSocketObj = new WebSocket(
                "ws://localhost:8081/servletAdvance/webSocketTest/" +"hr1" + "/" + "applicant1"
            );
            this.webSocketObj.onopen = this.onOpen
            this.webSocketObj.onmessage = this.onMessage
            this.webSocketObj.onerror = this.onError
            this.webSocketObj.onclose = this.onClose
        },
        onOpen() {
            
                //- readyState 等于1 的时候建立链接成功
                this.wsHeartflag = true;
                this.webSocketObj.send("hello 求职者");
                this.reconnectTime = 0;
        },
        timingHeart() {
            // 心跳检测  每4.5分钟发送一次 
            if (this.wsHeartflag) {
                this.webSocketObj.send('heartbeat-test');
            }
            this.wsHeart = setTimeout(() => {
                this.timingHeart();
            }, 100 * 1000); // 100s心跳
        },
        onMessage(evt) {
            console.log("message: " + evt.data);
            this.message = evt.data;
            this.messageList.push({msg:this.message});
            // var received_msg = evt && JSON.parse(evt.data);
            // if (received_msg.code === '1') {
            //     // 支付成功
            //     // this.$toast('恭喜您，支付成功，已升级成VIP');
            //     this.onClose();
            // } else {
            //     // 支付失败
            //     this.showSuccessPop = false;
            // }
        },
        onError() {
            // 链接失败，进行重连
            clearTimeout(this.wsHeart);
            this.wsHeartflag = false;
            if (this.reconnectTime <= 5) {
                setTimeout(() => {
                    this.onOpen();
                    this.reconnectTime += 1;
                }, 5000);
            } else {
                this.$toast('抱歉，暂时无法连接到聊天服务器，请稍后再试');
                this.onClose();
            }
        },
        onClose() {
            this.wsHeartflag = false;
            this.webSocketObj && this.webSocketObj.close && this.webSocketObj.close();
        },
    },
}
</script>
<style scoped>
.chatbox{
    width: 600px;
    height: 700px;
    border: 1px solid black;
    margin: 30px auto;
    padding: 10px;
}
.message{
    height: 550px;
}
.inputbox{
    display: flex;
}
input{
    width: 500px;
    height: 30px;
}
</style>