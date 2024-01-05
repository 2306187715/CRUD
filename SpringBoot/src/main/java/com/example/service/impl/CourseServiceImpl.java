package com.example.service.impl;

import com.example.mapper.CourseMapper;
import com.example.mapper.ElectiveMapper;
import com.example.pojo.Course;
import com.example.pojo.PageBean;
import com.example.pojo.Stu;
import com.example.service.CourseService;
import com.example.service.ElectiveService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ElectiveMapper electiveMapper;
    // 调用ElectiveServiceImpl中的方法
    final private ElectiveServiceImpl electiveService;
    public CourseServiceImpl(ElectiveServiceImpl electiveService) {
        this.electiveService = electiveService;
    }
    
    
    /**
     * 分页查询
     */
    @Override
    public PageBean page(Integer page, Integer pageSize, String name,
                         Integer teacherId, Integer semesterId,
                         Integer[] weekList, Integer dayOfWeek,
                         LocalTime startTime,
                         LocalTime endTime) {
        PageHelper.startPage(page, pageSize);
        
        Integer startWeek = weekList[0];
        Integer endWeek = weekList[1];
        
        List<Course> courseList = courseMapper.list(name, teacherId, semesterId, startWeek, endWeek, dayOfWeek, startTime, endTime);
        Page<Course> coursePage = (Page<Course>) courseList;
        
        
        PageBean pageBean = new PageBean(coursePage.getTotal(), coursePage.getResult());
        return pageBean;
        
    }
    
    /**
     * 根据id删除课程
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        courseMapper.deleteById(id);
    }
    
    /**
     * 新增课程
     *
     * @param course
     */
    @Override
    public void add(Course course) {
        
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        
        courseMapper.insert(course);
        
        
    }
    
    /**
     * 根据id查询课程
     *
     * @param id
     */
    @Override
    public Course getById(Integer id) {
        return courseMapper.getById(id);
    }
    
    /**
     * 根据id修改课程
     *
     * @param course
     */
    @Override
    public void update(Course course) {
        short semesterId = course.getSemesterId();
        
        // 获取该课程的所有已选学生
        Integer[] selectedStudents = electiveMapper.getStuByCourseIdAndSemesterId(course.getId(), semesterId);
        
        // 检查新的上课时间是否与学生已选的其他课程冲突
        for (Integer stuId : selectedStudents) {
            
            // 调用ElectiveServiceImpl的逻辑
            List<Course> existingCourses = electiveService.getExistingCourses(stuId, semesterId);
            existingCourses.removeIf(course1 -> course1.getId().equals(course.getId()));
            
            if (electiveService.isCourseConflict(existingCourses, course)) {
                throw new RuntimeException("学生已选的课程与新的上课时间冲突");
            } else {
                course.setUpdateTime(LocalDateTime.now());
                courseMapper.update(course);
            }
        }
    }
    
    @Override
    public List<Course> list() {
        return courseMapper.selectAll();
    }
    
    
}
