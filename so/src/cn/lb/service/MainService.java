package cn.lb.service;

import cn.lb.bean.QueryMsg;

public class MainService {

	public String getServiceName(){
		return this.getClass().getName();
	}
	
	public QueryMsg execute(QueryMsg queryMsg){
		return null;
	}
}
