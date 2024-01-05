package com.example.mapper;

import com.example.pojo.Login;
import com.example.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StuMapper {
    
    /**
     pageHelper
     * 分页查询
     */
    List<Stu> list(Integer id, Short gender, Integer collegeId,LocalDate begin, LocalDate end);
    
    /**
     * 批量删除
     * @param ids
     */
    void delete(Integer[] ids);
    
    /**
     * 新增用户
     * @param stu
     */
    void insert(Stu stu);
    
    /**
     * 按id查询
     * @param id
     * @return
     */
    Stu getById(Integer id);
    
    /**
     * 更新
     * @param stu
     */
    void update(Stu stu);
    
    /**
     * 用于映射，获得id和name
     * @return
     */
    @Select("select id,name from stu")
    List<Stu> selectAll();
    
    
    @Select("select count(*) from stu where id=#{userId} and password=#{password}")
    boolean selectByIdAndPassword(Login login);
    
    @Select("select id, name, password, gender, image, birthday, college_id, create_time, update_time from stu")
    List<Stu> selectAllData();
}
