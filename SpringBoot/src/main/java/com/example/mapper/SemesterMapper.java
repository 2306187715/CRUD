package com.example.mapper;

import com.example.pojo.Semester;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SemesterMapper {
    /**
     * 查询所有部门
     *
     * @return
     */
    @Select("select id, name, start_date, end_date, create_time, update_time from semester")
    List<Semester> list();
    
    /**
     * 根据id删除部门
     *
     * @param id
     */
    @Delete("delete from semester where id = #{id}")
    void deleteById(Integer id);
    
    /**
     * 新增部门
     *
     * @param semester
     */
    @Insert("insert into semester(name, start_date, end_date, create_time, update_time) values(#{name},#{startDate},#{endDate},#{createTime},#{updateTime}) ")
    void add(Semester semester);
    
    /**
     * 根据id查询部门
     *
     * @param id
     */
    @Select("select id, name, start_date, end_date, create_time, update_time from semester where id = #{id}")
    Semester getById(Integer id);
    
    /**
     * 根据id修改部门
     *
     * @param semester
     */
    @Update("update semester set name=#{name},start_date=#{startDate},end_date=#{endDate},update_time = #{updateTime} where id = #{id}")
    void update(Semester semester);
    
}
