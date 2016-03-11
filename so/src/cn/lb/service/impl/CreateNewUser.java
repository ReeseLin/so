package cn.lb.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:创建一个新用户
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class CreateNewUser extends MainService {

	public static final String SQL_INSERT_USER = " INSERT INTO USER (username) VALUES  (:username);";

	public static final String SQL_GET_ID = " SELECT LAST_INSERT_ID() as userid;";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);
		String username = map.get("username") + "";
		
		if (!("".equals(username.trim()))) {
			SQLQueryMsg sqlQueryMsg = insertUserInTable(username);
			SQLQueryMsg subsqlQueryMsg = getUserID(sqlQueryMsg);
			DBUtils.executeSQL(sqlQueryMsg);
			QueryMsg resQueryMsg = setResultMsg(subsqlQueryMsg);
			return resQueryMsg;
		} else {
			return setErrorResultMsg();
		}
	}

	private QueryMsg setErrorResultMsg() {
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("1");
		resQueryMsg.setError("传入username为空 ！");
		return resQueryMsg;
	}

	/**
	 * 构建回应数据
	 * 
	 * @param sqlQueryMsg
	 * @return
	 */
	private QueryMsg setResultMsg(SQLQueryMsg sqlQueryMsg) {
		QueryMsg resQueryMsg = new QueryMsg();
		Map<String, Object> resultMap = sqlQueryMsg.getResultMsg().get(0);
		String userid = resultMap.get("userid") + "";
		Map<String, Object> resultdate = new HashMap<String, Object>();
		resultdate.put("userid", userid);
		resQueryMsg.setResponse(true);
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(resultdate);
		resQueryMsg.setResult("0");
		return resQueryMsg;
	}

	/**
	 * 得到插入user的随机id
	 * 
	 * @param sqlQueryMsg
	 * @return
	 */
	private SQLQueryMsg getUserID(SQLQueryMsg sqlQueryMsg) {
		SQLQueryMsg subsqlQueryMsg = new SQLQueryMsg();
		subsqlQueryMsg.setSql(SQL_GET_ID);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);
		return subsqlQueryMsg;
	}

	/**
	 * 插入一个user到表中
	 * 
	 * @param map
	 * @return
	 */
	private SQLQueryMsg insertUserInTable(String username) {
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		Map<String, Object> parameters = new HashMap<String, Object>();
		sqlQueryMsg.setSql(SQL_INSERT_USER);
		parameters.put("username", username);
		sqlQueryMsg.setParameters(parameters);
		return sqlQueryMsg;
	}

}
