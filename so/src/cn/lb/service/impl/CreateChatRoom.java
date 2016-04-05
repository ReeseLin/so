package cn.lb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.service.SFC;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:创建聊天室
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class CreateChatRoom extends MainService {

	public static final String SQL_OPERATOR_CHAT_ROOM = " INSERT INTO chat_room (creater,chatroomname,creatername) VALUES (:creater,:chatroomname,:creatername);";

	public static final String SQL_CHACK_USER_ISEXIST = "SELECT COUNT(*) AS usercount FROM USER WHERE userid=:userid AND username=:username;";

	public static final String SQL_GETID = " SELECT LAST_INSERT_ID() as chatroomid;";

	public static final String SQL_USER_CHAT_ROOM = " INSERT INTO user_chat_room (userid,chatroomid,isagree) VALUES (:userid,:chatroomid,1);";

	public static final String SQL_CHAT_ROOM_MEMBER = " INSERT INTO chat_room_member (chatroomid,userid,isagree,iscreater)"
			+ " VALUES (:chatroomid,:userid,1,1);";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);
		String userid = (String) map.get(SFC.USERID);
		String username = (String) map.get(SFC.USERNAME);
		String chatroomname = (String) map.get(SFC.CHATROOMNAME);

		QueryMsg resQueryMsg = new QueryMsg();

		boolean userIsExist = chackUserExist(userid, username);

		if (userIsExist) {
			String chatroomid = createChatRoom(userid, username, chatroomname);

			insertUserChatRoomAndChatRoommember(chatroomid, userid);

			map.put(SFC.CHATROOMID, chatroomid);
			resQueryMsg.setResponse(true);
			resQueryMsg.iniDateTable();
			resQueryMsg.getDataTable().add(map);
			resQueryMsg.setResult(SFC.RESULT_SUCCESS);
		} else {
			resQueryMsg.setResult(SFC.RESULT_FAIL);
			resQueryMsg.setError("用户不存在");
		}

		return resQueryMsg;
	}

	/**
	 * 查询用户是否存在
	 * 
	 * @param userid
	 * @param username
	 * @return 返回true存在，返回false则用户不存在
	 * @throws Exception
	 */
	private boolean chackUserExist(String userid, String username)
			throws Exception {
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(SFC.USERID, userid);
		parameters.put(SFC.USERNAME, username);
		sqlQueryMsg.setSql(SQL_CHACK_USER_ISEXIST);
		sqlQueryMsg.setParameters(parameters);
		DBUtils.executeSQL(sqlQueryMsg);
		List<Map<String, Object>> result = sqlQueryMsg.getResultMsg();
		Map<String, Object> map = result.get(0);
		Long usercount = (Long) map.get(SFC.USERCOUNT);
		if (usercount == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 在用户聊天室表和聊天室成员表
	 * 
	 * @param chatroomid
	 * @param userid
	 * @throws Exception
	 */
	private void insertUserChatRoomAndChatRoommember(String chatroomid,
			String userid) throws Exception {

		// 在user_chat_room中把用户id和聊天室id写入并把isagree置为1
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(SFC.USERID, userid);
		parameters.put(SFC.CHATROOMID, chatroomid);
		sqlQueryMsg.setSql(SQL_USER_CHAT_ROOM);
		sqlQueryMsg.setParameters(parameters);

		// 在chat_room_member中用户id和聊天室id写入并把isagree置为1和iscreater置为1
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> subparameters = new HashMap<String, Object>();
		subparameters.put(SFC.USERID, userid);
		subparameters.put(SFC.CHATROOMID, chatroomid);
		subsqlQueryMsg.setSql(SQL_CHAT_ROOM_MEMBER);
		subsqlQueryMsg.setParameters(subparameters);

		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);

		DBUtils.executeSQL(sqlQueryMsg);

	}

	/**
	 * 创建聊天室
	 * 
	 * @param userid
	 * @param chatroomname
	 * @return 返回聊天室的ID
	 * @throws Exception
	 */
	private String createChatRoom(String userid, String username,
			String chatroomname) throws Exception {
		// 1,在chat_room表中创建一个唯一聊天室id并creater设置为用户id
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(SFC.CREATER, userid);
		parameters.put(SFC.CREATERNAME, username);
		parameters.put(SFC.CHATROOMNAME, chatroomname);

		sqlQueryMsg.setSql(SQL_OPERATOR_CHAT_ROOM);
		sqlQueryMsg.setParameters(parameters);

		// 2,获取chatroom的id
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		subsqlQueryMsg.setSql(SQL_GETID);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);

		DBUtils.executeSQL(sqlQueryMsg);

		List<Map<String, Object>> resultList = subsqlQueryMsg.getResultMsg();
		Map<String, Object> map = resultList.get(0);

		return map.get(SFC.CHATROOMID)+"";
	}
}
