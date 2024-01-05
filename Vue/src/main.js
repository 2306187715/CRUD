import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import ElementUI from 'element-ui';
import { getRole, getToken } from './utils/auth'
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import "babel-polyfill";
import nprogress from 'nprogress'

// ------------------------------
//引入封装好的请求
import { postRequest } from "@/utils/request.js";
import { putRequest } from "@/utils/request.js";
import { getRequest } from "@/utils/request.js";
import { deleteRequest } from "@/utils/request.js";

//插件形式使用请求
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
// --------------------------------





Vue.config.productionTip = false
Vue.use(ElementUI, {
    size: 'small'
});
Vue.prototype.$axios = axios;

// 前置守卫
router.beforeEach((to, from, next) => {
    // nprogress.start();
    const token = getToken();
    const role = getRole();
    if (!token && to.path !== '/login') {
        // this.$router.replace('/login')
        next('/login');
        // nprogress.done();
    } else if (to.meta.role && to.meta.role.indexOf(role) === -1) {
        next('/403');
        // nprogress.done();
    } else {
        next();
    }
})


new Vue({
    router,
    render: h => h(App)
}).$mount('#app')