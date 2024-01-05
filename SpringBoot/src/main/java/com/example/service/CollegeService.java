package com.example.service;

import com.example.pojo.College;
import com.example.pojo.PageBean;

import java.util.List;

public interface CollegeService {
    /**
     * 分页查询
     * @return
     */
    PageBean page(Integer page,Integer pageSize);
    
    /**
     * 删除学院
     * @param id
     */
    void delete(Integer id);
    
    /**
     * 新增学院
     * @param college
     */
    void add(College college);
    
    /**
     * 根据id查找学院
     * @param id
     * @return
     */
    College getById(Integer id);
    
    /**
     * 修改学院
     * @param college
     */
    void update(College college);
    
    List<College> list();
}
