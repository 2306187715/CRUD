package com.example.service.impl;

import com.example.mapper.CourseMapper;
import com.example.mapper.ElectiveMapper;
import com.example.pojo.Course;
import com.example.pojo.PageBean;
import com.example.pojo.Elective;
import com.example.pojo.Schedule;
import com.example.service.ElectiveService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ElectiveServiceImpl implements ElectiveService {
    
    @Autowired
    private ElectiveMapper electiveMapper;
    
    @Autowired
    private CourseMapper courseMapper;
    
    
    @Override
    public PageBean page(Integer page, Integer pageSize, Integer semesterId, Integer courseId, Integer stuId, Integer[] scoreList) {
        PageHelper.startPage(page, pageSize);
        
        Integer startScore = scoreList[0];
        Integer endScore = scoreList[1];
        
        List<Elective> electiveList = electiveMapper.list(semesterId, courseId, stuId, startScore, endScore);
        Page<Elective> electivePage = (Page<Elective>) electiveList;
        
        PageBean pageBean = new PageBean(electivePage.getTotal(), electivePage.getResult());
        
        return pageBean;
    }
    
    @Override
    public void delete(Integer[] ids) {
        electiveMapper.delete(ids);
    }
    
    /**
     * 新增选课记录,2023年最大屎山代码
     */
    @Override
    public void insert(Elective elective) throws RuntimeException {
        // 补充信息
        elective.setSemesterId(courseMapper.selectSemesterIdById(elective.getCourseId()));
        elective.setUpdateTime(LocalDateTime.now());
        elective.setCreateTime(LocalDateTime.now());
        
        // 构建新选课
        Course newCourse = courseMapper.getById(elective.getCourseId());
        
        if (newCourse == null) {
            throw new RuntimeException("课程不存在");
        }
        
        // 抽离参数
        Integer electiveStuId = elective.getStuId();
        short electiveSemesterId = elective.getSemesterId();
        Integer electiveCourseId = elective.getCourseId();
        
        // 获得某学生已经选修的课
        List<Course> existingCourses = getExistingCourses(electiveStuId, electiveSemesterId);

        
        // 判断新选修课与已有课程是否冲突
        if (!isCourseConflict(existingCourses, newCourse)) {
            // 如果是之前退过的课，不执行insert,而是update
            if (electiveMapper.isMarked(electiveCourseId, electiveStuId, electiveSemesterId)) {
                elective.setStatus((short) 0);
                elective.setId(electiveMapper.getIdByStuIdAndCourseId(electiveStuId, electiveCourseId, electiveSemesterId));
                electiveMapper.update(elective);
            } else {
                electiveMapper.insert(elective);
            }
        } else if (existingCourses.stream().anyMatch(course -> course.getId().equals(newCourse.getId()))) {
            // 判断是不是已经选过的课，是就设为选课
            elective.setId(electiveMapper.getIdByStuIdAndCourseId(electiveStuId, electiveCourseId, electiveSemesterId));
            if (electiveMapper.getStatusById(elective.getId()) == 0) {
                throw new RuntimeException("此课程已经选择");
            }
            elective.setStatus((short) 0);
            electiveMapper.update(elective);
        } else {
            throw new RuntimeException("选修课程与已有课程时间冲突");
        }
        
    }
    
    @Override
    public Elective getById(Integer id) {
        return electiveMapper.getById(id);
    }
    
    @Override
    public void update(Elective elective) {
        elective.setUpdateTime(LocalDateTime.now());
        elective.setId(electiveMapper.getIdByStuIdAndCourseId(elective.getStuId(), elective.getCourseId(), elective.getSemesterId()));
        if (elective.getId() == null || electiveMapper.getStatusById(elective.getId()) != 0) {
            throw new RuntimeException("未选修课程或已经退课");
        }
        electiveMapper.update(elective);
    }
    
    @Override
    public ArrayList<Schedule> getScheduleById(Elective elective) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        
        for (Integer i : electiveMapper.selectCourseByStuId(elective.getStuId(), elective.getSemesterId())) {
            Course course = courseMapper.getById(i);
            Schedule schedule = new Schedule();
            schedule.setTitle(course.getName());
            schedule.setDayOfWeek(course.getDayOfWeek());
            schedule.setStartTime(formatTime(course.getStartTime()));
            schedule.setEndTime((short) (formatTime(course.getEndTime()) - 1));
            schedule.setTeacherId(course.getTeacherId());
            
            schedules.add(schedule);
        }
        
        return schedules;
        
    }
    
    @Override
    public List<Schedule> getScheduleByTeacherId(Integer teacherId, short semesterId) {
        
        
        List<Schedule> schedules = new ArrayList<>();
        
        for (Integer i : courseMapper.selectCourseByTeacherId(teacherId, semesterId)) {
            Course course = courseMapper.getById(i);
            Schedule schedule = new Schedule();
            schedule.setTitle(course.getName());
            schedule.setDayOfWeek(course.getDayOfWeek());
            schedule.setStartTime(formatTime(course.getStartTime()));
            schedule.setEndTime((short) (formatTime(course.getEndTime()) - 1));
            schedule.setTeacherId(course.getTeacherId());
            
            schedules.add(schedule);
        }
        
        return schedules;
    }
    
    
    // 判断两个课程是否冲突的方法
    public boolean isCourseConflict(List<Course> existingCourses, Course newCourse) {
        for (Course existingCourse : existingCourses) {
            if (existingCourse.getSemesterId() == (newCourse.getSemesterId()) &&
                    existingCourse.getDayOfWeek() == (newCourse.getDayOfWeek()) &&
                    !(existingCourse.getEndWeek() < newCourse.getStartWeek() || existingCourse.getStartWeek() > newCourse.getEndWeek()) &&
                    !(!(existingCourse.getEndTime().isAfter(newCourse.getStartTime())) || !(existingCourse.getStartTime().isBefore(newCourse.getEndTime())))) {
                return true; // 冲突
            }
        }
        return false; // 不冲突
    }
    
    // 减去中午/晚上休息时间
    private short formatTime(LocalTime time) {
        if (time.getHour() <= 12) {
            return (short) (time.getHour() - 7);
        } else if (time.getHour() <= 18) {
            return (short) (time.getHour() - 9);
        } else {
            return (short) (time.getHour() - 10);
        }
    }
    
    public List<Course> getExistingCourses(Integer stuId, Short semesterId) {
        // 已经选修的课程
        List<Course> existingCourses = new ArrayList<>();
        for (Integer courseId : electiveMapper.selectCourseByStuId(stuId, semesterId)) {
            existingCourses.add(courseMapper.getById(courseId));
        }
        return existingCourses;
    }
    
}
