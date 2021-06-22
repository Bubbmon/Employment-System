<template>
    <div>
        <HeaderC></HeaderC>
        <!-- <div class="search">
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
                        <router-link :to="'/detail' + '?id=' + item.id">
                            【{{item.enterpriseName}}】 {{item.title}}
                        </router-link>
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
                        <router-link :to="'/infoDetailC' + '?id=' + item.id">
                             {{item.title}}
                        </router-link>
                    </div>
                </div>
            </div>
        </div> -->
        <Search></Search>
    </div>
</template>
<script>
import Search from '../../components/Search.vue'
import HeaderC from '../../components/Consumer/HeaderC.vue'
export default {
    components: {
        Search,
        HeaderC
    },
    data() {
        return {
            keyword: "",
            position: null,
            degree: null,
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
    // methods: {
    //     jump() {
    //         //TODO: 修改router
    //         if (this.keyword.length > 30 || this.keyword.length == 1) {
    //             window.alert('请输入长度大于1且小于30的字符串！')
    //         } else {
    //             this.$router.push("/retrievalC" + "?keyword=" + this.keyword + "&position=" + this.position + "&degree=" + this.degree);
    //         }
    //     },
    //     async getRecommendInfo() {
    //         // const {data:ret} = await this.$http.get("/employment/recommend");
    //         const { data: ret } = await this.$http.get('/data/retrieval.json');
    //         this.recommendList = ret;
    //     },
    //     async getInfo() {
    //         // const { data: ret } = await this.$http.get("/employment/info");
    //         const {data:ret} = await this.$http.get('/data/info.json')
    //         this.infoList = ret;
    //     }
    // },
    // mounted() {
    //     this.getRecommendInfo();
    //     this.getInfo();
    // }
}
</script>
<style scoped>
a{
    text-decoration: none;
    color: #2667B2;
    margin-top: 20px;
}
.item a{
    color: #2667B2;
}
.info-item{
    margin-top: 40px;
}
.search-box1 {
    margin-top: 150px;
    display: flex;
    border: 1px dotted #dadce0;
    /* border-radius: 22.5px; */
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
    background-image: url('../../assets/images/search.png');
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
    background-image: url('../../assets/images/banner1.jpg');
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