package com.example.controller;

import com.example.pojo.*;
import com.example.service.ElectiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/electives")
public class ElectiveController {
    
    @Autowired
    private ElectiveService electiveService;
    
    
    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Integer semesterId, Integer courseId, Integer stuId, Integer[] scoreList
                       ) {
        log.info("分页查询所有选课数据，{}，{},{},{}，{},{}", page, pageSize, semesterId, courseId, stuId, scoreList);
        
        // 封装到pageBean数据封装类中响应
        PageBean pageBean = electiveService.page(page, pageSize, semesterId, courseId, stuId, scoreList);
        
        return Result.success(pageBean);
    }
    
    /**
     * 根据id删除选课
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer[] ids) {
        log.info("删除选课：{}", (Object[]) ids);
        
        electiveService.delete(ids);
        
        return Result.success();
    }
    
    /**
     * 新增选课
     *
     * @param elective
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Elective elective) {
        log.info("插入选课数据:{}", elective.getStuId());
        
        electiveService.insert(elective);
        
        return Result.success();
    }
    
    /**
     * 根据id查询选课
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("获取选课id:{}", id);
        
        Elective elective = electiveService.getById(id);
        
        return Result.success(elective);
    }
    
    /**
     * 修改选课信息
     *
     * @param elective
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Elective elective) {
        log.info("更新数据：{}", elective);
        
        electiveService.update(elective);
        
        return Result.success();
    }
    
    /**
     * 按学生id查询选课记录。用于生成学期课表
     */
    @PostMapping("/stu/schedule")
    public Result getSchedule(@RequestBody Elective elective){
        log.info("查询学生课表：{}",elective.getStuId());
        List<Schedule> electiveList = electiveService.getScheduleById(elective);
        
        return Result.success(electiveList);
    }
    
    /**
     * 按教师id查询选课记录。用于生成学期课表
     */
    @PostMapping("/teacher/schedule")
    public Result getScheduleByTeacherId(@RequestBody Course course){
        log.info("查询教师课表：{},{}",course.getTeacherId(),course.getSemesterId());
        List<Schedule> electiveList = electiveService.getScheduleByTeacherId(course.getTeacherId(),course.getSemesterId());
        return Result.success(electiveList);
    }
    
    /**
     * created用于鉴权
     */
    @GetMapping("/getInfo")
    public Result getInfo(){
        return Result.success();
    }
}
