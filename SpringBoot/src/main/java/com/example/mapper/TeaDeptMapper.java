package com.example.mapper;

import com.example.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeaDeptMapper {
    /**
     * 查询所有学期
     * @return
     */
    @Select("select id, name, create_time, update_time from dept")
    List<Dept> list();
    
    /**
     * 根据id删除学期
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);
    
    /**
     * 新增学期
     * @param dept
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime}) ")
    void add(Dept dept);
    
    /**
     * 根据id查询学期
     * @param id
     */
    @Select("select id, name, create_time,update_time from dept where id = #{id}")
    Dept getById(Integer id);
    
    /**
     * 根据id修改学期
     * @param dept
     */
    @Update("update dept set name=#{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
    
}
