package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private String title;
    private short dayOfWeek;
    private short startTime;
    private short endTime;
    private Integer teacherId;
}
