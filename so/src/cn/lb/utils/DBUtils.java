package cn.lb.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.lb.bean.SQLQueryMsg;

/**
 * 
 * The name: 数据库工具类 负责执行sql并返回一个list<map>集合
 * What to do: 
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
 *
 */
public class DBUtils {

	// 日志记录的一个类，TODO 看看是否要替换成自己的日志记录类
	static final Log log = LogFactory.getLog(DBUtils.class);
	private static Map<Integer, String> paramsMap = new HashMap<Integer, String>();
	private static DataSource dbSource;
	private final static String DATASOURCE = "dataSource";
	private final static String REGEX = "(:(\\w+))";

	// 这个步骤好像是在springmvc框架中得到DataSource
	// TODO 但是具体的是否还是有连接池管理还有待验证
	static {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		dbSource = (DataSource) wac.getBean(DATASOURCE); // 配置文件里的beanid
	}

	private static void closePS(SQLQueryMsg sqlQueryMsg) throws SQLException {
		sqlQueryMsg.getPs().close();
		if (sqlQueryMsg.getSubsqlQueryMsg() != null) {
			closePS(sqlQueryMsg.getSubsqlQueryMsg());
		}
	}

	/**
	 * 执行单一prepareStatement的一个方法(如果传入的请求类中子类存在则开始迭代执行)
	 * 
	 * @param sqlQueryMsg
	 * @param conn
	 * @throws SQLException
	 */
	private static void executePS(SQLQueryMsg sqlQueryMsg, Connection conn)
			throws SQLException {
		PreparedStatement ps;
		String sql = parseSql(sqlQueryMsg.getSql());
		ps = conn.prepareStatement(sql);
		// 回填prepareStatement
		sqlQueryMsg.setPs(ps);
		// 填充参数
		fillParameters(ps, sqlQueryMsg.getParameters());
		ps.execute();
		// 回填参数
		sqlQueryMsg.setResultMsg(ResultSetToList(ps.getResultSet()));
		if (sqlQueryMsg.getSubsqlQueryMsg() != null) {
			executePS(sqlQueryMsg.getSubsqlQueryMsg(), conn);
		}
	}

	/**
	 * 把ResultSet转换为list
	 * 
	 * @param ps
	 * @return
	 * @throws SQLException
	 */
	private static List<Map<String, Object>> ResultSetToList(ResultSet rs)
			throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (rs != null) {
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData md = rs.getMetaData();
				int sum = md.getColumnCount();
				for (int i = 1; i <= sum; i++) {
					map.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 执行sql的方法，传入可为多个请求
	 * 
	 * @param sqlQueryMsg包装类
	 * @return
	 * @throws Exception
	 */
	public static void executeSQL(SQLQueryMsg sqlQueryMsg) throws Exception {
		Connection conn = null;
		try {
			conn = dbSource.getConnection();
			// 设置不自动提交
			conn.setAutoCommit(false);
			executePS(sqlQueryMsg, conn);
			conn.commit();
		} catch (Exception e) {
			log.debug("execute中出错：" + e.getMessage());
			conn.rollback();
		} finally {
			closePS(sqlQueryMsg);
			conn.close();
		}
	}

	/**
	 * 把传入的SQL解析成PreparedStatement可以接受的SQL 并记录下坐标和名字可以为后面赋值的时候使用ps.setObject()
	 * 
	 * @param sql
	 * @return
	 */
	public static String parseSql(String sql) {
		String regex = REGEX;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		emptyMap();
		int idx = 1;
		while (m.find()) {
			paramsMap.put(new Integer(idx++), m.group(0).substring(1));
		}
		String result = sql.replaceAll(regex, "?");
		log.debug("分析前：" + sql);
		log.debug("分析后：" + result);
		return result;
	}

	/**
	 * 向PreparedStatement中填入数据
	 * 
	 * @param ps
	 * @param parameters
	 * @return
	 */
	public static boolean fillParameters(PreparedStatement ps,
			Map<String, Object> parameters) {
		int index = 1;
		String valuename = null;
		try {
			for (java.util.Iterator<Entry<Integer, String>> it = paramsMap
					.entrySet().iterator(); it.hasNext();) {
				Entry<Integer, String> entry = it.next();
				index = entry.getKey();
				valuename = entry.getValue();
				ps.setObject(index, parameters.get(valuename));
			}
		} catch (SQLException e) {
			log.error("填充参数出错，原因：" + e.getMessage());
			return false;
		}
		return true;

	}

	public static Map<Integer, String> getParamsMap() {
		return paramsMap;
	}

	public static void emptyMap() {
		paramsMap.clear();
	}
}
