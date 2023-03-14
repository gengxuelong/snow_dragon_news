package com.example.demo.mapper;

import com.example.demo.pojo.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 14:23
 * @Description:
 */
@Mapper
@Repository
public interface UserMapper {
    int addUser(UserEntity userEntity);
    List<UserEntity> getAll();
    int deleteUser(Long id);
}
