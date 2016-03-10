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
 * Des:查询申请加入聊天室的用户名单
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public class CheckUsersJoin extends MainService {

	public static final String SQL_CHECK_USERS_JOIN = " SELECT userid,chatroomid FROM chat_room_member WHERE isagree='0' AND chatroomid IN (SELECT chatroomid FROM chat_room_member WHERE userid=:userid AND isagree='1')";

	@Override
	@Transactional
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		// 获取到请求内容的参数 TODO 获取参数这个地方感觉有点问题
		List<Map<String, Object>> list = queryMsg.getDataTable();
		Map<String, Object> map = list.get(0);

		String userid = (String) map.get("userid");

		// 插入user操作
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
