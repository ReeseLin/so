package cn.lb.service;

import cn.lb.service.impl.GetUserID;

public enum ServiceType {

	GetUserID(GetUserID.class);

	ServiceType(Class<? extends MainService> s) {
		service = s;
	}

	final private Class<? extends MainService> service;

	public Class<? extends MainService> getService() {
		return service;
	}
	
}
