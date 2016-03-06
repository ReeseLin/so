package cn.lb.service;

import cn.lb.bean.QueryMsg;
/**
 * service的一个父类
 * @author lin
 *
 */
public class MainService {

	public String getServiceName() {
		return this.getClass().getName();
	}

	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		return null;
	}

	
}
