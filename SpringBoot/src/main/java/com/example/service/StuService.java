package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.Stu;

import java.time.LocalDate;
import java.util.List;

public interface StuService {
    /**
     * 按页查询数据
     *
     * @param page
     * @param pageSize
     * @param id
     * @return
     */
    PageBean page(Integer page, Integer pageSize, Integer id, Short gender, Integer collegeId, LocalDate begin, LocalDate end);
    
    /**
     * 删除学生
     * @param ids
     */
    void delete(Integer[] ids);
    
    /**
     * 插入学生
     * @param stu
     */
    void insert(Stu stu);
    
    /**
     * 按id查询
     * @param id
     * @return
     */
    Stu getById(Integer id);
    
    /**
     * 修改学生
     * @param stu
     */
    void update(Stu stu);
    
    List<Stu> list();
    
    /**
     * 用于导出，获得所有数据
     * @return
     */
    List<Stu> findAll();
}
