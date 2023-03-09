package com.zakiis.rdb.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zakiis.core.util.JsonUtil;
import com.zakiis.rdb.demo.mapper.AddressMapper;
import com.zakiis.rdb.demo.mapper.UserMapper;
import com.zakiis.rdb.demo.model.Address;
import com.zakiis.rdb.demo.model.User;
import com.zakiis.security.CipherUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RdbDemoApplicationTests {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AddressMapper addressMapper;

	@Test
	void contextLoads() {
		prepareData();
		testQuery();
	}

	private void testQuery() {
		User dbUser = userMapper.selectById(1L);
		log.info("user: {}", JsonUtil.toJson(dbUser));
		
		// no filter required if the criteria length is a multiple of 2 in full-width characters case or a multiple of 4 in half-width characters case. 
		System.out.println("===========fuzzy query 黄贝");
		LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<Address>()
				.like(Address::getDetails, CipherUtil.getEncryptedValue("黄贝", true));
		List<Address> addressList1 = addressMapper.selectList(queryWrapper);
		log.info("fuzzy query 黄贝 returns: {}", addressList1);
		// filter required in other cases of fuzzy query.
		queryWrapper = new LambdaQueryWrapper<Address>()
				.like(Address::getDetails, CipherUtil.getEncryptedValue("黄贝村", true));
		addressList1 = addressMapper.selectList(queryWrapper);
		log.info("fuzzy query 黄贝村(before filter) returns: {}", addressList1);
		List<Address> addressList2 = addressList1.stream()
				.filter(addr -> addr.getDetails().indexOf("黄贝村") != -1)
				.collect(Collectors.toList());
		log.info("fuzzy query 黄贝村(after filter) returns: {}", addressList2);
		// no filter need in precise query case
		queryWrapper = new LambdaQueryWrapper<Address>()
				.likeRight(Address::getZipCode, CipherUtil.getEncryptedValue("518000", false));
		addressList1 = addressMapper.selectList(queryWrapper);
		log.info("precise query zipCode(518000) returns: {}", addressList1);
	}

	private void prepareData() {
		userMapper.truncate();
		addressMapper.truncate();
		
		Address addressModel = new Address();
		addressModel.setCountry("中国");
		addressModel.setProvince("广东");
		addressModel.setCity("深圳");
		addressModel.setRegion("罗湖");
		addressModel.setStreet("黄贝");
		addressModel.setZipCode("518001");
		addressModel.setDetails("黄贝岭3路1号");
		addressMapper.insert(addressModel);
		
		User userModel = new User();
		userModel.setName("张三");
		userModel.setPassword("123456");
		userModel.setUsername("ZhangSan");
		userModel.setResAddressId(addressModel.getId());
		userMapper.insert(userModel);
		
		Address addressModel2 = new Address();
		addressModel2.setCountry("中国");
		addressModel2.setProvince("广东");
		addressModel2.setCity("深圳");
		addressModel2.setRegion("罗湖");
		addressModel2.setStreet("黄贝");
		addressModel2.setZipCode("518001");
		addressModel2.setDetails("黄贝村1巷");
		addressMapper.insert(addressModel2);
		
		User userModel2 = new User();
		userModel2.setName("李四");
		userModel2.setPassword("123465");
		userModel2.setUsername("LiSi");
		userModel2.setResAddressId(addressModel2.getId());
		userMapper.insert(userModel2);
		
		Address addressModel3 = new Address();
		addressModel3.setCountry("中国");
		addressModel3.setProvince("广东");
		addressModel3.setCity("深圳");
		addressModel3.setRegion("福田");
		addressModel3.setStreet("香蜜湖");
		addressModel3.setZipCode("518000");
		addressModel3.setDetails("福田区农林路1号");
		addressMapper.insert(addressModel3);
		
		User userModel3 = new User();
		userModel3.setName("王五");
		userModel3.setPassword("123462");
		userModel3.setUsername("Wangwu");
		userModel3.setResAddressId(addressModel3.getId());
		userMapper.insert(userModel3);
	}

}
