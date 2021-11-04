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
import Main from "../views/main/main.vue";

Vue.use(VueRouter);

const routes = [
  // {
  //   path: "/",
  //   name: "Home",
  //   component: Home,
  // },
  // {
  //   path: "/about",
  //   name: "About",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ "@/views/About.vue"),
  // },
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
    path: "/form",
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
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
