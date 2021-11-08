// 설문 제작
// 설문 제작
// import Editform from "../views/Editform.vue";
// 설문 문항 편집
import Createform from "@/views/Createform.vue";
import SurveyCreateComplete from "@/views/SurveyCreateComplete.vue";
import QuestionEdit from "@/views/question/QuestionEdit.vue";
import filter from "@/views/Editform.vue";
import TestPage from "@/views/test.vue";
import Vue from "vue";
import VueRouter from "vue-router";
import Form from "../views/Res/SurveyForm.vue";
import SurveyDetail from "@/views/survey/SurveyDetail.vue";
import Finish from "../views/Res/SurveyFinish.vue";
import DefaultTemplateList from "../views/DefaultTemplateList.vue";
import RecentTemplateList from "../views/RecentTemplateList.vue";
import Main from "../views/main/main.vue";
import TemplatePreview from "@/views/TemplatePreview";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "main",
    component: Main,
  },
  {
    path: "/question/questionedit",
    name: "QuestionEdit",
    component: QuestionEdit,
  },
  {
    path: "/form/createform",
    name: "FromCreateion",
    component: Createform,
  },
  {
    path: "/form/:linkCode",
    name: "Form",
    component: Form,
  },
  {
    path: "/survey/complete",
    name: "SurveyCreateComplete",
    component: SurveyCreateComplete,
  },
  {
    path: "/test",
    name: "TestPage",
    component: TestPage,
  },
  {
    path: "/filter",
    name: "filter",
    component: filter,
  },
  {
    path: "/survey/detail",
    name: "SurveyDetail",
    component: SurveyDetail,
  },
  {
    path: "/finish",
    name: "Finish",
    component: Finish,
  },
  {
    path: "/template/default",
    name: "DefaultTemplateList",
    component: DefaultTemplateList,
  },
  {
    path: "/template/:templateId/preview",
    name: "TemplatePreview",
    component: TemplatePreview,
  },
  {
    path: "/template/recent",
    name: "RecentTemplateList",
    component: RecentTemplateList,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
