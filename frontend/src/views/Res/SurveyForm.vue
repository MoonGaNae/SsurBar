<template>
  <div class = "surveyWrapper">
    <div class="surveyForm">
        <el-tag type="danger" effect="plain" style="border-radius:50px;">{{template.sub}}일남음</el-tag>
        <div class="surveyDes">
            <h1 class="title">{{template.title}}</h1>
            <h5 class="date">{{template.start}}~{{template.end}}</h5>
            <p class="description">{{template.desc}}</p>
        </div>
        <hr>
        <div class="surveyContent">
            <el-form ref="form" v-model="form">
                <el-collapse>
                    <el-collapse-item title="Filters" name="1">
                        <div class="surveytitle" v-for="(item, idx) in filters" :key="idx">
                            {{item.questionNum}}. {{item.title}}
                            <br>
                            <el-form-item>
                                <el-radio-group v-model="form.filterRes[idx]">
                                    <div class="surveytitle" v-for="(example, exampleIdx) in filterExample[item.questionNum-1]" :key="exampleIdx">
                                        <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                    </div>                        
                                </el-radio-group>
                            </el-form-item>
                        </div> 
                    </el-collapse-item>

                    <el-collapse-item v-for="(item, idx) in category" :key="idx" :title="item.categoryName">
                        <div class="surveytitle" v-for="(question, questionIdx) in questions" :key="questionIdx">
                            <div v-if="item.categoryId==question.categoryId">
                                {{question.questionNum}}. {{question.title}} 
                                <br>
                                <el-form-item>   
                                    <el-radio-group v-model="form.questionRes[questionIdx]">
                                        <div class="surveytitle" v-for="(example, exampleIdx) in questionExample[questionIdx].content" :key="exampleIdx">
                                            <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                        </div> 
                                    </el-radio-group>
                                </el-form-item>
                                </div> 
                            </div>
                    </el-collapse-item>
                </el-collapse>

                <div class="button">
                    <el-button
                    type="primary"
                    @click="submitForm()"
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
import { mapActions, mapState } from "vuex";

  export default {
    data () {
      return {
        form:{
            filterTitle:[],
            filterRes:[],
            questionId:[],
            questionRes:[]
        },
        rules: {
          filterRes: [
            { required: true, message: '일치하는 항목에 체크해주세요.', trigger: 'change' },
          ],
          questionRes: [
            { required: true, message: '일치하는 항목에 체크해주세요.', trigger: 'change' },
          ],
        },
        radio: '1',
        // templateId : '1234657891234',
        // surveyId: 'rm15zxga9lsRp',
        tempaletId : '',
        surveyId : '',
        template:{
            title:'',
            desc:'',
            start:'',
            end:'',
            sub:''
        },
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
    computed: {
        ...mapState("survey", ["surveyInfo"]),      
    },
    methods:{
        ...mapActions("survey", ["getSurveyDetailInfo"]),
        submitForm() {
            console.log(this.form)
            var questionIdAnswer = new Array();
            var answerlist = new Array();
            var filterlist = new Array();

            // Json 형태 수정 해야됨 
            for(var i=0; i<this.form.questionId.length; i++){
                var QId = this.form.questionId[i];
                var Qres = this.form.questionRes[i];
                questionIdAnswer.push({questionId: QId, answer:JSON.stringify(Qres)})
                answerlist.push(JSON.stringify({questionId: QId, answer:JSON.stringify(Qres)}));
            }
            for(var j=0; j<this.form.filterTitle.length; j++){
                var Ftitle = this.form.filterTitle[j];
                var Fres = this.form.filterRes[j];
                var something = { };
                something[Ftitle]= Fres;
                filterlist.push(something)
            }

            console.log(filterlist)

            const formData = {
                surveyId : this.surveyId,
                filterAnswer : JSON.stringify(filterlist),
                answerList : answerlist
            }
            console.log(formData)
            if(this.checkForm(questionIdAnswer)){
                this.register(formData);
                return true;
            }else{
                this.$fire({
                    title: "응답실패",
                    text : "아직 체크하지 않은 응답이 존재합니다!",
                    type: "error",
                })
                console.log(false);
                return false;
            }
        },
        getFilterList(surveyId){
            axios.get("survey/" + surveyId + "/filters").then((res)=>{
                this.filters = res.data.filterQuestionList;
  
                var contents = new Array();
                var contentAnswers = new Array();

                for(var i=0; i<this.filters.length; i++){
                    contents[i] = this.filters[i].content
                    contentAnswers[i] = JSON.parse(contents[i])
                    this.form.filterTitle.push(this.filters[i].title)
                }
                this.filterExample = contentAnswers;
                console.log(this.filters);
                console.log(this.filterExample)
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
                    this.form.questionId.push(this.questions[i].questionId)
                    console.log(categorys)
                }
                var uniqueCategory = this.removeDuplicates(categorys, "categoryId");
                console.log("uniqueArray is: " + JSON.stringify(uniqueCategory));

                this.category = uniqueCategory
                this.questionExample = questionExs;
                console.log(this.form)

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
            axios
            .post("response" , data)
            .then(()=>{
                // 완료페이지 이동
                this.$router.push("/finish");
                console.log("Success!")
            })
        },
        // radio 버튼을 다 누르지 않았을때 넘어가지 않도록 유효성 검사하는 로직 (alert 추가해야됨)
        checkForm(data){
            console.log(data);
            for(var i=0; i<data.length; i++){
                var result = data[i].answer;
                if(!result){
                    return false;
                }
            }
            return true;
        },
        async setInfo(){
            await this.getSurveyDetailInfo(this.surveyId)
            this.template.title = this.surveyInfo.title;
            this.template.desc = this.surveyInfo.description;
            this.template.start = this.surveyInfo.creationTime;
            this.template.end = this.surveyInfo.endTime;

            // 시작날짜와 마감날짜 차이를 구하는 변수 선언
            var sdt = new Date(this.template.start);
            var edt = new Date(this.template.end);
            var dateDiff = Math.ceil((edt-sdt)/(1000*3600*24));
            this.template.sub = dateDiff;      
        },

        async process(){
            const linkCode = this.$route.params.linkCode;
            await axios
                .get("/survey/decode-link?linkCode=" + linkCode +"&type=answer")
                .then((res) => {
                    this.templateId = res.data.templateId;
                    this.surveyId = res.data.surveyId;
                    this.getQuestionList(this.templateId);
                    this.getFilterList(this.surveyId);
                })
                .catch((err) => {
                    console.log(err);
                });
            
        }
    },
    // 동기적으로 호출 
    created() {
        this.process()
        this.setInfo()
        
        
    },
  }
</script>

<style scoped>

.surveyWrapper{
    background-color: navy;
    height: 100vh;
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
    height: 100vh;
    position : relative;
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
    width: 20%;
    position : absolute;
    bottom : 3em;
    right:40%;
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
