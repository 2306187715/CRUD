import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/',
    component: resolve => require(['../components/common/Home.vue'], resolve),
    meta: { title: 'home' },
    children: [
      {
        path: '/dashboard',
        component: resolve => require(['../views/DashboardView.vue'], resolve),
        meta: { title: '首页' }
      },
      // {
      //   path: '/icon',
      //   component: resolve => require(['../components/page/Icon.vue'], resolve),
      //   meta: { title: '自定义图标' }
      // },
      {
        path: '/stu',
        component: resolve => require(['../views/StuView.vue'], resolve),
        meta: { title: '学生信息', role: ['admin'] }
      },
      {
        path: '/college',
        component: resolve => require(['../views/CollegeView.vue'], resolve),
        meta: { title: '学院信息' }
      },
      {
        path: '/teacher',
        component: resolve => require(['../views/TeacherView.vue'], resolve),
        meta: { title: '教师信息', role: ['admin'] }
      },
      {
        path: '/dept',
        component: resolve => require(['../views/DeptView.vue'], resolve),
        meta: { title: '部门信息', role: ['admin', 'teacher'] }
      },
      {
        path: '/course',
        component: resolve => require(['../views/CourseView.vue'], resolve),
        meta: { title: '课程信息', role: ['stu', 'teacher', 'admin'] }
      },
      {
        path: '/elective',
        component: resolve => require(['../views/ElectiveView.vue'], resolve),
        meta: { title: '选课信息', role: ['admin', 'teacher'] }
      },
      {
        path: '/semester',
        component: resolve => require(['../views/SemesterView.vue'], resolve),
        meta: { title: '学期信息', role: ['admin', 'teacher'] }
      },
      {
        path: '/schedule',
        component: resolve => require(['../views/ScheduleView.vue'], resolve),
        meta: { title: '课表信息', role: ['stu', 'teacher'] }
      },
      {
        path: '/404',
        component: resolve => require(['../views/404.vue'], resolve),
        meta: { title: '404' }
      },
      {
        path: '/403',
        component: resolve => require(['../views/403.vue'], resolve),
        meta: { title: '403' }
      }
    ]
  },
  {
    path: '/login',
    component: resolve => require(['../views/LoginView.vue'], resolve)
  },
  {
    path: '*',
    redirect: '/404'
  }
]

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})



export default router
