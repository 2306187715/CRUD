import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize, id, collegeId, deptId) {
    return getRequest(
        '/teachers?page=' + page + '&pageSize=' + pageSize + '&id=' + id + '&collegeId=' + collegeId + '&deptId=' + deptId
    )
}
/**
 * 按id查询教师
 * @param {*} id 
 * @returns 
 */
export function selectById(id) {
    return getRequest('/teachers/' + id)
}
export function selectAllTeachers(){
    return getRequest('/teachers/list')
}

export function add(data) {
    return postRequest('/teachers', data)
}
export function update(data) {
    return putRequest('/teachers', data)
}
export function deleteById(id) {
    return deleteRequest('/teachers/' + id)
}
export function getInfo(){
    return getRequest('/teachers/getInfo')
}