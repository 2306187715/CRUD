package com.example.controller;

import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.Dept;
import com.example.service.TeaDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class TeaDeptController {
    @Autowired
    private TeaDeptService teaDeptService;
    
    /**
     * 查询所有部门
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize
    ){
        log.info("查询所有部门信息");
        PageBean pageBean = teaDeptService.page(page,pageSize);
        return Result.success(pageBean);
    }
    
    /**
     * 查询所有部门
     */
    @GetMapping("/list")
    public Result selectAll(){
        log.info("查询所有部门");
        List<Dept> deptList = teaDeptService.list();
        return Result.success(deptList);
    }
    
    /**
     * 删除某个部门
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除部门：{}",id);
        
        teaDeptService.delete(id);
        
        return Result.success();
    }
    
    /**
     * 新增部门
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门：{}", dept.getName());
        
        teaDeptService.add(dept);
        
        return Result.success();
    }
    
    /**
     * 根据id查询部门
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询部门：{}",id);
        
        Dept dept = teaDeptService.getById(id);
        
        return Result.success(dept);
    }
    
    /**
     * 根据id修改部门
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改部门：{},{}", dept.getId(), dept.getName());
        
        teaDeptService.update(dept);
        
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
