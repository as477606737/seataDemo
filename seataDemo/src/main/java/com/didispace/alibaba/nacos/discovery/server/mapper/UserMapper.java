package com.didispace.alibaba.nacos.discovery.server.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.didispace.alibaba.nacos.discovery.server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

@Mapper
public interface  UserMapper extends BaseMapper<User> {
    @Select("select * from user LIMIT #{limit}")
    Cursor<User> scan(@Param("limit") int limit);
}
