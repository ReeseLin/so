package cn.lb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	public static final String SQL_UPDATE_CHAT_ROOM_MESSAGE = " INSERT INTO chat_room_message (chatroomid,message,senderid,createtime,sendername) VALUES (:chatroomid,:message,:userid,:createtime,:username);";
	
	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);

//		String userid = (String) map.get("userid");
//		String message = (String) map.get("message");
//		String sendername = (String) map.get("username");
//		String chatroomid = (String) map.get("chatroomid");

//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("userid", userid);
//		parameters.put("message", message);
//		parameters.put("chatroomid", chatroomid);
//		parameters.put("sendername", sendername);
		
		SQLQueryMsg sqlQueryMsg;
		
		boolean isagree = checkIsBeAgree(map);
		
		if (isagree) {
			sqlQueryMsg = new SQLQueryMsg();
			sqlQueryMsg.setSql(SQL_UPDATE_CHAT_ROOM_MESSAGE);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createtime = format.format(new Date());
			map.put("createtime", createtime);
			sqlQueryMsg.setParameters(map);
			DBUtils.executeSQL(sqlQueryMsg);
		}else{
			return setErrorResQueryMsg();
		}

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(map);
		return resQueryMsg;

	}

	private boolean checkIsBeAgree(Map<String, Object> map) throws Exception {
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		sqlQueryMsg.setSql(SQL_CHECK_MEMBER_ISCREATER);
		sqlQueryMsg.setParameters(map);
		DBUtils.executeSQL(sqlQueryMsg);
		String isagree = (String) sqlQueryMsg.getResultMsg().get(0)
				.get("isagree");
		if("1".equals(isagree)){
			return true;
		}
		return false;
	}

	private QueryMsg setErrorResQueryMsg() {
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("1");
		resQueryMsg.setError("未加入该房间！");
		return resQueryMsg;
	}

}
