package com.example.mapper;

import com.example.pojo.Login;
import com.example.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    /**
     * pageHelper
     * 分页查询
     */
    List<Teacher> list(Integer id, Integer collegeId, Integer deptId);
    
    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(Integer[] ids);
    
    /**
     * 新增教师
     *
     * @param teacher
     */
    void insert(Teacher teacher);
    
    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    Teacher getById(Integer id);
    
    /**
     * 更新
     *
     * @param teacher
     */
    void update(Teacher teacher);
    
    @Select("select * from teacher")
    List<Teacher> listAll();
    
    @Select("select count(*) from teacher where id=#{userId} and password=#{password}")
    boolean selectByIdAndPassword(Login login);
}
