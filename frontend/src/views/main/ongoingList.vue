<template>
    <div id="ListWrapper">
        <el-table 
            :data="surveyList"
            :default-sort = "{prop: 'startDate', order: 'descending'}"
            style="width: 100%">
            <el-table-column
            prop="title"
            label="설문 제목"
            style="font-weight:600;">
            </el-table-column>
            <el-table-column
            prop="teamName"
            label="담당팀"
            width="180">
            </el-table-column>
            <el-table-column
            prop="creationTime"
            label="시작 날짜"
            sortable
            width="180">
            </el-table-column>
            <el-table-column
            prop="endTime"
            label="종료 날짜"
            sortable
            width="180">
            </el-table-column>
            <el-table-column
            prop="cnt"
            label="응답 수"
            width="120">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
  export default {
    data() {
      return {
        tableList:[],
      }
    },
    computed: {
      ...mapState("list", ["surveyList"]),
    },
    methods:{
    ...mapActions("list", ["getOngoingSurveyList"]),
      getSurveyList(){
          console.log(2);
          console.log(this.surveyList)
          this.tableList = this.surveyList;
      }
    },
    created() {
      this.getOngoingSurveyList();
      this.getSurveyList();
      
    },
  }
</script>

<style scoped>
#ListWrapper{
  padding-left:0;
}
</style>