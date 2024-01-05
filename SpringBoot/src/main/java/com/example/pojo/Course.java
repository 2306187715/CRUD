package com.example.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 课程实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id; // 课程id
    private String name; // 课程名称
    private Integer teacherId; // 老师id
    private short credit; // 学分
    private short hours; //学时
    private short semesterId; //学期id
    private short startWeek; //课程开始周
    private short endWeek;   //课程结束周
    private LocalTime startTime; //开始时间
    private LocalTime endTime;   //结束时间
    private short dayOfWeek; //上课日期 星期一……
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
