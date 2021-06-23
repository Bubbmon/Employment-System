<template>
    <div style="background:#f6f6f6">
        <div style="display:flex;">
            <div class="list">
                <!-- <h2 style="margin:40px;">正在聊天</h2>
                <div class="onlineTalker">
                    {{onlineTalker}}
                </div> -->
                <h2 style="margin:40px;">聊天记录</h2>
                <div class="item" v-for="(item, index) in talkerList" :key="'talker'+index" @click="chatWith(item)">
                    {{item}}
                </div>
                <h2 style="margin:40px;">未接收的消息</h2>
                <div class="item" v-for="(item, index) in newMsg" :key="'newMsg'+index" @click="historyChatWith(item)">
                    {{item}}
                </div>
            </div>
            <div style="width:100%">
                <div class="chat" id="chat">
                    <!-- <vue-scroll> -->
                    <div class="chat-item" v-for="(item, index) in chatContent" :key="'chat'+index">
                        <div style="margin-right:40px;background-color:#e9e9e9;width:100px;text-align:center;">{{item.from}}</div>
                        <div>{{item.message}}</div>
                    </div>
                    <!-- </vue-scroll> -->
                </div>
                <div class="inputbox">
                    <textarea type="text" v-model="newMessage"></textarea>
                    <button @click="send">send</button>
                </div>
            </div>

        </div>
    </div>
</template>
<script>

export default {
    components: {
    },
    data() {
        return {
            webSocketObj: null,
            talkerList: [],
            chatContent: [],
            newMsg: [],
            historyMsg: null,
            newMessage: null,
            talker: null,
            onlineTalker: null
        }
    },
    mounted() {
        this.getTalkers();
        this.handleMsg();
        this.talkFromDetail();
        this.initWebSocket();
    },
    beforeDestroy() {
        this.over()
    },
    methods: {
        talkFromDetail() {
            if (this.$route.query.hrId != null) {
                // var target = document.querySelector(".onlineTalker");
                // target.style.color = '#2667B2';
                setTimeout(() => {
                    this.onlineTalker = this.$route.query.hrId;
                    this.talker = this.onlineTalker;
                    var inList = false;
                    var index = -1;
                    for (var item in this.talkerList) {
                        if (this.talker == this.talkerList[item]) {
                            index = item;
                            inList = true;
                            break;
                        }
                    }
                    if(inList){
                        this.chatWith(this.talkerList[index]);
                    }else{
                        this.talkerList.push(this.talker);
                        this.chatWith(this.talker);
                    }
                    
                }, 100);
            }
        },
        initWebSocket() {
            this.webSocketObj = new WebSocket(
                "ws://1.117.44.227:8088/employment/webSocket"
            );
            this.webSocketObj.onopen = this.onOpen
            this.webSocketObj.onmessage = this.onMessage
            this.webSocketObj.onerror = this.onError
            this.webSocketObj.onclose = this.onClose
            this.over = () => {
                this.webSocketObj.close()
            }
        }, onOpen() {
            console.log('open');
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
            console.log(evt.data);
            let message = JSON.parse(evt.data);
            console.log(message);
            this.chatContent.push({ from: message.id, message: message.message });
            var inList = false;
            for (var item in this.talkerList) {
                if (message.id == this.talkerList[item]) {
                    inList = true;
                }
            }
            if (!inList) {
                this.talkerList.push(message.id);
            }
            this.onlineTalker = message.from;
            setTimeout(() => {
                let box = document.querySelector(".chat");
                box.scrollTop = box.scrollHeight;
            }, 100);
        },
        onError() {
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
            console.log('close')
            this.webSocketObj.send("#close#" + this.$store.state.token.value);
            this.wsHeartflag = false;
            this.webSocketObj && this.webSocketObj.close && this.webSocketObj.close();
        },
        getTalkers() {
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/talk/whoTalkedWithMe",
                headers: {
                    token: this.$store.state.token.value
                }
            }).then(res => {
                this.talkerList = res.data.filter(item => item != this.$store.state.id.value);
            })
        },
        send() {
            this.chatContent.push({ from: this.$store.state.id.value, message: this.newMessage, to: this.talker });
            this.newMessage = encodeURIComponent(this.newMessage);

            this.$http({
                method: "post",
                url: "http://1.117.44.227:8088/employment/talk/talkTo",
                headers: {
                    "token": this.$store.state.token.value,
                    "id": this.talker,
                    "message": this.newMessage
                },
            }).then(res => {
                setTimeout(() => {
                    let box = document.querySelector(".chat");
                    box.scrollTop = box.scrollHeight;
                }, 100);
                this.newMessage = "";
                console.log(res.data);

            })
        },
        chatWith(val) {
            this.talker = val;
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/talk/history",
                headers: {
                    token: this.$store.state.token.value,
                    id: val
                },
            }).then(res => {
                this.chatContent = res.data;
                setTimeout(() => {
                    let box = document.querySelector(".chat");
                    box.scrollTop = box.scrollHeight;
                }, 100);

                // console.log(this.chatContent);
            })

        },
        historyChatWith(val) {
            this.talker = val;
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/talk/newFrom",
                headers: {
                    token: this.$store.state.token.value,
                    from: val
                },
            }).then(res => {
                setTimeout(() => {
                    let box = document.querySelector(".chat");
                    box.scrollTop = box.scrollHeight;
                }, 100);
                this.historyMsg = res.data;
                this.chatContent = this.historyMsg;
                this.chatWith(val);
            })
        },
        handleMsg() {
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/talk/new",
                headers: {
                    token: this.$store.state.token.value,
                },
            }).then(res => {
                this.newMsg = res.data.filter(item => item != this.$store.state.id.value);
            })
        }

    }
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
    height: 600px;
    overflow-x: hidden;
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
textarea {
    width: 80%;
    height: 100px;
    line-height: 20px;
    border: 1px solid #e9e9e9;
    background: #fff;
}
button {
    border: none;
    background-color: black;
    color: #fff;
    width: 20%;
    cursor: pointer;
}
</style>