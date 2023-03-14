package com.example.demo.mapper;

import com.example.demo.pojo.UserPhoneEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/8/1 18:01
 * @Description:
 */
@Mapper
@Repository
public interface UserPhoneMapper {
    int addOne(UserPhoneEntity userPhoneEntity);
    List<UserPhoneEntity> getAll();
}
