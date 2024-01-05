package com.example.service.impl;

import com.example.mapper.SemesterMapper;
import com.example.pojo.Semester;
import com.example.pojo.PageBean;
import com.example.service.SemesterService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {
    
    @Autowired
    private SemesterMapper semesterMapper;
    
    /**
     * 分页查询
     */
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        
        List<Semester> semesterList = semesterMapper.list();
        Page<Semester> semesterPage = (Page<Semester>) semesterList;
        
        PageBean pageBean = new PageBean(semesterPage.getTotal(), ((Page<Semester>) semesterList).getResult());
        
        return pageBean;
        
    }
    
    /**
     * 查询所有学期
     */
    @Override
    public List<Semester> list() {
        return semesterMapper.list();
    }
    
    /**
     * 根据id删除学期
     */
    @Override
    public void delete(Integer id) {
        semesterMapper.deleteById(id);
    }
    
    /**
     * 新增学期
     */
    @Override
    public void add(Semester semester) {
        semester.setCreateTime(LocalDateTime.now());
        semester.setUpdateTime(LocalDateTime.now());
        
        semesterMapper.add(semester);
    }
    
    /**
     * 根据id查询学期
     */
    @Override
    public Semester getById(Integer id) {
        return semesterMapper.getById(id);
    }
    
    /**
     * 根据id修改学期
     */
    @Override
    public void update(Semester semester) {
        semester.setUpdateTime(LocalDateTime.now());
        
        semesterMapper.update(semester);
    }
    
    
}
