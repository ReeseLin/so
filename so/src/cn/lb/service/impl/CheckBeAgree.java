package cn.lb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:查询是否被允许加入聊天室
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class CheckBeAgree extends MainService {


	public static final String SQL_CHECK_AGREE = " SELECT isagree FROM chat_room_member WHERE chatroomid=:chatroomid AND userid=:userid;";

	public static final String SQL_UPDATE_CHAT_ROOM = " UPDATE user_chat_room SET isagree='1' WHERE chatroomid=:chatroomid AND userid=:userid;";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);

		String userid = (String) map.get("userid");
		String username = (String) map.get("chatroomid");

		// 插入user操作
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("chatroomid", username);
		sqlQueryMsg.setSql(SQL_CHECK_AGREE);
		sqlQueryMsg.setParameters(parameters);

		// 执行
		DBUtils.executeSQL(sqlQueryMsg);

		String isagree = (String) sqlQueryMsg.getResultMsg().get(0)
				.get("isagree");

		// sqlQueryMsg.getResultMsg().get(0)).get("isagree")
		// 如果这个时候isagree等于1
		// 更新自己的user_chat_room表
		if ("1".equals(isagree)) {
			SQLQueryMsg UpdatesqlQueryMsg = new SQLQueryMsg();
			UpdatesqlQueryMsg.setSql(SQL_UPDATE_CHAT_ROOM);
			UpdatesqlQueryMsg.setParameters(parameters);
			DBUtils.executeSQL(UpdatesqlQueryMsg);
		}

		// 构建回应数据
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isagree", isagree);
		resultMap.put("userid", parameters.get("userid"));
		resultMap.put("chatroomid", parameters.get("chatroomid"));
		resultList.add(resultMap);

		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		resQueryMsg.iniDateTable();
		resQueryMsg.setDataTable(resultList);

		return resQueryMsg;
	}

}
