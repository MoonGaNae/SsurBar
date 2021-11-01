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
            <el-form ref="form" v-model="form">
                <el-collapse v-model="activeNames" @change="handleChange">


                    <el-collapse-item title="Filters" name="1">
                        <el-form-item>
                            <div class="surveytitle" v-for="(item, idx) in filters" :key="idx">
                                {{item.questionNum}}. {{item.title}}
                                <br>
                                <el-radio-group v-model="form.filterRes[idx]">
                                    <div class="surveytitle" v-for="(example, exampleIdx) in filterExample[item.questionNum-1]" :key="exampleIdx">
                                        <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                    </div>                        
                                </el-radio-group>
                            </div> 
                        </el-form-item>
                    </el-collapse-item>

                    <el-collapse-item v-for="(item, idx) in category" :key="idx" :title="item.categoryName">
                        <el-form-item>
                            <div class="surveytitle" v-for="(question, questionIdx) in questions" :key="questionIdx">
                                <div v-if="item==question.categoryId"> {{question.questionNum}}. {{question.title}} 
                                    <el-radio-group v-model="form.questionRes[questionIdx]">
                                    <div class="surveytitle" v-for="(example, exampleIdx) in questionExample" :key="exampleIdx">
                                        {{example.questionId}}
                                        <el-radio :label="example.questionId" style="display: block; margin-top:1.5em;"></el-radio>
                                    </div>
                                    </el-radio-group>
                                </div>
                                <br>
                            </div> 
                        </el-form-item>
                    </el-collapse-item>



                    <!-- <el-collapse-item v-for="(item, idx) in questions" :key="idx" :title="item.categoryName">
                        <el-form-item>
                            <div class="surveytitle">
                                {{item.questionNum}}. {{item.title}}
                                <br>
                                <el-radio-group v-model="form.questionRes[idx]">
                                    <div class="surveytitle" v-for="(example, exampleIdx) in questionexample[item.questionNum-1]" :key="exampleIdx">
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
            filterRes:[],
            questionRes:[]
        },
        radio: '1',
        templateId : '1234657891234',
        surveyId: 'rm15zxga9lsRp',
        category: [
            {
            categoryId: "",
            categoryName: ""
            }
        ],
        filters: [],
        questions:[],
        filterExample: [],
        questionExample: [
            {
                questionId: "",
                content: []
            }
        ]
      };
    },
    methods:{
        // radio 버튼을 다 누르지 않았을때 넘어가지 않도록 유효성 검사 추가 
        submitForm(formName) {
            console.log("!" + this.form.filterRes)
            const formData = {
                surveyId : this.surveyId,
                filterAnswer : JSON.stringify(this.form.filterRes)
            }
            console.log(formData)

            this.$refs[formName].validate((valid) => {
                if (valid) {
                this.register(formData);
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
                console.log(this.filters);
                console.log(this.filterexample)
            })
        },
        getQuestionList(templateId){
            axios.get("template/" + templateId + "/questions").then((res)=>{
                console.log(res)
                this.questions = res.data.questionList;

                var contents = new Array();
                var categorys = new Array();
                var questionExs = new Array();

                for(var i=0; i<this.questions.length; i++){
                    contents[i] = this.questions[i].content
                    categorys.push({categoryId : this.questions[i].categoryId,categoryName: this.questions[i].categoryName});
                    questionExs.push({questionId: this.questions[i].questionId, content: JSON.parse(contents[i])})
                    console.log(categorys)
                }
                var uniqueCategory = this.removeDuplicates(categorys, "categoryId");
                console.log("uniqueArray is: " + JSON.stringify(uniqueCategory));

                this.category = uniqueCategory
                this.questionexample = questionExs;
                console.log(this.questionexample)
                console.log(this.questions)

            })
        },
        // 카테고리 중복값을 제거하기 위한 메소드 
        removeDuplicates(originalArray, prop) {
            var newArray = [];
            var lookupObject  = {};

            for(var i in originalArray) {
                lookupObject[originalArray[i][prop]] = originalArray[i];
            }

            for(i in lookupObject) {
                newArray.push(lookupObject[i]);
            }
            return newArray;
        },
        register(data){
            console.log(data);
        },
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
