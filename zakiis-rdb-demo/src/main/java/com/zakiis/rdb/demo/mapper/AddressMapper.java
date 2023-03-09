package com.zakiis.rdb.demo.mapper;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zakiis.rdb.demo.model.Address;

public interface AddressMapper extends BaseMapper<Address> {

	@Delete("truncate table address")
	int truncate();
}
