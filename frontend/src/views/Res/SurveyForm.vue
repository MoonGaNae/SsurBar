<template>
  <div class = "surveyWrapper">
    <div class="surveyForm">
        <el-tag type="danger" effect="plain" style="border-radius:50px;">1일남음</el-tag>
        <div class="surveyDes">
            <h1 class="title">업무 만족도 조사</h1>
            <h5 class="date">2021.10.25~2021.10.28</h5>
            <p class="description">인사팀, 재무팀, 개발팀 여러분의 더 나은 업무를 위해 업무에 대한 자세한 의견을 듣고자 만족도 조사를 실시합니다. 잠깐의 시간을 내주어 설문을 진행해주시면 감사하겠습니다.</p>
        </div>
        <hr>
        <div class="surveyContent">
            <el-form ref="form" :model="form">
                <el-collapse v-model="activeNames" @change="handleChange">
                    <el-collapse-item title="Filters" name="1">
                        <el-form-item>
                            <div class="surveytitle" v-for="(item, idx) in filters" :key="idx">
                                {{item.questionNum}}. {{item.title}}
                                <br>
                                <el-radio-group v-model="form.category">
                                    <div class="surveytitle" v-for="(example, idx) in filterexample[item.questionNum-1]" :key="idx">
                                        <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                    </div>                        
                                </el-radio-group>
                            </div> 
                        </el-form-item>
                    </el-collapse-item>
                    <el-collapse-item v-for="(item, idx) in questions" :key="idx" :title="item.categoryName">
                            <el-form-item>
                                <div class="surveytitle">
                                    {{item.questionNum}}. {{item.title}}
                                    <br>
                                    <el-radio-group v-model="form.category">
                                        <div class="surveytitle" v-for="(example, idx) in questionexample[item.questionNum-1]" :key="idx">
                                            <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                        </div>
                                    </el-radio-group>
                                </div> 
                            </el-form-item>
                        </el-collapse-item>

                    <!-- <el-collapse-item title="Feedback" name="2">
                        <el-form-item>
                            <div class="surveytitle" v-for="(item, idx) in questions" :key="idx">
                                {{item.questionNum}}. {{item.title}}
                                <br>
                                <el-radio-group v-model="form.category">
                                    <div class="surveytitle" v-for="(example, idx) in questionexample[item.questionNum-1]" :key="idx">
                                        <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                    </div>
                                </el-radio-group>
                            </div> 
                        </el-form-item>
                    </el-collapse-item> -->


                    <!-- <el-collapse-item title="Efficiency" name="3">
                        <el-form-item>
                            <div class="surveytitle" v-for="(item, idx) in filters" :key="idx">
                                {{item.questionNum}}. {{item.title}}
                                <br>
                                <el-radio-group v-model="form.category">
                                    <div class="surveytitle" v-for="(example, idx) in filterexample[item.questionNum-1]" :key="idx">
                                        <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                    </div>                        
                                </el-radio-group>
                            </div> 
                        </el-form-item>
                    </el-collapse-item> -->
                </el-collapse>

                <div class="button">
                    <el-button
                    type="primary"
                    @click="submitForm('form')"
                    size="medium"
                    style="border-radius: 50px;"
                    >완료</el-button
                    >
                </div>
                

            </el-form>
        </div>
        <div style="text-align:center;">
            <img class="logo" src="@/assets/smalllogo.png" />
        </div>     
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";

  export default {
    data () {
      return {
        form:{
            
        },
        radio: '1',
        templateId : '1234657891234',
        surveyId: 'rm15zxga9lsRp',
        category: [],
        filters: [],
        questions:[],
        filterexample: [],
        questionexample: []
      };
    },
    methods:{
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                this.register(this.form);
                } else {
                return false;
                }
            });
        },
        getFilterList(surveyId){
            axios.get("survey/" + surveyId + "/filters").then((res)=>{
                this.filters = res.data.filterQuestionList;
  
                var contents = new Array();
                var contentAnswers = new Array();

                for(var i=0; i<this.filters.length; i++){
                    contents[i] = this.filters[i].content
                    contentAnswers[i] = JSON.parse(contents[i])
                }
                this.filterexample = contentAnswers;
            })
        },
        getQuestionList(templateId){
            axios.get("template/" + templateId + "/questions").then((res)=>{
                console.log(res)
                this.questions = res.data.questionList;
                this.category = res.data.questionList.categoryId;

                var contents = new Array();
                var contentAnswers = new Array();

                for(var i=0; i<this.questions.length; i++){
                    contents[i] = this.questions[i].content
                    contentAnswers[i] = JSON.parse(contents[i])
                }
                this.questionexample = contentAnswers;

            })
        },
        register(data){
            console.log(data);
        },
        surveyFilters() {
            console.log(1);
        },
        surveyQuestions(){
            console.log(1);
        }

    },
    // 동기적으로 호출 
    created() {
        this.getQuestionList(this.templateId);
        this.getFilterList(this.surveyId);
    },

    
  }
</script>

<style scoped>

.surveyWrapper{
    background-color: navy;
}

.surveyDes h1{
    font-weight:800;
}
.surveyDes h5{
    font-weight:700;
}
.surveyForm{
    margin-left:25%;
    margin-right:25%;
    padding : 2em;
    text-align: left;
    background-color: white; 
}
.surveyContent{
    margin-top:50px;   
}
.surveytitle{
    margin-top:1em;
    margin-bottom: 1em;
    font-size: large;
}
.logo{
    margin-top : 3em;
}
.button .el-button {
  background-color: orange;
  border-color: orange;
  width: 100px;
}
.button .el-button:hover {
  background-color: rgba(255, 166, 0, 0.815);
  border-color: orange;
}
.button{
    text-align:center; 
    margin-top:3em;
}
</style>
