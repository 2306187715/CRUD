package com.example.service.impl;

import com.example.mapper.TeacherMapper;
import com.example.pojo.PageBean;
import com.example.pojo.Teacher;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherServiceImpl implements com.example.service.TeacherService {
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    
    @Override
    public PageBean page(Integer page, Integer pageSize, Integer id, Integer collegeId, Integer deptId) {
        PageHelper.startPage(page, pageSize);
        
        List<Teacher> teacherList = teacherMapper.list(id, collegeId, deptId);
        Page<Teacher> teacherPage = (Page<Teacher>) teacherList;
        
        PageBean pageBean = new PageBean(teacherPage.getTotal(), teacherPage.getResult());
        
        return pageBean;
    }
    
    @Override
    public List<Teacher> list() {
        return teacherMapper.listAll();
    }
    
    @Override
    public void delete(Integer[] ids) {
        teacherMapper.delete(ids);
    }
    
    @Override
    public void insert(Teacher teacher) {
        
        teacher.setPassword(teacher.getPassword() == null ? "teacher" : teacher.getPassword());
        teacher.setUpdateTime(LocalDateTime.now());
        teacher.setCreateTime(LocalDateTime.now());
        
        teacherMapper.insert(teacher);
    }
    
    @Override
    public Teacher getById(Integer id) {
        return teacherMapper.getById(id);
    }
    
    @Override
    public void update(Teacher teacher) {
        teacher.setUpdateTime(LocalDateTime.now());
        
        teacherMapper.update(teacher);
    }
}
