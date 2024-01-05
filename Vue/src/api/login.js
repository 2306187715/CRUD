import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

export function doLogin(data) {
    return postRequest('/login', data)
}