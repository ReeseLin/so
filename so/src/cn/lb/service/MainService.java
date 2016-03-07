package cn.lb.service;

import cn.lb.bean.QueryMsg;
/**
 * 
 * The name: service的一个父类
 * What to do: 
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
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
