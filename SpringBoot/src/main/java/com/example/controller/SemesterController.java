package com.example.controller;

import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.Semester;
import com.example.service.SemesterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/semesters")
public class SemesterController {
    @Autowired
    private SemesterService semesterService;
    
    /**
     * 查询所有学期
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize
    ){
        log.info("查询所有学期信息");
        PageBean pageBean = semesterService.page(page,pageSize);
        return Result.success(pageBean);
    }
    
    /**
     * 查询所有学期
     */
    @GetMapping("/list")
    public Result selectAll(){
        log.info("查询所有学期");
        List<Semester> semesterList = semesterService.list();
        return Result.success(semesterList);
    }
    
    /**
     * 删除某个学期
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除学期：{}",id);
        
        semesterService.delete(id);
        
        return Result.success();
    }
    
    /**
     * 新增学期
     */
    @PostMapping
    public Result add(@RequestBody Semester semester){
        log.info("新增学期：{}", semester.getName());
        
        semesterService.add(semester);
        
        return Result.success();
    }
    
    /**
     * 根据id查询学期
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询学期：{}",id);
        
        Semester semester = semesterService.getById(id);
        
        return Result.success(semester);
    }
    
    /**
     * 根据id修改学期
     */
    @PutMapping
    public Result update(@RequestBody Semester semester){
        log.info("修改学期：{},{}", semester.getId(), semester.getName());
        
        semesterService.update(semester);
        
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
