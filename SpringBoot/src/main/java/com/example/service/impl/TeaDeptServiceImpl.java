package com.example.service.impl;

import com.example.mapper.TeaDeptMapper;
import com.example.pojo.Dept;
import com.example.pojo.PageBean;
import com.example.service.TeaDeptService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeaDeptServiceImpl implements TeaDeptService {
    
    @Autowired
    private TeaDeptMapper teaDeptMapper;
    
    /**
     * 分页查询
     */
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        
        List<Dept> deptList = teaDeptMapper.list();
        Page<Dept> deptPage = (Page<Dept>) deptList;
        
        PageBean pageBean = new PageBean(deptPage.getTotal(), ((Page<Dept>) deptList).getResult());
        
        return pageBean;
        
    }
    
    /**
     * 查询所有部门
     */
    @Override
    public List<Dept> list() {
        return teaDeptMapper.list();
    }
    
    /**
     * 根据id删除部门
     */
    @Override
    public void delete(Integer id) {
        teaDeptMapper.deleteById(id);
    }
    
    /**
     * 新增部门
     */
    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        
        teaDeptMapper.add(dept);
    }
    
    /**
     * 根据id查询部门
     */
    @Override
    public Dept getById(Integer id) {
        return teaDeptMapper.getById(id);
    }
    
    /**
     * 根据id修改部门
     */
    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        
        teaDeptMapper.update(dept);
    }
    
    
}
