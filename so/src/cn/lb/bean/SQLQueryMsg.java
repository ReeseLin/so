package cn.lb.bean;

import java.util.Map;
/**
 * sql请求包装类
 * sql语句有指定的格式,如"SELECT * FROM USER where username = :username"
 * parameters是传入参数值，如上的话传入的应该是  key=value username="xxxx"
 * @author lin
 *
 */
public class SQLQueryMsg {
	private String sql;

	private Map<String,Object> parameters;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	
}
