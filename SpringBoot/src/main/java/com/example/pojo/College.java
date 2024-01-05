package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学院实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class College {
    private Integer id; // ID
    private String name; // 学院名称
    private Integer number; //学员人数
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
