package cn.lb.service.impl;

import java.util.Map;

import cn.lb.bean.QueryMsg;
import cn.lb.bean.SQLQueryMsg;
import cn.lb.service.MainService;
import cn.lb.utils.DBUtils;

/**
 * 
 * Des:同意用户加入聊天室
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class AgreeUserJoin extends MainService {

	public static final String SQL_CHECK_ISCREATER = " SELECT iscreater FROM chat_room_member WHERE chatroomid=:chatroomid AND userid=:userid";

	public static final String SQL_UPDATE_CHAT_ROOM_MEMBER = " UPDATE chat_room_member SET isagree = '1' WHERE chatroomid =:chatroomid AND userid=:friendid;";

	@Override
	public QueryMsg execute() throws Exception {
		Map<String, Object> map = dataTable.get(0);
		
		SQLQueryMsg sqlQueryMsg;
		
		boolean iscreater = chackIsCreater(map);

		if (iscreater) {
			sqlQueryMsg = new SQLQueryMsg();
			sqlQueryMsg.setSql(SQL_UPDATE_CHAT_ROOM_MEMBER);
			sqlQueryMsg.setParameters(map);
			DBUtils.executeSQL(sqlQueryMsg);
		}else{
			return setErrorResQueryMsg();
		}

		// 构建回应数据
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.iniDateTable();
		resQueryMsg.getDataTable().add(map);
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("0");

		return resQueryMsg;

	}

	private boolean chackIsCreater(Map<String, Object> map) throws Exception {
		SQLQueryMsg sqlQueryMsg = new SQLQueryMsg();
		sqlQueryMsg.setSql(SQL_CHECK_ISCREATER);
		sqlQueryMsg.setParameters(map);
		DBUtils.executeSQL(sqlQueryMsg);
		String iscreater = (String) sqlQueryMsg.getResultMsg().get(0)
				.get("iscreater");
		if("1".equals(iscreater)){
			return true;
		}
		return false;
	}

	private QueryMsg setErrorResQueryMsg() {
		QueryMsg resQueryMsg = new QueryMsg();
		resQueryMsg.setResponse(true);
		resQueryMsg.setResult("1");
		resQueryMsg.setError("不是房间创建者");
		return resQueryMsg;
	}

}
