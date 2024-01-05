package com.example.service.impl;

import com.example.mapper.StuMapper;
import com.example.pojo.PageBean;
import com.example.pojo.Stu;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StuServiceImpl implements com.example.service.StuService {
    
    @Autowired
    private StuMapper stuMapper;
    
    
    @Override
    public PageBean page(Integer page, Integer pageSize, Integer id, Short gender, Integer collegeId, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);
        
        List<Stu> stuList = stuMapper.list(id, gender, collegeId, begin, end);
        Page<Stu> stuPage = (Page<Stu>) stuList;
        
        PageBean pageBean = new PageBean(stuPage.getTotal(), stuPage.getResult());
        
        return pageBean;
    }
    
    @Override
    public void delete(Integer[] ids) {
        stuMapper.delete(ids);
    }
    
    @Override
    public void insert(Stu stu) {
        
        stu.setPassword(stu.getPassword() == null ? "123456" : stu.getPassword());
        stu.setUpdateTime(LocalDateTime.now());
        stu.setCreateTime(LocalDateTime.now());
        
        stuMapper.insert(stu);
    }
    
    @Override
    public Stu getById(Integer id) {
        return stuMapper.getById(id);
    }
    
    @Override
    public void update(Stu stu) {
        stu.setUpdateTime(LocalDateTime.now());
        
        stuMapper.update(stu);
    }
    
    @Override
    public List<Stu> list() {
        return stuMapper.selectAll();
    }
    
    @Override
    public List<Stu> findAll() {
        return stuMapper.selectAllData();
    }
}
