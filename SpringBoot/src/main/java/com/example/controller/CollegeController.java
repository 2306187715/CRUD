package com.example.controller;


import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.College;
import com.example.service.CollegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/colleges")
public class CollegeController {
    
    @Autowired
    private CollegeService collegeService;
    
    /**
     * 分页查询学院
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize
    ){
        log.info("查询所有课程信息");
        PageBean pageBean = collegeService.page(page,pageSize);
        return Result.success(pageBean);
    }
    
    /**
     * 查询所有学院
     */
    @GetMapping("/list")
    public Result selectAll(){
        log.info("查询所有学院");
        List<College> collegeList = collegeService.list();
        return Result.success(collegeList);
    }
    
    /**
     * 删除某个学院
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除学院：{}",id);
        
        collegeService.delete(id);
        
        return Result.success();
    }
    
    /**
     * 新增学院
     */
    @PostMapping
    public Result add(@RequestBody College college){
        log.info("新增学院：{}", college.getName());
        
        collegeService.add(college);
        
        return Result.success();
    }
    
    /**
     * 根据id查询学院
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询学院：{}",id);
        
        College college = collegeService.getById(id);
        
        return Result.success(college);
    }
    
    /**
     * 根据id修改学院
     */
    @PutMapping
    public Result update(@RequestBody College college){
        log.info("修改学院：{},{}", college.getId(), college.getName());
        
        collegeService.update(college);
        
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
