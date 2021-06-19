<template>
    <div class="whole">
        <div class="detail-pane">
            <div class="content">
                <h1 style="margin-top:50px;">{{info.title}}</h1>
                <div style="display: flex">
                    <div class="enterprise" @click="jumpToCompany">{{info.enterpriseName}}</div>
                    <div class="chatBtn" @click="chat">立即沟通</div>
                </div>

                <div class="attributes">
                    <div>
                        <span class="atr">
                            <span class="atr-name"> 岗位类型 </span>
                            <span class="atr-value">{{info.position}}</span>
                        </span>
                        <span style="margin-left:200px" class="atr">
                            <span class="atr-name"> 薪酬 </span>
                            <span class="atr-value">{{info.salary}}</span>
                        </span>
                    </div>
                    <div>
                        <span class="atr">
                            <span class="atr-name"> 学历要求 </span>
                            <span class="atr-value">{{info.degree}}</span>
                        </span>
                        <span style="margin-left:200px" class="atr">
                            <span class="atr-name"> 浏览量 </span>
                            <span class="atr-value">{{info.views}}</span>
                        </span>
                    </div>
                </div>
                <div>
                    <h2 style="margin-bottom:30px;">职位描述</h2>
                    {{content}}
                </div>
                <!-- TODO: 投递入口 -->
                <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-submenu index="2">
                        <template slot="title">投递简历</template>
                        <el-menu-item index="0">投递已有简历
                        </el-menu-item>
                        <el-menu-item index="1">上传新的简历
                            <input type="file" id="newResume" name="newResume" value="newResume" accept=".jpg, .png, .pdf" multiple="false">
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            info: {},
            id: null,
            position: null,
            enterpriseName: null,
            title: null,
            salary: null,
            degree: null,
            views: null,
            hrId: null,
            hrName: null,
            content: null,
            activeIndex: 1,
            index: null,
            newResume: null,
            enterpriseId: null
        }
    },
    methods: {
        async getData() {
            const infoId = decodeURI(this.$route.query.id);
            const { data: ret } = await this.$http.get('http://1.117.44.227:8088/employment/position/' + infoId);
            // TODO: 将测试数据换成真实URL
            // const { data: ret } = await this.$http.get('/data/detail.json');
            this.info = ret;
            this.content = ret.content;
            console.log(this.info);
        },
        chat(){
            if (this.$route.path == "/detailC") {
                this.$router.push("/myChatC" + "?hrId=" + this.info.hrId);
            } else if (this.$route.path == "/detailT") {
                window.alert("请先行登录");
            } else if (this.$route.path == "/detailHR") {
                this.$router.push("/myChatHR" + "?hrId=" + this.info.hrId);
            }

        },
        jumpToCompany() {
            if (this.$route.path == "/detailC") {
                this.$router.push("/companyC" + "?id=" + this.info.enterpriseId);
            } else if (this.$route.path == "/detailT") {
                this.$router.push("/companyT" + "?id=" + this.info.enterpriseId);
            } else if (this.$route.path == "/detailHR") {
                this.$router.push("/companyHR" + "?id=" + this.info.enterpriseId);
            }
        },
        handleSelect(key) {
            if (this.$store.state.token == null) {
                window.alert("登录后方可投递！");
            }
            else {
                //投递已有简历
                if (key == "0") {
                    this.$http({
                        method: "post",
                        url: "/employment/send/recruiter",
                        data: {
                            token: this.$store.state.token.value,
                            useSelf: true,
                            positionId: this.info.id,
                            qualifier: ""
                        }
                    }).then(res => {
                        var sendResult = res.data.sendResult;

                        if (sendResult == 0) {
                            window.alert("投递成功！");
                        } else if (sendResult == 1) {
                            window.alert("投递失败");
                        }
                    })
                } else if (key == "1") { //投递新的简历
                    const input = document.querySelector("input");
                    this.newResume = input.files[0];
                    console.log(this.newResume);
                    let qualifier = null;
                    //将临时简历上传，获取qualifier
                    this.$http({
                        method: "post",
                        url: "/employment/send/recruiter/upload",
                        data: {
                            token: this.$store.state.token,
                            resume: this.newResume
                        }
                    }).then(res => {
                        var resumeResult = res.data.resumeResult;
                        qualifier = res.data.qualifier;
                        if (resumeResult == 0) {
                            window.alert("上传成功！");
                        } else if (resumeResult == 1) {
                            window.alert("上传失败");
                        }
                    })
                    //将上传的临时简历进行投递
                    this.$http({
                        method: "post",
                        url: "/employment/send/recruiter",
                        data: {
                            token: this.$store.state.token,
                            useSelf: false,
                            positionId: this.info.id,
                            qualifier: qualifier
                        }
                    }).then(res => {
                        var sendResult = res.data.sendResult;

                        if (sendResult == 0) {
                            window.alert("投递成功！");
                        } else if (sendResult == 1) {
                            window.alert("投递失败");
                        }
                    })
                }
            }
        }
    },
    mounted() {
        this.getData();
    }
}
</script>
<style scoped>
.whole {
    background-color: #f6f6f6;
    height: 100%;
    overflow: hidden;
}
.atr-name {
    color: #666666;
    margin-right: 50px;
}
.content {
    width: 75%;
    margin: 0 auto;
}
.enterprise,.chatBtn {
    width: 100px;
    height: 50px;
    margin: 30px 0;
    background-color: #2667b2;
    border: none;
    color: white;
    text-align: center;
    line-height: 50px;
    cursor: pointer;
    margin-right: 20px;
}
.enterprise:hover {
    transform: translateY(1px);
}
.attributes {
    margin-bottom: 30px;
}
.attributes div {
    width: 600px;
    margin-top: 40px;
    height: 50px;
    border-bottom: 1px solid #e9e9e9;
}
/* .attributes span {
    margin: 10px;
    padding: 5px;
    border: none;
    border-radius: 5px;
    background-color: #f1f3f5;
} */
.content >>> .el-menu {
    margin-top: 30px;
}
.content >>> .el-menu--horizontal > .el-submenu .el-submenu__title {
    background-color: black;
    color: white;
}
.content >>> .el-menu--horizontal > .el-submenu .el-submenu__title:hover {
    transform: translateY(2px);
}
.content >>> .el-menu.el-menu--horizontal {
    border: none;
    background: none;
}
</style>