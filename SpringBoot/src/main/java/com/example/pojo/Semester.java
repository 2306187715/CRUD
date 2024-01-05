package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semester {
    private short id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
