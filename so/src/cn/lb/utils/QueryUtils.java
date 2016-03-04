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

public class QueryUtils {
	
	
	public static QueryMsg parseXmlStringToQueryMsg(String xmlString) throws Exception {

		QueryMsg queryMsg = new QueryMsg();
		Document document = DocumentHelper.parseText(xmlString);
		getElementsDate(queryMsg, document);
		return queryMsg;
	}
	
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
	private static List<Map<Object, Object>> getDateTableMsg(Element dateTableNode) {
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
