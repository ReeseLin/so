package cn.lb.service;

import cn.lb.service.impl.GetUserID;
import cn.lb.service.impl.CreateChatRoom;

/**
 * 服务类型，把新创建的服务在这里注册
 * 
 * @author lin
 * 
 */
public enum ServiceType {

	GetUserID(GetUserID.class), // 获得user id服务
	CreateChatRoom(CreateChatRoom.class);// 创建聊天室服务

	
	
	ServiceType(Class<? extends MainService> service) {
		this.service = service;
	}

	final private Class<? extends MainService> service;

	public Class<? extends MainService> getService() {
		return service;
	}

}
