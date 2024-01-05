package com.example.service;

import com.example.pojo.Semester;
import com.example.pojo.PageBean;

import java.util.List;

public interface SemesterService {
    /**
     * 返回所有部门
     */
    PageBean page(Integer page,Integer pageSize);
    
    /**
     * 删除部门
     */
    void delete(Integer id);
    
    /**
     * 新增部门
     */
    void add(Semester semester);
    
    /**
     * 根据id查找部门
     */
    Semester getById(Integer id);
    
    /**
     * 修改部门
     */
    void update(Semester semester);
    
    List<Semester> list();
}
