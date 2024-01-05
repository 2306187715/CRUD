package com.example.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 选修课实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Elective {
    private Integer id; // ID
    private Integer stuId; // 学生id
    private Integer courseId; // 课程id
    private short semesterId; // 学期id
    private short score; //考试分数
    private short status; //选课记录状态
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
