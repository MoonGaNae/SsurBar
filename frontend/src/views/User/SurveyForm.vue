<template>
  <div class = "surveyWrapper">
    <div class="surveyForm">
        <el-tag type="danger" effect="plain">1일남음</el-tag>
        <div class="surveyDes">
            <h1 class="title">업무 만족도 조사</h1>
            <h5 class="date">2021.10.25~2021.10.28</h5>
            <p class="description">인사팀, 재무팀, 개발팀 여러분의 더 나은 업무를 위해 업무에 대한 자세한 의견을 듣고자 만족도 조사를 실시합니다. 잠깐의 시간을 내주어 설문을 진행해주시면 감사하겠습니다.</p>
        </div>
        <hr>
        <div class="surveyContent">
            <el-form ref="form" :model="form">
                <el-collapse v-model="activeNames" @change="handleChange">
                    <el-collapse-item title="Consistency" name="1">
                        <el-form-item>
                            <el-radio-group v-model="form.category">
                                <div class="surveytitle">
                                    본인의 직위를 선택해주세요
                                </div>
                                <el-radio label="Option A" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option B" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option C" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option D" style="display: block; margin-top:1.5em;"></el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-collapse-item>

                    <el-collapse-item title="Feedback" name="2">
                        <el-form-item>
                            <el-radio-group v-model="form.category">
                                <div class="surveytitle">
                                    본인의 직위를 선택해주세요
                                </div>
                                <el-radio label="Option A" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option B" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option C" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option D" style="display: block; margin-top:1.5em;"></el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-collapse-item>

                    <el-collapse-item title="Efficiency" name="3">
                        <el-form-item>
                            <el-radio-group v-model="form.category">
                                <div class="surveytitle">
                                    본인의 직위를 선택해주세요
                                </div>
                                <el-radio label="Option A" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option B" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option C" style="display: block; margin-top:1.5em;"></el-radio>
                                <el-radio label="Option D" style="display: block; margin-top:1.5em;"></el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-collapse-item>
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

            <!-- <hr>
            <div class="category">
                <el-collapse v-model="activeNames" @change="handleChange">
                    <el-collapse-item title="Consistency" name="1">
                        <div class="surveytitle">
                            본인의 직위를 선택해주세요
                        </div>
                        <div class="answer">
                            <el-radio v-model="radio" label="1" style="display: block; margin-top:1.5em;">Option A</el-radio>
                            <el-radio v-model="radio" label="2" style="display: block; margin-top:1.5em;">Option B</el-radio>
                            <el-radio v-model="radio" label="3" style="display: block; margin-top:1.5em;">Option C</el-radio>
                            <el-radio v-model="radio" label="4" style="display: block; margin-top:1.5em;">Option D</el-radio>
                            <el-radio v-model="radio" label="5" style="display: block; margin-top:1.5em;">Option E</el-radio>
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="Feedback" name="2">
                        <div class="surveytitle">
                            본인의 나이를 선택해주세요
                        </div>
                        <div class="answer">
                            <el-radio v-model="radio" label="1" style="display: block; margin-top:1.5em;">Option A</el-radio>
                            <el-radio v-model="radio" label="2" style="display: block; margin-top:1.5em;">Option B</el-radio>
                            <el-radio v-model="radio" label="3" style="display: block; margin-top:1.5em;">Option C</el-radio>
                            <el-radio v-model="radio" label="4" style="display: block; margin-top:1.5em;">Option D</el-radio>
                            <el-radio v-model="radio" label="5" style="display: block; margin-top:1.5em;">Option E</el-radio>
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="Efficiency" name="3">
                        <div class="surveytitle">
                            본인의 직위를 선택해주세요
                        </div>
                        <div class="answer">
                            <el-radio v-model="radio" label="1" style="display: block; margin-top:1.5em;">Option A</el-radio>
                            <el-radio v-model="radio" label="2" style="display: block; margin-top:1.5em;">Option B</el-radio>
                            <el-radio v-model="radio" label="3" style="display: block; margin-top:1.5em;">Option C</el-radio>
                            <el-radio v-model="radio" label="4" style="display: block; margin-top:1.5em;">Option D</el-radio>
                            <el-radio v-model="radio" label="5" style="display: block; margin-top:1.5em;">Option E</el-radio>
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="Controllability" name="4">
                        <div class="surveytitle">
                            원하는 선택지를 골라주세요
                        </div>
                        <div class="answer">
                            <el-radio v-model="radio" label="1" style="display: block; margin-top:1.5em;">Option A</el-radio>
                            <el-radio v-model="radio" label="2" style="display: block; margin-top:1.5em;">Option B</el-radio>
                            <el-radio v-model="radio" label="3" style="display: block; margin-top:1.5em;">Option C</el-radio>
                            <el-radio v-model="radio" label="4" style="display: block; margin-top:1.5em;">Option D</el-radio>
                            <el-radio v-model="radio" label="5" style="display: block; margin-top:1.5em;">Option E</el-radio>
                        </div>
                    </el-collapse-item>
                </el-collapse>
                
            </div> -->
        </div>
        <div style="text-align:center;">
            <img class="logo" src="@/assets/smalllogo.png" />
        </div>     
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        form:{
            category: [],
        },
        radio: '1'
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
    register(data){
        console.log(data);
      }

    }
  }
</script>

<style scoped>

.surveyWrapper{
    background-color: navy;
}
.surveyDes{
    margin-top: 0.5em;
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

