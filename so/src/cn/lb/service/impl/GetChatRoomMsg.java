package cn.lb.service.impl;

import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:接收消息
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class GetChatRoomMsg extends MainService {

	// public static final String SQL_CHECK_MEMBER_ISCREATER =
	// " SELECT isagree FROM chat_room_member WHERE chatroomid=:chatroomid AND userid=:userid;";

	// public static final String SQL_GET_CHAT_ROOM_MESSAGE =
	// "SELECT * FROM chat_room_message WHERE createtime >:lasttime AND senderid!=:userid ;";

	public static final String SQL_GET_MESSAGE = "SELECT * FROM chat_room_message "
			+ " WHERE chatroomid "
			+ " IN (SELECT chatroomid FROM chat_room_member WHERE userid=:userid AND isagree='1')"
			+ " AND senderid !=:userid AND createtime >:lasttime ORDER BY createtime;";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);

		SQLQueryMsg sqlQueryMsg = null;

		sqlQueryMsg = new SQLQueryMsg();
		sqlQueryMsg.setSql(SQL_GET_MESSAGE);
		sqlQueryMsg.setParameters(map);
		DBUtils.executeSQL(sqlQueryMsg);

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		if (sqlQueryMsg.getResultMsg() != null) {
			resQueryMsg.setDataTable(sqlQueryMsg.getResultMsg());
		}
		return resQueryMsg;
	}
}
