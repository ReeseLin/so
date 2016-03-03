package cn.lb.service;

import org.springframework.transaction.annotation.Transactional;

import cn.lb.bean.DateCollection;


public interface MainService {
	
	
	@Transactional
	public void insertUser(DateCollection dc) throws Exception;
}
