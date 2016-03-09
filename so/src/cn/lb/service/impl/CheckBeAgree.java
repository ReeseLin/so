package cn.lb.service.impl;

import java.util.ArrayList;
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
 * The name: 新建一个用户 What to do: 根据一个传入的用户名得到一个唯一id并返回
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class CheckBeAgree extends MainService {

	public static final String SQL_CHECK_AGREE = " SELECT isagree FROM user_chat_room WHERE userid=:userid AND chatroomid=:chatroomid;";

	@Override
	@Transactional
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		// 获取到请求内容的参数 TODO 获取参数这个地方感觉有点问题
		List<Map<Object, Object>> list = queryMsg.getDataTable();
		Map<Object, Object> map = list.get(0);

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

		// 构建回应数据
		List<Map<Object,Object>> resultList = new ArrayList<Map<Object,Object>>();
		Map<Object,Object> resultMap =new HashMap<Object,Object>();
		resultMap.put("isagree", (sqlQueryMsg.getResultMsg().get(0)).get("isagree"));
		resultList.add(resultMap);
		
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");
		resQueryMsg.iniDateTable();
		resQueryMsg.setDataTable(resultList);

		return resQueryMsg;
	}

}
