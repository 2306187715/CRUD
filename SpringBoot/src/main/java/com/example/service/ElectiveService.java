package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.Elective;
import com.example.pojo.Schedule;

import java.util.ArrayList;
import java.util.List;

public interface ElectiveService {
    /**
     * 按页查询数据
     */
    PageBean page(Integer page, Integer pageSize, Integer semesterId, Integer courseId, Integer stuId, Integer[] scoreList);
    
    /**
     * 删除选课
     *
     * @param ids
     */
    void delete(Integer[] ids);
    
    /**
     * 插入选课
     *
     * @param elective
     */
    void insert(Elective elective);
    
    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    Elective getById(Integer id);
    
    /**
     * 修改选课
     *
     * @param elective
     */
    void update(Elective elective);
    
    /**
     * 查询学生stu所有选课
     *
     * @return
     */
    ArrayList<Schedule> getScheduleById(Elective id);
    
    List<Schedule> getScheduleByTeacherId(Integer teacherId, short semesterId);
}
