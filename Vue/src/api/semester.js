import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize) {
    return getRequest(
        '/semesters?page=' + page + '&pageSize=' + pageSize
    )
}
/**
 * 按id查询学期
 * @param {*} id 
 * @returns 
 */
export function selectById(id){
    return getRequest('/semesters/'+id)
}
export function selectAllSemesters(){
    return getRequest('/semesters/list')
}

export function add(data){
    return postRequest('/semesters',data)
}
export function update(data){
    return putRequest('/semesters',data)
}
export function deleteById(id){
    return deleteRequest('/semesters/'+id)
}
export function getInfo(){
    return getRequest('/semesters/getInfo')
}