package cn.utils;
/*package cn.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;

public class TestAll {

	private String postMsg = "<?xml version='1.0' encoding='UTF-8' ?>"
			+ "<DocumentElement>" + "<MethodName>GetUserID</MethodName>"
			+ "<AccessKey>123123123123</AccessKey>" + "<DataTable>"
			+ "<username>admin</username><userid>123154</userid>" + "</DataTable>"
			+ "</DocumentElement>";

	@Test
	public void test() throws Exception {
		System.out.println(postMsg);

		Document document = DocumentHelper.parseText(postMsg);

		// 得到编码
		System.out.println(document.getXMLEncoding());

		Node node = document.selectSingleNode("//DocumentElement/MethodName");
		System.out.println("title=" + node.getName() + "/data="
				+ node.getText());

		Element node1 = (Element) document
				.selectSingleNode("//DocumentElement/DataTable");

		System.out.println("title=" + node1.getName() + "/data="
				+ node1.getText());
		for (Iterator i = node1.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			System.out.println(element.getName() + "////" + element.getText());
		}
	}

	@Test
	public void testMyUtils() throws Exception {
		QueryMsg queryMsg = parseXmlStringToQueryMsg(postMsg);
		System.out.println(queryMsg);
	}

	public QueryMsg parseXmlStringToQueryMsg(String xmlString) throws Exception {

		QueryMsg queryMsg = new QueryMsg();
		Document document = DocumentHelper.parseText(xmlString);
		getElementsDate(queryMsg, document);
		return queryMsg;
	}

	private void getElementsDate(QueryMsg queryMsg, Document document) {
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

	private List<Map<Object, Object>> getDateTableMsg(Element dateTableNode) {
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
*/