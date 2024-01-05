import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize) {
    return getRequest(
        '/colleges?page=' + page + '&pageSize=' + pageSize
    )
}
/**
 * 按id查询学院
 * @param {*} id 
 * @returns 
 */
export function selectById(id){
    return getRequest('/colleges/'+id)
}

/**
 * 全部查询
 * @returns colleges
 */
export function selectAllColleges(){
    return getRequest('/colleges/list')
}

export function add(data){
    return postRequest('/colleges',data)
}
export function update(data){
    return putRequest('/colleges',data)
}
export function deleteById(id){
    return deleteRequest('/colleges/'+id)
}
export function getInfo(){
    return getRequest('/colleges/getInfo')
}