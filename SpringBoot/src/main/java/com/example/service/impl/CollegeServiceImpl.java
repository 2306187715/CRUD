package com.example.service.impl;

import com.example.mapper.CollegeMapper;
import com.example.pojo.College;
import com.example.pojo.College;
import com.example.pojo.PageBean;
import com.example.service.CollegeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    
    @Autowired
    private CollegeMapper collegeMapper;
    
    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        
        List<College> courseList = collegeMapper.list();
        Page<College> coursePage = (Page<College>) courseList;
        
        return new PageBean(coursePage.getTotal(), coursePage.getResult());
        
    }
    
    /**
     * 查询所有学院
     */
    @Override
    public List<College> list() {
        return collegeMapper.list();
    }
    
    /**
     * 根据id删除学院
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        collegeMapper.deleteById(id);
    }
    
    /**
     * 新增学院
     *
     * @param college
     */
    @Override
    public void add(College college) {
        college.setCreateTime(LocalDateTime.now());
        college.setUpdateTime(LocalDateTime.now());
        
        collegeMapper.insert(college);
    }
    
    /**
     * 根据id查询学院
     *
     * @param id
     */
    @Override
    public College getById(Integer id) {
        return collegeMapper.getById(id);
    }
    
    /**
     * 根据id修改学院
     *
     * @param college
     */
    @Override
    public void update(College college) {
        college.setUpdateTime(LocalDateTime.now());
        
        collegeMapper.update(college);
    }
    
    
}
