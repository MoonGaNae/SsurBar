import Vue from "vue";
import VueRouter from "vue-router";
// 설문 제작 
import Editform from "../views/Editform.vue";
// 설문 문항 편집
import Createform from "../views/Createform.vue";
import Form from "@/views/Res/SurveyForm.vue"

Vue.use(VueRouter);

const routes = [
  {
    path: "/Editform",
    name: "Editform",
    component: Editform,
  },
  {
    path: "/Createform",
    name: "Createform",
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





