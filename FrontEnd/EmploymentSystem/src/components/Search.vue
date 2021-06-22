<template>
    <div class="whole">
        <div class="search">
            <div class="search-box1">
                <input class="search-input1" @keyup.enter="jump" v-model="keyword" placeholder="请输入检索词">
                <div class="search-btn1" @click="jump"></div>
            </div>
            <el-select v-model="position" placeholder="请选择岗位">
                <el-option v-for="item in positions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
            <el-select v-model="degree" placeholder="请选择学历">
                <el-option v-for="item in degrees" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
        </div>
        <div style="display:flex; width:75%; margin:0 auto;">
            <div class="recommend">
                <h3>推荐</h3>
                <div style="margin-top:30px">
                    <div class="item" v-for="item in recommendList" :key="item.id">
                        <div @click="jumpToDetail(item.id)" class="url">
                            【{{item.enterpriseId}}】 {{item.title}}
                        </div>
                        <div style="font-size:.8rem; margin:20px 5px">
                            <span>学历：{{item.degree}}</span>
                            <span style="margin-left:20px">职位：{{item.position}}</span>
                            <span style="margin-left:20px">薪资：{{item.salary}}</span>
                            <span style="margin-left:20px">浏览量：{{item.views}}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="info">
                <h3>资讯</h3>
                <div style="margin-top:30px">
                    <div class="info-item" v-for="item in infoList" :key="item.id">
                        <div @click="jumpToInfo(item.id)" class="url">
                            {{item.title}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { positionTransfer } from '@/utils/positionTransfer';
import { degreeTransfer } from '@/utils/degreeTransfer';
export default {
    data() {
        return {
            keyword: "",
            position: "",
            degree: "",
            recommendList: [],
            infoList: [],
            positions: [
                {
                    value: '0',
                    label: "技术"
                },
                {
                    value: '1',
                    label: "产品"
                },
                {
                    value: '2',
                    label: "设计"
                },
                {
                    value: '3',
                    label: "运营"
                },
                {
                    value: '4',
                    label: "市场"
                }, {
                    value: '5',
                    label: "人事"
                }, {
                    value: '6',
                    label: "财务"
                }, {
                    value: '7',
                    label: "行政"
                }, {
                    value: '8',
                    label: "高级管理"
                }
            ],
            count:5,
            degrees: [
                {
                    value: '0',
                    label: "不限"
                },
                {
                    value: '1',
                    label: "高中"
                },
                {
                    value: '2',
                    label: "专科"
                },
                {
                    value: '3',
                    label: "本科"
                },
                {
                    value: '4',
                    label: "硕士"
                },
                {
                    value: '5',
                    label: "博士"
                }
            ]
        }
    },
    methods: {
        jump() {
            //TODO: 修改router
            if (this.keyword.length > 30 || this.keyword.length == 0) {
                window.alert('请输入长度大于0且小于30的字符串！');
            }
            // else if(this.position == null){
            //     window.alert('请选择岗位');
            // }else if(this.degree == null){
            //     window.alert('请选择学历要求');
            // }
            else {
                if (this.$route.path == "/searchC") {
                    this.$router.push("/retrievalC" + "?keyword=" + this.keyword + "&position=" + this.position + "&degree=" + this.degree);
                } else if (this.$route.path == "/searchT" || this.$route.path == "/") {
                    this.$router.push("/retrievalT" + "?keyword=" + this.keyword + "&position=" + this.position + "&degree=" + this.degree);
                } else if (this.$route.path == "/searchHR") {
                    this.$router.push("/retrievalHR" + "?keyword=" + this.keyword + "&position=" + this.position + "&degree=" + this.degree);
                }
            }
        },
        async getRecommendInfo() {
            this.$http({
                method: "get",
                url:"http://1.117.44.227:8088/employment/recommend",
                headers: {
                        token: this.$store.state.token.value,
                        count: this.count,//推荐条数
                    },
            }).then(res => {
                this.recommendList = res.data.map(item=>{
                    item.position = positionTransfer(item.position).key;
                    item.degree = degreeTransfer(item.degree).key;
                    return item;
                })
            })
            // const { data: ret } = await this.$http.get('/data/retrieval.json');
            // this.recommendList = ret;
        },
        async getInfo() {
            // const { data: ret } = await this.$http.get("http://1.117.44.227:8088/employment/info");
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/info",
                headers: {
                    count:7
                },
            }).then(res => {
                this.infoList = res.data;
            })
            // this.infoList = ret;
        },
        jumpToDetail(val) {
            if (this.$route.path == "/searchC") {
                this.$router.push("/detailC" + "?id=" + val);
            } else if (this.$route.path == "/searchT" || this.$route.path == "/") {
                this.$router.push("/detailT" + "?id=" + val);
            } else if (this.$route.path == "/searchHR") {
                this.$router.push("/detailHR" + "?id=" + val);
            }
        },
        jumpToInfo(val) {
            if (this.$route.path == "/searchC") {
                this.$router.push("/infoDetailC" + "?id=" + val);
            } else if (this.$route.path == "/searchT" || this.$route.path == "/") {
                this.$router.push("/infoDetailT" + "?id=" + val);
            } else if (this.$route.path == "/searchHR") {
                this.$router.push("/infoDetailHR" + "?id=" + val);
            }
        }
    },
    mounted() {
        this.getRecommendInfo();
        this.getInfo();
    }
}
</script>
<style scoped>
.whole {
    background-color: #f6f6f6;
}
.url,
a {
    text-decoration: none;
    color: #2667b2;
    margin-top: 20px;
    cursor: pointer;
}
.item .url {
    color: #2667b2;
}
.info-item {
    margin-top: 40px;
}
.search-box1 {
    margin-top: 150px;
    display: flex;
    border: 1px dotted #dadce0;
    overflow: hidden;
    background-color: white;
    margin-left: 50px;
    width: 500px;
    z-index: 30;
}
input {
    border: hidden;
    border-right: 1px solid #dadce0;
    margin: 5px;
    padding-left: 10px;
}

.search-input1 {
    height: 45px;
    width: 600px;
    line-height: 45px;
}
.search-btn1 {
    background-image: url('../assets/images/search.png');
    background-size: 70%;
    background-position-y: 10px;
    background-repeat: no-repeat;
    height: 45px;
    width: 45px;
    cursor: pointer;
}
.recommend,
.info {
    width: 50%;
    margin: 50px auto;
}
.search {
    overflow: hidden;
    background-image: url('../assets/images/banner1.jpg');
    background-size: cover;
    height: 400px;
    background-position: 0px -150px;
}
.search >>> .el-select {
    margin-left: 50px;
    margin-top: 20px;
    border-radius: 0;
}
.search >>> .el-input--suffix .el-input__inner {
    border: none;
}
.search >>> .el-scrollbar .el-select-dropdown__item.selected {
    color: black;
}
</style>