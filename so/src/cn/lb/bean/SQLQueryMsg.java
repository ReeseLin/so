package cn.lb.bean;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * 
 * The name: sql请求包装类 
 * What to do: 通过创建一个sql请求类可以执行一个sql操作
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public class SQLQueryMsg {

	// sql语句
	private String sql;

	// 可以设置多重查询
	private SQLQueryMsg subsqlQueryMsg;

	// 返回的信息
	private List<Map<String, Object>> resultMsg;

	// 填充ps在后续可以关闭
	PreparedStatement ps;

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public List<Map<String, Object>> getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(List<Map<String, Object>> resultMsg) {
		this.resultMsg = resultMsg;
	}


	public SQLQueryMsg getSubsqlQueryMsg() {
		return subsqlQueryMsg;
	}

	public void setSubsqlQueryMsg(SQLQueryMsg subsqlQueryMsg) {
		this.subsqlQueryMsg = subsqlQueryMsg;
	}



	private Map<String, Object> parameters;

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
