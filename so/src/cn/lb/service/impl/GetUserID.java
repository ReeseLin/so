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
 * 根据一个传入的用户名得到一个唯一id并返回
 * @author lin
 *
 */
public class GetUserID extends MainService {


	@Override
	@Transactional
	public QueryMsg execute(QueryMsg queryMsg) throws Exception {
		SQLQueryMsg sqlQueryMsg =new SQLQueryMsg();
		String sql="SELECT * FROM USER where username = :username";
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("username", "lin");
		sqlQueryMsg.setSql(sql);
		sqlQueryMsg.setParameters(parameters);
		List<Map<String, Object>> list =DBUtils.executeSQL(sqlQueryMsg);
		
		return null;
	}

}
