package com.example.service;

import com.example.pojo.Course;
import com.example.pojo.PageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

public interface CourseService {
    /**
     * 返回所有课程
     *
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String name,
                  Integer teacherId, Integer semesterId,
                  Integer[] weekList, Integer dayOfWeek,
                  LocalTime startTime,
                  LocalTime endTime);
    
    /**
     * 删除课程
     *
     * @param id
     */
    void delete(Integer id);
    
    /**
     * 新增课程
     *
     * @param course
     */
    void add(Course course);
    
    /**
     * 根据id查找课程
     *
     * @param id
     * @return
     */
    Course getById(Integer id);
    
    /**
     * 修改课程
     *
     * @param course
     */
    void update(Course course);
    
    List<Course> list();
}
