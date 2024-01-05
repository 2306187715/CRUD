package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.Teacher;

import java.util.List;

public interface TeacherService {
    /**
     * 按页查询数据
     */
    PageBean page(Integer page, Integer pageSize, Integer id, Integer collegeId, Integer deptId);
    
    /**
     * 查询全部教师
     */
    List<Teacher> list();
    
    /**
     * 删除教师
     */
    void delete(Integer[] ids);
    
    /**
     * 插入教师
     */
    void insert(Teacher teacher);
    
    /**
     * 按id查询
     */
    Teacher getById(Integer id);
    
    /**
     * 修改教师
     */
    void update(Teacher teacher);
    

}
