package cn.lb.service;

import cn.lb.service.impl.GetUserID;
import cn.lb.service.impl.CreateChatRoom;
import cn.lb.service.impl.CheckBeAgree;
import cn.lb.service.impl.ApplyJoinChatRoom;
import cn.lb.service.impl.CheckUsersJoin;

/**
 * 服务类型，把新创建的服务在这里注册
 * The name: 
 * What to do: 
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public enum ServiceType {

	GetUserID(GetUserID.class), // 获得user id服务
	CreateChatRoom(CreateChatRoom.class),// 创建聊天室服务
	ApplyJoinChatRoom(ApplyJoinChatRoom.class),// 申请加入聊天室服务
	CheckUsersJoin(CheckUsersJoin.class),// 查询是否有人申请加入聊天室
	CheckBeAgree(CheckBeAgree.class);// 查询是否被同意加入聊天室

	
	
	ServiceType(Class<? extends MainService> service) {
		this.service = service;
	}

	final private Class<? extends MainService> service;

	public Class<? extends MainService> getService() {
		return service;
	}

}
