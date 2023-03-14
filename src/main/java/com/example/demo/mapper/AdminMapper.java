package com.example.demo.mapper;

import com.example.demo.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     admin 对应 mapper
 */
@Mapper
@Repository
public interface AdminMapper {
    List<Admin> getAdminList();
    int updateAdmin(Admin admin);
    Admin getAdmin(String name);
}
