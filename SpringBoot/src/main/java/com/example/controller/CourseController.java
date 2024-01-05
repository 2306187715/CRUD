package com.example.controller;


import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.Course;
import com.example.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 分页查询所有课程
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Integer teacherId, Integer semesterId,
                       Integer[] weekList, Integer dayOfWeek,
                       @DateTimeFormat(pattern = "HH-mm") LocalTime startTime,
                       @DateTimeFormat(pattern = "HH-mm") LocalTime endTime
                       
    ){
        log.info("查询所有课程信息:{},{},{},{},{},{},{},{},{}",page,pageSize,name,teacherId,semesterId,weekList,dayOfWeek,startTime,endTime);
        PageBean pageBean = courseService.page(page,pageSize,name,teacherId,semesterId,weekList,dayOfWeek,startTime,endTime);
        return Result.success(pageBean);
    }
    
    /**
     * 查询所有课程
     */
    @GetMapping("/list")
    public Result selectAll(){
        log.info("查询所有课程id&课程名");
        List<Course> courseList = courseService.list();
        return Result.success(courseList);
    }
    
    /**
     * 删除某个课程
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除课程：{}", id);
        
        courseService.delete(id);
        
        return Result.success();
    }
    
    /**
     * 新增课程
     */
    @PostMapping
    public Result add(@RequestBody Course course) {
        log.info("新增课程：{},{},{}", course.getName(),course.getStartWeek(),course.getEndWeek());
        
        courseService.add(course);
        
        return Result.success();
    }
    
    /**
     * 根据id查询课程
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("查询课程：{}", id);
        
        Course course = courseService.getById(id);
        
        return Result.success(course);
    }
    
    /**
     * 根据id修改课程
     */
    @PutMapping
    public Result update(@RequestBody Course course) {
        log.info("修改课程：{}", course.getId());
        
        courseService.update(course);
        
        return Result.success();
    }
    
    /**
     * created用于鉴权
     */
    @GetMapping("/getInfo")
    public Result getInfo(){
        return Result.success();
    }
}
