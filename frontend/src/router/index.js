// // 설문 제작
// // 설문 제작
// import Editform from "../views/Editform.vue";
// // 설문 문항 편집
import Createform from "@/views/Createform.vue";

import QuestionEdit from "@/views/question/QuestionEdit.vue";
import Vue from "vue";
import VueRouter from "vue-router";
// import Home from "../views/Home.vue";
import Form from "../views/Res/SurveyForm.vue";

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
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
