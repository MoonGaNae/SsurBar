// 설문 제작
// 설문 제작
// import Editform from "../views/Editform.vue";
// 설문 문항 편집
import Createform from "@/views/Createform.vue";
import SurveyCreateComplete from "@/views/SurveyCreateComplete.vue";
import QuestionEdit from "@/views/question/QuestionEdit.vue";
import filter from "@/views/Editform.vue";
import CreatePreview from "@/views/CreatePreview.vue";
import Vue from "vue";
import VueRouter from "vue-router";
import Form from "../views/Res/SurveyForm.vue";
import SurveyDetail from "@/views/survey/SurveyDetail.vue";
import Finish from "../views/Res/SurveyFinish.vue";
import DefaultTemplateList from "../views/DefaultTemplateList.vue";
import RecentTemplateList from "../views/RecentTemplateList.vue";
import Main from "../views/main/main.vue";
import TemplatePreview from "@/views/TemplatePreview";
import Login from "@/views/user/login.vue";
import Result from "@/views/survey/SurveyResultLink.vue";
import SurveyClosed from "../views/Res/SurveyClosed.vue";
import Certification from "../views/user/Certification.vue";

import store from "@/store/";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "main",
    component: Main,
    meta: { requireAuth: true },
  },
  {
    path: "/survey/closed",
    name: "SurveyClosed",
    component: SurveyClosed,
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
    meta: { requireAuth: true },
  },
  {
    path: "/form/:linkCode",
    name: "Form",
    component: Form,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/result/:linkCode",
    name: "ResultLink",
    component: Result,
  },
  {
    path: "/survey/complete",
    name: "SurveyCreateComplete",
    component: SurveyCreateComplete,
    meta: { requireAuth: true },
  },
  {
    path: "/survey/preview",
    name: "CreatePreview",
    component: CreatePreview,
    meta: { requireAuth: true },
  },
  {
    path: "/filter",
    name: "filter",
    component: filter,
    meta: { requireAuth: true },
  },
  {
    path: "/survey/detail",
    name: "SurveyDetail",
    component: SurveyDetail,
    props: true,
    meta: { requireAuth: true },
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
    meta: { requireAuth: true },
  },
  {
    path: "/template/:templateId/preview",
    name: "TemplatePreview",
    component: TemplatePreview,
    meta: { requireAuth: true },
  },
  {
    path: "/template/recent",
    name: "RecentTemplateList",
    component: RecentTemplateList,
    meta: { requireAuth: true },
  },
  {
    path: "/certification",
    name: "Certification",
    component : Certification,
    // meta: { requireAuth: true},
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach(function (to, from, next) {
  if (
    to.matched.some((routeInfo) => {
      return routeInfo.meta.requireAuth;
    })
  ) {
    if (store.state.user.isLogin) {
      // console.log("로그인 됨");
      next();
    } else {
      // console.log("로그인 안됨");
      next("/login");
    }
  } else {
    next();
  }
});

export default router;
