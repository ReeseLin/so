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

	public static final String SQL_GET_CHAT_ROOM_ = " SELECT * FROM chat_room WHERE chatroomid = :chatroomid;";

	public static final String SQL_CHECK_IS_ALREADY_APPLY = " SELECT COUNT(*) AS applytime FROM chat_room_member WHERE chatroomid=:chatroomid AND userid=:userid AND isagree!=1;";

	@Override
	public QueryMsg execute() throws Exception {
		QueryMsg resQueryMsg = new QueryMsg();
		Map<String, Object> chatroomdetail = null;
		Map<String, Object> parameters = setParameter();
		List<Map<String, Object>> detailList = getChatRoomDetail(parameters);
		int roomCount = detailList.size();
		// 判断是否房间存在
		if (roomCount >= 1) {
			boolean isApply = chackIsAlreadyApply(parameters);
			if (!isApply) {
				resQueryMsg.setResult("1");
				resQueryMsg.setError("已经申请过了！");
				return resQueryMsg;
			}
			chatroomdetail = detailList.get(0);
			saveApply(parameters);
			assembleSuccessResponse((String)parameters.get("userid"), resQueryMsg, chatroomdetail);
		} else {
			resQueryMsg.setResult("1");
			resQueryMsg.setError("没有该房间");
		}
		return resQueryMsg;
	}

	private Map<String, Object> setParameter() {
		Map<String, Object> map =dataTable.get(0);
		String userid = (String) map.get("userid");
		String chatroomid = (String) map.get("chatroomid");

		// 设置sql中的参数(可共享)
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("chatroomid", chatroomid);
		return parameters;
	}

	private List<Map<String, Object>> getChatRoomDetail(
			Map<String, Object> parameters) throws Exception {
		SQLQueryMsg chackQM = new SQLQueryMsg();
		chackQM.setSql(SQL_GET_CHAT_ROOM_);
		chackQM.setParameters(parameters);
		DBUtils.executeSQL(chackQM);
		List<Map<String, Object>> detailList = chackQM.getResultMsg();
		return detailList;
	}

	private void saveApply(Map<String, Object> parameters) throws Exception {
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		sqlQueryMsg.setSql(SQL_USER_CHAT_ROOM);
		sqlQueryMsg.setParameters(parameters);
		// 查询插入user id操作
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		subsqlQueryMsg.setSql(SQL_CHAT_ROOM_MEMBER);
		subsqlQueryMsg.setParameters(parameters);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);
		// 执行
		DBUtils.executeSQL(sqlQueryMsg);
	}

	private void assembleSuccessResponse(String userid, QueryMsg resQueryMsg,
			Map<String, Object> chatroomdetail) {
		// 构建回应数据
		Map<String, Object> resultdate = new HashMap<String, Object>();
		resultdate.put("userid", userid);
		resultdate.put("applyroomname", chatroomdetail.get("chatroomname"));
		resultdate.put("applyroomid", chatroomdetail.get("chatroomid"));
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(resultdate);
	}

	private boolean chackIsAlreadyApply(Map<String, Object> parameters)
			throws Exception {
		// 判断是否重复申请
		SQLQueryMsg chackAA = new SQLQueryMsg();
		chackAA.setSql(SQL_CHECK_IS_ALREADY_APPLY);
		chackAA.setParameters(parameters);
		DBUtils.executeSQL(chackAA);
		List<Map<String, Object>> detailList2 = chackAA.getResultMsg();

		String applytimeString = Long.toString((Long) detailList2.get(0).get(
				"applytime"));

		int applytime = Integer.parseInt(applytimeString);
		if (applytime > 0) {
			return false;
		}
		return true;
	}

}
