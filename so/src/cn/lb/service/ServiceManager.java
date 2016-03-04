package cn.lb.service;

import cn.lb.bean.QueryMsg;

public class ServiceManager {

	public static QueryMsg execute(QueryMsg queryMsg) throws Exception {
		// 得到methodName
		String methodName = queryMsg.getMethodName();
		// 获取到methodName类
		Class<? extends MainService> service = getSpecificService(methodName);
		// 实例化指定类
		MainService mservice = service.newInstance();
		// 运行execute方法
		QueryMsg resultQM = mservice.execute(queryMsg);
		
		return resultQM;
	}

	private static Class<? extends MainService> getSpecificService(
			String methodName) {
		
		return null;
	}

}
