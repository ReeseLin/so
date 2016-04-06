package cn.lb.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:查询申请加入聊天室的用户名单
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class CheckUsersJoin extends MainService {

	public static final String SQL_CHECK_USERS_JOIN = " SELECT crm.userid ,crm.chatroomid ,u.username "
			+ " FROM chat_room_member AS crm JOIN USER AS u ON crm.userid=u.userid"
			+ " WHERE isagree='0' AND chatroomid IN (SELECT chatroomid FROM chat_room_member WHERE userid=:userid AND isagree='1');";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);

		String userid = (String) map.get("userid");

		// 根据userid查询是否有人申请user之下的聊天室
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		sqlQueryMsg.setSql(SQL_CHECK_USERS_JOIN);
		sqlQueryMsg.setParameters(parameters);

		// 执行
		DBUtils.executeSQL(sqlQueryMsg);

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		resQueryMsg.setDataTable(sqlQueryMsg.getResultMsg());

		return resQueryMsg;

	}

}
