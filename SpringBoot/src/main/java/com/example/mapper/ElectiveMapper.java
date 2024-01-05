package com.example.mapper;

import com.example.pojo.Elective;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ElectiveMapper {
    
    /**
     pageHelper
     * 分页查询
     */
    List<Elective> list(Integer semesterId, Integer courseId, Integer stuId, Integer startScore, Integer endScore);
    
    /**
     * 批量删除
     * @param ids
     */
    void delete(Integer[] ids);
    
    /**
     * 新增选课记录
     * @param elective
     */
    void insert(Elective elective);
    
    /**
     * 按id查询
     * @param id
     * @return
     */
    Elective getById(Integer id);
    
    /**
     * 更新
     * @param elective
     */
    void update(Elective elective);
    
    /**
     * 查询某个学生所选的课
     */
    @Select("select course_id from elective where stu_id=#{id} and status=0 and semester_id=#{semesterId}")
    List<Integer> selectCourseByStuId(Integer id, short semesterId);
    
    /**
     * 补充id信息，用于选课
     */
    @Select("select id from elective where stu_id=#{stuId} and course_id=#{courseId} and semester_id=#{semesterId}")
    Integer getIdByStuIdAndCourseId(Integer stuId, Integer courseId, short semesterId);
    
    /**
     * 按选课记录id返回选课记录状态
     */
    @Select("select status from elective where id=#{id}")
    Integer getStatusById(Integer id);
    
    @Select("select count(*) from elective where course_id=#{courseId} and stu_id=#{stuId} and semester_id=#{semesterId}")
    boolean isMarked(Integer courseId, Integer stuId, short semesterId);
    
    /**
     * 查询某个学生某学期选修了什么课程（用于对已经被选的课程修改时判断是否冲突）
     */
    @Select("select stu_id from elective where course_id=#{courseId} and semester_id=#{semesterId}")
    Integer[] getStuByCourseIdAndSemesterId(Integer courseId, short semesterId);
    
    
}
