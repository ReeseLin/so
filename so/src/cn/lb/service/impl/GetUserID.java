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
 * The name: 新建一个用户
 * What to do: 根据一个传入的用户名得到一个唯一id并返回
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public class GetUserID extends MainService {

	
	public static final String SQL_INSERTUSER=" INSERT INTO USER (username) VALUES  (:username);" ;
	
	public static final String SQL_GETID=" SELECT LAST_INSERT_ID() as userid;";
	
	public static final String USERNAME="username";
	
	public static final String USERID="userid";
	
	@Override
	@Transactional
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		//获取到请求内容的参数 TODO 获取参数这个地方感觉有点问题
		List<Map<Object, Object>> list = queryMsg.getDataTable();
		Map<Object, Object> map=list.get(0);
		
		//插入user操作
		SQLQueryMsg sqlQueryMsg =new SQLQueryMsg();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put(USERNAME, map.get(USERNAME));
		sqlQueryMsg.setSql(SQL_INSERTUSER);
		sqlQueryMsg.setParameters(parameters);

		//查询插入user id操作
		SQLQueryMsg subsqlQueryMsg =new SQLQueryMsg();
		subsqlQueryMsg.setSql(SQL_GETID);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);
		
		//执行
		DBUtils.executeSQL(sqlQueryMsg);
		
		//构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		Map<String, Object> resultMap =subsqlQueryMsg.getResultMsg().get(0);
		String userid=resultMap.get(USERID)+"";
		Map<Object, Object> resultdate = new HashMap<Object, Object>();
		resultdate.put(USERID, userid);
		resQueryMsg.setResponse(true);
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(resultdate);
		resQueryMsg.setResult("0");
		
		return resQueryMsg;
	}

}
