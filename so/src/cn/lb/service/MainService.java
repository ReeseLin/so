package cn.lb.service;

import java.util.List;
import java.util.Map;

import cn.lb.bean.QueryMsg;

/**
 * 
 * The name: service的一个父类 What to do:
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class MainService {

	public List<Map<String, Object>> dataTable;

	// public QueryMsg resQueryMsg ;

	public String getServiceName() {
		return this.getClass().getName();
	}

	/**
	 * 设置传入参数
	 * 
	 * @param queryMsg
	 */
	public void setParameter(QueryMsg queryMsg) {
		dataTable = queryMsg.getDataTable();
	}

	public QueryMsg execute() throws Exception {
		return null;
	}

}
