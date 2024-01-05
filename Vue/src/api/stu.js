import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize, id, gender, collegeId, begin, end) {
    return getRequest(
        '/students?page=' + page + '&pageSize=' + pageSize + '&id=' + id + '&gender=' + gender + '&collegeId=' + collegeId + '&begin=' + begin + '&end=' + end
    )
}
/**
 * 按id查询学生
 * @param {*} id 
 * @returns 
 */
export function selectById(id) {
    return getRequest('/students/' + id)
}
export function selectAllStu() {
    return getRequest('/students/list')
}

export function add(data) {
    return postRequest('/students', data)
}
export function update(data) {
    return putRequest('/students', data)
}
export function deleteById(id) {
    return deleteRequest('/students/' + id)
}
export function upload(data) {
    return postRequest('/students/upload', data)
}
export function getInfo(){
    return getRequest('/students/getInfo')
}