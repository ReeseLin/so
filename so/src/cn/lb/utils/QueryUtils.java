package cn.lb.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import cn.lb.bean.QueryMsg;

/**
 * 
 * The name: 把指定格式XML解析为QueryMsg包装类的工具类 What to do:
 * 
 * @author ReeseLin
 * @Email 172053362@qq.com
 * 
 * 
 */
public class QueryUtils {

	/**
	 * 将XML解析成QueryMsg
	 * 
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	public static QueryMsg parseXmlStringToQueryMsg(String xmlString)
			throws Exception {

		QueryMsg queryMsg = new QueryMsg();
		Document document = DocumentHelper.parseText(xmlString);
		getElementsDate(queryMsg, document);
		return queryMsg;
	}

	/**
	 * 将QueryMsg解析成XML
	 * 
	 * @param queryMsg
	 * @return
	 * @throws Exception
	 */
	public static String parseQueryMsgToXmlString(QueryMsg queryMsg)
			throws Exception {
		Document document = null;
		if (queryMsg.isResponse()) {
			document = DocumentHelper.createDocument();
			document.setXMLEncoding(queryMsg.getEncoding());
			Element root = document.addElement("DocumentElement");
			root.addElement("Result").addText(queryMsg.getResult());
			if("1".equals(queryMsg.getResult())){
				root.addElement("Error").addText(queryMsg.getError());
			}
			if (queryMsg.getDataTable() != null) {
				Element DataTable = root.addElement("DataTable");
				Map<Object, Object> dataMap = queryMsg.getDataTable().get(0);
				for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
					DataTable.addElement(entry.getKey() + "").addText(
							entry.getValue() + "");
				}
			}

		} else {
			// TODO 如果不是回应数据的话用另外的格式，现在暂时没用到
		}
		return document.asXML();
	}

	/**
	 * 解析标签使用的是dom4j的技术 写的有点烂 TODO 看看是否可以优化
	 * 
	 * @param queryMsg
	 * @param document
	 */
	private static void getElementsDate(QueryMsg queryMsg, Document document) {
		// 设置编码
		queryMsg.setEncoding(document.getXMLEncoding());

		try {
			Node MethodName = document
					.selectSingleNode("//DocumentElement/MethodName");
			queryMsg.setMethodName(MethodName.getText());
		} catch (Exception e) {
		}
		try {
			Node DataTable = document
					.selectSingleNode("//DocumentElement/DataTable");
			List<Map<Object, Object>> lm = getDateTableMsg((Element) DataTable);
			queryMsg.setDataTable(lm);
		} catch (Exception e) {
		}
		try {
			Node Result = document.selectSingleNode("//DocumentElement/Result");
			queryMsg.setResult(Result.getText());
		} catch (Exception e) {
		}
		try {
			Node Error = document.selectSingleNode("//DocumentElement/Error");
			queryMsg.setError(Error.getText());
		} catch (Exception e) {
		}
		try {
			Node AccessKey = document
					.selectSingleNode("//DocumentElement/AccessKey");
			queryMsg.setAccessKey(AccessKey.getText());
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("rawtypes")
	private static List<Map<Object, Object>> getDateTableMsg(
			Element dateTableNode) {
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator i = dateTableNode.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			map.put(element.getName(), element.getText());
		}
		list.add(map);
		return list;
	}
}
