package com.example.service;

import com.example.pojo.Dept;
import com.example.pojo.PageBean;

import java.util.List;

public interface TeaDeptService {
    /**
     * 返回所有学期
     */
    PageBean page(Integer page,Integer pageSize);
    
    /**
     * 删除学期
     */
    void delete(Integer id);
    
    /**
     * 新增学期
     */
    void add(Dept dept);
    
    /**
     * 根据id查找学期
     */
    Dept getById(Integer id);
    
    /**
     * 修改学期
     */
    void update(Dept dept);
    
    List<Dept> list();
}
