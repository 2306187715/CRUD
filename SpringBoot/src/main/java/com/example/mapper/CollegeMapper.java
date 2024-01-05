package com.example.mapper;

import com.example.pojo.College;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CollegeMapper {
    
    /**
     * 查询所有学院
     *
     * @return
     */
    List<College> list();
    
    
    
    /**
     * 根据id删除学院
     *
     * @param id
     */
    void deleteById(Integer id);
    
    /**
     * 新增学院
     *
     * @param college
     */
    void insert(College college);
    
    /**
     * 根据id查询学院
     *
     * @param id
     */
    College getById(Integer id);
    
    /**
     * 根据id修改学院
     *
     * @param college
     */
    void update(College college);
    
    
}
