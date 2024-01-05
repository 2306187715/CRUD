//api.js

// 引入 axios
import axios from "axios";
//引入 element-ui 信息
import { Message } from "element-ui";
import router from "@/router";
import { getToken, removeToken } from "./auth";

// 创建axios实例对象
const http = axios.create({
    baseURL: '/url', // 服务器后端地址
    timeout: 10000,
})

//传送json格式的post请求
export const postRequest = (url, params) => {
    return http.post(
        `${url}`,
        params,
    )
}

//传送json格式的put请求
export const putRequest = (url, params) => {
    return http.put(
        `${url}`,
        params,
    )
}

//传送json格式的get请求
export const getRequest = (url) => {
    return http.get(
        `${url}`,
    )
}

//传送json格式的delete请求
export const deleteRequest = (url, params) => {
    return http.delete(
        `${url}`,
        params,
    )
}




// axios请求拦截器
http.interceptors.request.use(
    config => {
        let token = getToken()
        if (token) {
            config.headers['token'] = token;
        }
        return config;
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)


//axios响应拦截器
http.interceptors.response.use(res => {
    if (res.data.code === 0 && res.data.msg === 'NOT_LOGIN') {// 返回登录页面
        console.log('用户为登录, 直接跳转至登录页面');
        removeToken();
        router.replace('/login');
        return res;
    } else {
        return res;
    }
},

    error => {
        console.log('err' + error)
        let { message } = error;
        if (message == "Network Error") {
            message = "后端接口连接异常";
        }
        else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        }
        else if (message.includes("Request failed with status code")) {
            message = "系统接口" + message.substr(message.length - 3) + "异常";
        }
        return Promise.reject(error)

    })