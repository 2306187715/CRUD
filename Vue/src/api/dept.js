import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize) {
    return getRequest(
        '/depts?page=' + page + '&pageSize=' + pageSize
    )
}
/**
 * 按id查询部门
 * @param {*} id 
 * @returns 
 */
export function selectById(id){
    return getRequest('/depts/'+id)
}

/**
 * 全部查询
 * @returns depts
 */
export function selectAllDepts(){
    return getRequest('/depts/list')
}

export function add(data){
    return postRequest('/depts',data)
}
export function update(data){
    return putRequest('/depts',data)
}
export function deleteById(id){
    return deleteRequest('/depts/'+id)
}
export function getInfo(){
    return getRequest('/depts/getInfo')
}