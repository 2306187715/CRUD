package com.example.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class stpInterfaceImpl implements StpInterface {
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        switch (loginType) {
            case "stu":
                List<String> listStu = new ArrayList<>();
                // listStu.add(loginId.toString());
                listStu.add("stu.update");
                listStu.add("*.select");
                listStu.add("elective.*");
                return listStu;
            case "teacher":
                List<String> listTeacher = new ArrayList<>();
                // listTeacher.add(loginId.toString());
                listTeacher.add("teacher.update");
                listTeacher.add("*.select");
                listTeacher.add("elective.*");
                return listTeacher;
            case "admin":
                List<String> listAdmin = new ArrayList<>();
                // listAdmin.add(loginId.toString());
                listAdmin.add("admin");
                return listAdmin;
            default:
                break;
        }
    return null;
    }
    
    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("teacher");
        list.add("stu");
        return list;
    }
}
