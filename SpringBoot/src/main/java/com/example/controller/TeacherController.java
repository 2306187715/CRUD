package com.example.controller;

import com.example.pojo.Dept;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.Teacher;
import com.example.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    
    /**
     * 分页查询
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Integer id, Integer collegeId, Integer deptId
    ) {
        log.info("分页查询所有教师数据，{}，{},{},{},{}", page, pageSize, id, collegeId, deptId);
        
        // 封装到pageBean数据封装类中响应
        PageBean pageBean = teacherService.page(page, pageSize, id, collegeId, deptId);
        
        return Result.success(pageBean);
    }
    
    /**
     * 查询所有教师
     */
    @GetMapping("/list")
    public Result selectAll(){
        log.info("查询所有教师");
        List<Teacher> teacherList = teacherService.list();
        return Result.success(teacherList);
    }
    
    /**
     * 根据id删除教师
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer[] ids) {
        log.info("删除教师：{}", (Object[]) ids);
        
        teacherService.delete(ids);
        
        return Result.success();
    }
    
    /**
     * 新增教师
     */
    @PostMapping
    public Result insert(@RequestBody Teacher teacher) {
        log.info("插入教师数据:{}", teacher);
        
        teacherService.insert(teacher);
        
        return Result.success();
    }
    
    /**
     * 根据id查询教师
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("获取教师id:{}", id);
        
        Teacher teacher = teacherService.getById(id);
        
        return Result.success(teacher);
    }
    
    /**
     * 修改教师信息
     */
    @PutMapping
    public Result update(@RequestBody Teacher teacher) {
        log.info("更新数据：{}", teacher);
        
        teacherService.update(teacher);
        
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
