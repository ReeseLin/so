package cn.lb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:申请加入聊天室
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class ApplyJoinChatRoom extends MainService {

	public static final String SQL_USER_CHAT_ROOM = " INSERT INTO user_chat_room (userid,chatroomid) VALUES (:userid,:chatroomid);";

	public static final String SQL_CHAT_ROOM_MEMBER = " INSERT INTO chat_room_member (userid,chatroomid) VALUES (:userid,:chatroomid);";

	@Override
	@Transactional
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);

		String userid = (String) map.get("userid");
		String username = (String) map.get("chatroomid");

		//TODO 判断是否房间存在？
		
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("chatroomid", username);
		sqlQueryMsg.setSql(SQL_USER_CHAT_ROOM);
		sqlQueryMsg.setParameters(parameters);

		// 查询插入user id操作
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		subsqlQueryMsg.setSql(SQL_CHAT_ROOM_MEMBER);
		subsqlQueryMsg.setParameters(parameters);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);

		// 执行
		DBUtils.executeSQL(sqlQueryMsg);

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");

		return resQueryMsg;
	}

}
