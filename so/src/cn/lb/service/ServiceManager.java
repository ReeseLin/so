package cn.lb.service;

import cn.lb.bean.QueryMsg;

/**
 * 
 * The name: service一个管理类 What to do: 通过这个类可以通过指定的service name获取到指定service
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class ServiceManager {

	/**
	 * 执行方法
	 * 
	 * @param queryMsg请求的包装类type为request
	 * @return queryMsg请求的包装类type为response
	 * @throws Exception
	 */
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

	/**
	 * 通过服务名遍历service type匹配到的service得到他的Class
	 * 
	 * @param methodName服务名
	 * @return
	 */
	private static Class<? extends MainService> getSpecificService(
			String methodName) {
		Class<? extends MainService> clazz = null;
		for (ServiceType serviceType : ServiceType.values()) {
			if (serviceType.name().equals(methodName)) {
				clazz = serviceType.getService();
			}
		}
		return clazz;
	}

}
