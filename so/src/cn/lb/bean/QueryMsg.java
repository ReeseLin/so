package cn.lb.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * The name: post请求数据包装类
 * What to do: 与QueryUtils结合使用可以把指定格式的xml装换为QueryMsg
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public class QueryMsg {

	// 这里type有两种类型 1,request 2,response
	private String Type;
	private String MethodName;
	private String Result;
	private String Error;
	private Date createtime;
	private String AccessKey;
	private List<Map<Object, Object>> DataTable;
	private String encoding;

	public QueryMsg() {
		super();
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getMethodName() {
		return MethodName;
	}

	public void setMethodName(String methodName) {
		MethodName = methodName;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getAccessKey() {
		return AccessKey;
	}

	public void setAccessKey(String accessKey) {
		AccessKey = accessKey;
	}

	public List<Map<Object, Object>> getDataTable() {
		return DataTable;
	}

	public void setDataTable(List<Map<Object, Object>> dataTable) {
		DataTable = dataTable;
	}

}
