package cn.lb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.lb.bean.DateCollection;
import cn.lb.mapper.UserMapper;
import cn.lb.service.MainService;

public class MainServiceImpl implements MainService{

	//注入dao
	@Autowired
	private UserMapper um;
	
	@Override
	@Transactional
	public void insertUser(DateCollection dc) throws Exception {
		um.insertUser(dc);
	}

}
