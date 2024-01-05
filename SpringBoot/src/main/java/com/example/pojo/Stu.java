package com.example.pojo;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stu {
    @Alias("学号")
    private Integer id; // 学号
    @Alias("姓名")
    private String name; // 姓名
    @Alias("密码")
    private String password; // 密码
    @Alias("性别")
    private Short gender; // 性别 , 1 男, 2 女
    @Alias("照片")
    private String image; // 图像url
    @Alias("生日")
    private LocalDate birthday; // 生日
    @Alias("学院")
    private Integer collegeId; // 学院id
    @Alias("创建时间")
    private LocalDateTime createTime; // 创建时间
    @Alias("修改时间")
    private LocalDateTime updateTime; // 修改时间
}
