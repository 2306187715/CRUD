import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize, semesterId, courseId, stuId, scoreList) {
    return getRequest(
        '/electives?page=' + page + '&pageSize=' + pageSize + '&semesterId=' + semesterId + '&courseId=' + courseId + '&stuId=' + stuId + '&scoreList=' + scoreList
    )
}
/**
 * 按id查询选课记录
 * @param {*} id 
 * @returns 
 */
export function selectById(id) {
    return getRequest('/electives/' + id)
}

export function add(data) {
    return postRequest('/electives', data)
}
export function update(data) {
    return putRequest('/electives', data)
}
export function deleteById(id) {
    return deleteRequest('/electives/' + id)
}
export function getScheduleByStuId(data){
    return postRequest('/electives/stu/schedule',data)
}
export function getScheduleByTeacherId(data){
    return postRequest('/electives/teacher/schedule',data)
}


export function getInfo(){
    return getRequest('/electives/getInfo')
}