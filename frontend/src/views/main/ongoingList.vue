<template>
    <div id="ListWrapper">
      <div style="float:right; margin-bottom:1%;">
        <el-input
            v-model="search"
            size="mini"
            placeholder="Title to search"
            >
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <el-table 
            :data="surveyList.filter(data => !search || data.title.includes(search))"
            :default-sort = "{prop: 'startDate', order: 'descending'}"
            style="width: 100%">
            <el-table-column
            prop="title"
            label="설문 제목">
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

<style scoped>
#ListWrapper{
  padding-left:0;
}
</style>

<script>
import { mapState, mapActions } from "vuex";
  export default {
    data() {
      return {
        tableList:[],
        search: '',
      }
    },
    computed: {
      ...mapState("list", ["surveyList"]),
    },
    methods:{
    ...mapActions("list", ["getOngoingSurveyList"]),
    },
    created() {
      this.getOngoingSurveyList();
    },
  }
</script>