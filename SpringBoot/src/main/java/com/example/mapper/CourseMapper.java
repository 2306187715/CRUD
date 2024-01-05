package com.example.mapper;

import com.example.pojo.Course;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
import java.util.List;

@Mapper
public interface CourseMapper {
    /**
     * 查询所有课程
     */
    List<Course> list(String name, Integer teacherId, Integer semesterId,
                      Integer startWeek, Integer endWeek, Integer dayOfWeek, LocalTime startTime, LocalTime endTime);
    
    /**
     * 根据id删除课程
     */
    void deleteById(Integer id);
    
    /**
     * 新增课程
     */
    void insert(Course course);
    
    /**
     * 根据id查询课程
     */
    Course getById(Integer id);
    
    /**
     * 根据id修改课程
     */
    void update(Course course);
    
    @Select("select id,name from course")
    List<Course> selectAll();
    
    @Select("select semester_id from course where id=#{id}")
    short selectSemesterIdById(Integer id);
    
    @Select("select id from course where teacher_id=#{teacherId} and semester_id=#{semesterId}")
    Integer[] selectCourseByTeacherId(Integer teacherId, short semesterId);
}
