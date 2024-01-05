package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.Stu;
import com.example.service.StuService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/students")
public class StuController {
    
    @Autowired
    private StuService stuService;
    
    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping
    public Result page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Integer id, Short gender, Integer collegeId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("分页查询所有学生数据，{}，{},{},{},{},{}", page, pageSize, id, gender, begin, end);
        
        // 封装到pageBean数据封装类中响应
        PageBean pageBean = stuService.page(page, pageSize, id, gender, collegeId, begin, end);
        
        return Result.success(pageBean);
    }
    
    
    /**
     * 导入csv
     */
    @PostMapping("/upload")
    public Result handleFileUpload(MultipartFile file) throws Exception {
        List<Stu> stuList = ExcelUtil.getReader(file.getInputStream()).readAll(Stu.class);
        if (!CollectionUtil.isEmpty(stuList)) {
            for (Stu stu : stuList) {
                try {
                    stuService.insert(stu);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        return Result.success();
    }
    
    /**
     * 导出excel
     */
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {
        System.out.println("===============================================================");
        List<Stu> stuList = stuService.findAll();
        if (CollectionUtil.isEmpty(stuList)) {
            return Result.error("暂无数据");
        }
        
        // 定义List
        List<Map<String, Object>> list = new ArrayList<>(stuList.size());
        
        // 定义map，put数据
        for (Stu stu : stuList) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("学号", stu.getId());
            row.put("姓名", stu.getName());
            row.put("性别", stu.getGender());
            row.put("密码", stu.getPassword());
            row.put("生日", stu.getBirthday());
            row.put("学院", stu.getCollegeId());
            row.put("创建时间", stu.getCreateTime());
            row.put("修改时间", stu.getUpdateTime());
            
            list.add(row);
        }
        
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=output.xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        writer.close();
        
        
        return Result.success();
    }
    
    
    /**
     * 查询所有学生
     */
    @GetMapping("/list")
    public Result selectAll() {
        log.info("查询所有学生id&姓名");
        List<Stu> stuList = stuService.list();
        return Result.success(stuList);
    }
    
    /**
     * 根据id删除学生
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer[] ids) {
        log.info("删除学生：{}", (Object[]) ids);
        
        stuService.delete(ids);
        
        return Result.success();
    }
    
    /**
     * 新增学生
     *
     * @param stu
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Stu stu) {
        log.info("插入学生数据:{}", stu);
        
        stuService.insert(stu);
        
        return Result.success();
    }
    
    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("获取学生id:{}", id);
        
        Stu stu = stuService.getById(id);
        
        return Result.success(stu);
    }
    
    /**
     * 修改学生信息
     *
     * @param stu
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Stu stu) {
        log.info("更新数据：{}", stu);
        
        stuService.update(stu);
        
        return Result.success();
    }
    
    /**
     * created用于鉴权
     */
    @GetMapping("/getInfo")
    public Result getInfo() {
        return Result.success();
    }
}
