package cn.lb.service.impl;

import org.springframework.transaction.annotation.Transactional;


public interface MainService {
	
	
	@Transactional(readOnly=true)
	public void findContacterList() throws Exception;
}
