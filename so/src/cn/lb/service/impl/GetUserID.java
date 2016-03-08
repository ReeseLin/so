package cn.lb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;
import cn.lb.utils.QueryUtils;
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

	
	public static final String INSERTUSER=" INSERT INTO USER (username) VALUES  (:username);" ;
	
	public static final String GETID=" SELECT LAST_INSERT_ID() as userid;";
	
	
	@Override
	@Transactional
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		//获取到请求内容的参数 TODO 获取参数这个地方感觉有点问题
		List<Map<Object, Object>> list = queryMsg.getDataTable();
		Map<Object, Object> map=list.get(0);
		
		//插入user操作
		SQLQueryMsg sqlQueryMsg =new SQLQueryMsg();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("username", map.get("username"));
		sqlQueryMsg.setSql(INSERTUSER);
		sqlQueryMsg.setParameters(parameters);

		//查询插入user id操作
		SQLQueryMsg subsqlQueryMsg =new SQLQueryMsg();
		subsqlQueryMsg.setSql(GETID);
		sqlQueryMsg.setSubsqlQueryMsg(subsqlQueryMsg);
		
		DBUtils.executeSQL(sqlQueryMsg);
		
		//得到返回user id
		Map<String, Object> resultMap =subsqlQueryMsg.getResultMsg().get(0);
		String userid=resultMap.get("userid")+"";
		Map<Object, Object> resultdate = new HashMap<Object, Object>();
		resultdate.put("userid", userid);
		
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(resultdate);
		resQueryMsg.setResult("0");
		
		return resQueryMsg;
	}

}
