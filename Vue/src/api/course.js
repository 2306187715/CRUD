import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

/**
 * 分页查询
 * @returns 
 */
export function page(page, pageSize, name, teacherId, semesterId, weekList, dayOfWeek, startTime, endTime) {
    return getRequest(
        '/courses?page=' + page + '&pageSize=' + pageSize + '&name=' + name + '&teacherId=' + teacherId + '&semesterId=' + semesterId +'&weekList=' + weekList +'&dayOfWeek=' + dayOfWeek + '&startTime=' + startTime + '&endTime=' + endTime
    )
}
/**
 * 按id查询课程
 * @param {*} id 
 * @returns 
 */
export function selectById(id) {
    return getRequest('/courses/' + id)
}
export function selectAllCourses(){
    return getRequest('/courses/list')
}

export function add(data) {
    return postRequest('/courses', data)
}
export function update(data) {
    return putRequest('/courses', data)
}
export function deleteById(id) {
    return deleteRequest('/courses/' + id)
}
export function getInfo(){
    return getRequest('/courses/getInfo')
}