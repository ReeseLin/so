package cn.lb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:同意用户加入聊天室
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public class AgreeUserJoin extends MainService {

	public static final String SQL_CHECK_ISCREATER = " SELECT iscreater FROM chat_room_member WHERE chatroomid=:chatroomid AND userid=:userid";

	public static final String SQL_UPDATE_CHAT_ROOM_MEMBER = " UPDATE chat_room_member SET isagree = '1' WHERE chatroomid =:chatroomid AND userid=:friendid;";

	@Override
	@Transactional
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		// 获取到请求内容的参数 TODO 获取参数这个地方感觉有点问题
		List<Map<String, Object>> list = queryMsg.getDataTable();
		Map<String, Object> map = list.get(0);

		String userid = (String) map.get("userid");
		String friendid = (String) map.get("friendid");
		String chatroomid = (String) map.get("chatroomid");

		// 插入user操作
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("friendid", friendid);
		parameters.put("chatroomid", chatroomid);
		sqlQueryMsg.setSql(SQL_CHECK_ISCREATER);
		sqlQueryMsg.setParameters(parameters);

		// 执行
		DBUtils.executeSQL(sqlQueryMsg);

		String iscreater = (String) sqlQueryMsg.getResultMsg().get(0)
				.get("iscreater");
		if ("1".equals(iscreater)) {
			sqlQueryMsg = new SQLQueryMsg();
			sqlQueryMsg.setSql(SQL_UPDATE_CHAT_ROOM_MEMBER);
			sqlQueryMsg.setParameters(parameters);
			DBUtils.executeSQL(sqlQueryMsg);
		}

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");

		return resQueryMsg;

	}

}
