<template>
    <div class="test">
        <HeaderHR></HeaderHR>
        <div class="chatbox">
            <div class="message">
                <div class="item" v-for="(item,index) in messageList" :key="index">
                    <div>{{item.from}}  {{item.msg}}</div>
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
    components: {
        HeaderHR
    },
    data() {
        return {
            webSocketObj: null,
            wsHeartflag: false,
            reconnectTime: 0,
            message: null,
            newMessage: "",
            messageList: [],
            talkerId:decodeURI(this.$route.query.hrId)
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
           this.messageList.push({from:"我", msg: this.newMessage});
           this.newMessage = encodeURIComponent(this.newMessage);

            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/talk/talkTo",
                headers: {
                    "token": this.$store.state.token.value,//此处放置请求到的用户token
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
            // this.$http({
            //     method: "get",
            //     url: "http://1.117.44.227:8088/employment/webSocket",
            //     headers:{
            //         token:this.$store.state.token.value
            //     }
            // }).then(res=>{
            //     this.webSocketObj = res.data;
            //     console.log("websocket:" + this.webSocketObj);
            // })
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
            this.webSocketObj.send("#open#"+this.$store.state.token.value);
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
            // console.log(typeof(evt.data));
            // this.message =JSON.stringify(evt.data);
            this.message = JSON.parse(evt.data);
            console.log((this.message)) 
            this.talkerId = this.message.id;
            console.log(this.talkerId);
            this.messageList.push({ from:this.message.id, msg: this.message.message });
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
            this.webSocketObj.send("#close#"+this.$store.state.token.value);
            this.wsHeartflag = false;
            this.webSocketObj && this.webSocketObj.close && this.webSocketObj.close();
        },
    },
}
</script>
<style scoped>
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
}
</style>