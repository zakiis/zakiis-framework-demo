package com.zakiis.rdb.demo.mapper;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zakiis.rdb.demo.model.User;

public interface UserMapper extends BaseMapper<User> {

	@Delete("truncate table user")
	int truncate();
}
