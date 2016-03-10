package cn.lb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * The name: 创建聊天室service What to do:
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class CreateChatRoom extends MainService {

	public static final String SQL_OPERATOR_CHAT_ROOM = " INSERT INTO chat_room (creater,chatroomname) VALUES (:creater,:chatroomname);";

	public static final String SQL_GETID = " SELECT LAST_INSERT_ID() as chatroomid;";

	public static final String SQL_USER_CHAT_ROOM = " INSERT INTO user_chat_room (userid,chatroomid,isagree) VALUES (:userid,:chatroomid,1);";

	public static final String SQL_CHAT_ROOM_MEMBER = " INSERT INTO chat_room_member (chatroomid,userid,isagree,iscreater)"
			+ " VALUES (:chatroomid,:userid,1,1);";

	@Override
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		List<Map<String, Object>> list = queryMsg.getDataTable();
		Map<String, Object> map = list.get(0);

		String userid = (String) map.get("userid");
		String chatroomname = (String) map.get("chatroomname");
		String chatroomid = createChatRoom(userid, chatroomname);
		setTwoTable(chatroomid, userid);

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		Map<String, Object> resultdate = new HashMap<String, Object>();
		resultdate.put("chatroomid", chatroomid);
		resQueryMsg.setResponse(true);
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(resultdate);
		resQueryMsg.setResult("0");

		return resQueryMsg;
	}

	private void setTwoTable(String chatroomid, String userid) throws Exception {
		// 3,在user_chat_room中把用户id和聊天室id写入并把isagree置为1
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("chatroomid", chatroomid);
		sqlQueryMsg.setSql(SQL_USER_CHAT_ROOM);
		sqlQueryMsg.setParameters(parameters);

		// 4,在chat_room_member中用户id和聊天室id写入并把isagree置为1和iscreater置为1
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> subparameters = new HashMap<String, Object>();
		subparameters.put("userid", userid);
		subparameters.put("chatroomid", chatroomid);
		subsqlQueryMsg.setSql(SQL_CHAT_ROOM_MEMBER);
		subsqlQueryMsg.setParameters(subparameters);

		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);

		DBUtils.executeSQL(sqlQueryMsg);

	}

	private String createChatRoom(String userid, String chatroomname)
			throws Exception {
		// 1,在chat_room表中创建一个唯一聊天室id并creater设置为用户id
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("creater", userid);
		parameters.put("chatroomname", chatroomname);
		sqlQueryMsg.setSql(SQL_OPERATOR_CHAT_ROOM);
		sqlQueryMsg.setParameters(parameters);

		// 2,获取chatroom的id
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		subsqlQueryMsg.setSql(SQL_GETID);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);

		DBUtils.executeSQL(sqlQueryMsg);

		List<Map<String, Object>> resultList = subsqlQueryMsg.getResultMsg();
		Map<String, Object> map = resultList.get(0);

		return map.get("chatroomid") + "";
	}
}
