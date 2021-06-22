<template>
    <div class="test">
        <HeaderC></HeaderC>
        <div class="chatbox">
            <div class="message">
                <div class="item" v-for="(item,index) in messageList" :key="index">
                    <div style="height:30px">
                        <span style="width:100px;text-align:center;margin-right:20px;">{{item.from}} </span>
                        <span>{{item.msg}}</span>
                    </div>
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
import HeaderC from "../../components/Consumer/HeaderC.vue"
export default {
    name: 'test',
    components: {
        HeaderC
    },
    data() {
        return {
            webSocketObj: null,
            wsHeartflag: false,
            reconnectTime: 0,
            message: "",
            newMessage: "",
            messageList: [],
            talkerId: decodeURI(this.$route.query.hrId)
        }
    },
    created() {
        this.initWebSocket();
    },
    destroyed() {
        // this.websock.close() //离开路由之后断开websocket连接
    },
    methods: {
        send() {
            // this.webSocketObj.send(this.newMessage);
            this.messageList.push({ from: this.$store.state.id.value, msg: this.newMessage });
            this.newMessage = encodeURIComponent(this.newMessage);
            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/talk/talkTo",
                headers: {
                    "token": this.$store.state.token.value,
                    //TODO:把id换成消息接受者
                    "id": this.talkerId,
                    "message": this.newMessage
                },
            }).then(res => {
                this.newMessage = "";
                console.log(res.data);
            })
        },
        initWebSocket() {
            this.webSocketObj = new WebSocket(
                "ws://1.117.44.227:8088/employment/webSocket"
            );
            this.webSocketObj.onopen = this.onOpen
            this.webSocketObj.onmessage = this.onMessage
            this.webSocketObj.onerror = this.onError
            this.webSocketObj.onclose = this.onClose
        },
        onOpen() {

            //- readyState 等于1 的时候建立链接成功
            this.wsHeartflag = true;
            this.webSocketObj.send("#open#" + this.$store.state.token.value);
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
            console.log("message" + evt.data);
            this.message = JSON.parse(evt.data);
            this.messageList.push({ from: this.message.id, msg: this.message.message });
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
            this.webSocketObj.send("#close#" + this.$store.state.token.value);
            this.wsHeartflag = false;
            this.webSocketObj && this.webSocketObj.close && this.webSocketObj.close();
        },
    },
}
</script>
<style scoped>
.test {
    background-color: #f6f6f6;
}
.chatbox {
    width: 570px;
    height: 600px;
    border: 1px solid #e9e9e9;
    margin: 10px auto;
    background-color: #fff;
    justify-content: center;
}
.message {
    height: 560px;
}
.inputbox {
    display: flex;
}
input {
    width: 510px;
    height: 40px;
    border: 1px solid #e9e9e9;
    line-height: 40px;
}
button {
    border: none;
    background-color: black;
    color: #fff;
    width: 60px;
    cursor: pointer;
}
</style>