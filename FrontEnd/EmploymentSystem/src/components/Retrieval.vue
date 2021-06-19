<template>
    <div>
        <div class="retrieval-pane">
            <div style="background-color:#E3E3E3;width:75%;margin:20px auto;height:100px;overflow:hidden">
                <div class="search-box1">
                    <input class="search-input1" @keyup.enter="jump" v-model="keyword" placeholder="请输入检索词">
                    <div class="search-btn1" @click="jump"></div>
                </div>
            </div>
            <div style="display:flex;width:75%;margin:0 auto;">
                <div class="select">
                    <el-select v-model="position" placeholder="请选择岗位" style="margin-top:20px;margin-left:50px;">
                        <el-option v-for="item in positions" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <el-select v-model="degree" placeholder="请选择学历" style="margin-top:30px;margin-left:50px;">
                        <el-option v-for="item in degrees" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="content">
                    <div class="item" v-for="item in resultList" :key="item.id" style="width:100%;">
                        <div @click="jumpToDetail(item.id)" class="url">
                            【{{item.enterpriseName}}】 {{item.title}}
                        </div>
                        <div style="font-size:.8rem; margin:20px 5px">
                            <span>学历：{{item.degree}}</span>
                            <span>职位：{{item.position}}</span>
                            <span>薪资：{{item.salary}}</span>
                            <span>浏览量：{{item.views}}</span>
                        </div>
                    </div>
                    <div class="pagination">
                        <el-pagination layout="prev, pager, next" :total=num :page-size="20" @current-change="handleCurrentChange">
                        </el-pagination>
                    </div>
                </div>

            </div>

        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            resultList: [],
            keyword: decodeURI(this.$route.query.keyword),
            position: decodeURI(this.$route.query.position),
            degree: decodeURI(this.$route.query.degree),
            num: null, //总条目数
            total:null,
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
            ],
            title:null
        }
    },
    methods: {
        async getData(val) {
            let keyword = decodeURI(this.$route.query.keyword);
            keyword = encodeURIComponent(keyword);
            console.log(keyword);
            const position = decodeURI(this.$route.query.position);
            const degree = decodeURI(this.$route.query.degree);
            this.$http({
                method: "get",
                url: "http://1.117.44.227:8088/employment/search?keyword=" + keyword + "&position=" +position+"&degree="+degree+"&page=" + val + "&pageSize=20",
            }).then(res => {
                this.resultList = res.data.info
                this.num = res.data.num;
                
            })
            // console.log(val);
            // const { data: ret } = await this.$http.get('/data/retrieval.json');
            // this.resultList = ret;
        },
        handleCurrentChange(val){
            console.log("点击的页码为" + val);
            this.getData(val);
        },
        jump() {
            //TODO: 修改router
            if (this.keyword.length > 30 || this.keyword.length == 1) {
                window.alert('请输入长度大于1且小于30的字符串！')
            }else if(this.position == null){
                window.alert('请选择岗位');
            }else if(this.degree == null){
                window.alert('请选择学历要求');
            }
            else {
                this.getData(1);
            }
        },
        jumpToDetail(val) {
            if (this.$route.path == "/retrievalC") {
                this.$router.push("/detailC" + "?id=" + val);
            } else if (this.$route.path == "/retrievalT") {
                this.$router.push("/detailT" + "?id=" + val);
            } else if (this.$route.path == "/retrievalHR") {
                this.$router.push("/detailHR" + "?id=" + val);
            }
        }
    },
    mounted() {
        this.getData(1);
    }
}
</script>
<style scoped>
.retrieval-pane {
    background-color: #f6f6f6;
    height: 100%;
    overflow: hidden;
}
.url {
    text-decoration: none;
    color: #2667b2;
    cursor: pointer;
}
.url:visited {
    color: #50008d;
}
.url:hover {
    text-decoration: underline;
}
.select {
    background-color: #fff;
    width: 30%;
    margin-right: 5px;
}
.content {
    background-color: #fff;
    width: 70%;
}
.item {
    width: 100%;
    padding-top: 30px;
    border-bottom: 1px solid #e9e9e9;
}
span {
    margin-right: 20px;
}
.search-box1 {
    margin-top: 20px;
    display: flex;
    border: 1px solid #dadce0;
    /* border-radius: 22.5px; */
    overflow: hidden;
    background-color: white;
    margin-left: auto;
    margin-right: auto;
    width: 650px;
    /* z-index: 30; */
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
</style>