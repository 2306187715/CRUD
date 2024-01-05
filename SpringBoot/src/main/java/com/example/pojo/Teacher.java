package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 教师实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Integer id; // 职工号
    private String name; // 教师名称
    private String password; //密码
    private short gender; // 性别
    private String image; // 图像url
    private short title; // 职称
    private Integer collegeId; // 学院id
    private Integer deptId; // 部门id
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
