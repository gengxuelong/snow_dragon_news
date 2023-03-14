package com.example.demo.mapper;

import com.example.demo.pojo.ModuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 18:16
 * @Description:
 */
@Mapper
@Repository
public interface ModulesMapper {
    List<ModuleEntity> getAll();
}
