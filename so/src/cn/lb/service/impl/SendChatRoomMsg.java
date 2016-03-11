package cn.lb.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:发送消息
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class SendChatRoomMsg extends MainService {

	public static final String SQL_CHECK_MEMBER_ISCREATER = " SELECT isagree FROM chat_room_member WHERE chatroomid=:chatroomid AND userid=:userid;";

	public static final String SQL_UPDATE_CHAT_ROOM_MESSAGE = " INSERT INTO chat_room_message (chatroomid,message,senderid,createtime) VALUES (:chatroomid,:message,:userid, NOW());";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);

		String userid = (String) map.get("userid");
		String message = (String) map.get("message");
		String chatroomid = (String) map.get("chatroomid");

		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("message", message);
		parameters.put("chatroomid", chatroomid);
		sqlQueryMsg.setSql(SQL_CHECK_MEMBER_ISCREATER);
		sqlQueryMsg.setParameters(parameters);

		// 执行
		DBUtils.executeSQL(sqlQueryMsg);

		String isagree = (String) sqlQueryMsg.getResultMsg().get(0)
				.get("isagree");
		
		if ("1".equals(isagree)) {
			sqlQueryMsg = new SQLQueryMsg();
			sqlQueryMsg.setSql(SQL_UPDATE_CHAT_ROOM_MESSAGE);
			sqlQueryMsg.setParameters(parameters);
			DBUtils.executeSQL(sqlQueryMsg);
		}else{
			return setErrorResQueryMsg();
		}

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		return resQueryMsg;

	}

	private QueryMsg setErrorResQueryMsg() {
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("1");
		resQueryMsg.setError("未加入该房间！");
		return resQueryMsg;
	}

}
