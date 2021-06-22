<template>
    <div>
        <HeaderHR></HeaderHR>
        <div class="issue-pane">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="标题">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="岗位类型">
                    <el-select v-model="form.position" placeholder="请选择岗位类型">
                        <el-option label="技术" value="0"></el-option>
                        <el-option label="产品" value="1"></el-option>
                        <el-option label="设计" value="2"></el-option>
                        <el-option label="运营" value="3"></el-option>
                        <el-option label="市场" value="4"></el-option>
                        <el-option label="人事" value="5"></el-option>
                        <el-option label="财务" value="6"></el-option>
                        <el-option label="行政" value="7"></el-option>
                        <el-option label="高级管理" value="8"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学历">
                    <el-select v-model="form.degree" placeholder="请选择学历要求">
                        <el-option label="不限" value="0"></el-option>
                        <el-option label="高中" value="1"></el-option>
                        <el-option label="专科" value="2"></el-option>
                        <el-option label="本科" value="3"></el-option>
                        <el-option label="硕士" value="4"></el-option>
                        <el-option label="博士" value="5"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="薪酬">
                    <el-input v-model="form.salary"></el-input>
                </el-form-item>
                
                <el-form-item label="正文">
                    <el-input type="textarea" v-model="form.content"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">立即创建</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>

            <div class="positionId">
                该招聘信息的ID为：{{positionId}}
            </div>
        </div>

    </div>
</template>
<script>
import HeaderHR from "../../components/HR/HeaderHR.vue"
export default {
    components: {
        HeaderHR
    },
    data() {
        return {
            form: {
                title: '',
                position: '',
                degree:'',
                content:'',
                salary:''
            },
            positionResult:null,
            positionId:null
        }
    },
    methods: {
      onSubmit() {
          console.log(this.form.position)
        this.$http({
            method: "post",
                url: "http://1.117.44.227:8088/employment/position/post",
                headers: {
                    "token": this.$store.state.token.value,
                    "position": this.form.position,
                    "title": encodeURIComponent(this.form.title),
                    "salary": this.form.salary,
                    "degree": this.form.degree
                },
                data: this.form.content
        }).then(res=>{
            this.positionResult = res.data.positionResult;
            this.positionId = res.data.positionId;
            if(this.positionResult == 0){
                window.alert("发布成功, 招聘信息id为" + this.positionId);
                var positionIdArea = document.querySelector(".positionId");
                positionIdArea.style.display = 'block';
            }else if(this.positionId == 1){
                window.alert("发布失败");
            }
        })
      }
    }
}
</script>
<style scoped>
.issue-pane {
    margin: 0 auto;
    margin-top: 50px;
    width: 75%;
}
input {
    border: 1px solid grey;
    border-radius: 10px;
}
.issue-pane >>> .el-textarea__inner {
    height: 300px;
}
.positionId{
    display: none;
}
</style>