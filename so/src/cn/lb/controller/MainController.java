package cn.lb.controller;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.lb.bean.DateCollection;
import cn.lb.bean.QueryMsg;
import cn.lb.bean.User;
import cn.lb.service.MainService;
import cn.lb.service.ServiceManager;
import cn.lb.utils.HttpUtils;
import cn.lb.utils.QueryUtils;

@Controller
@RequestMapping("/MainController")
public class MainController {

	/**
	 * 解析指定的xml格式 并指引到指定的service method
	 * 
	 * @throws Exception
	 */
	@RequestMapping("Dispatch")
	public void Dispatch(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		InputStream in = request.getInputStream();
		String receive = HttpUtils.getInputStreamMsg(in, "UTF-8");
		QueryMsg queryMsg = QueryUtils.parseXmlStringToQueryMsg(receive);
		System.out.println(receive);
		QueryMsg resultMsg = ServiceManager.execute(queryMsg);
		responserMsg(resultMsg, response);
	}

	/**
	 * 统一返回
	 * 
	 * @param resultMsg
	 * @param response
	 */
	private void responserMsg(QueryMsg resultMsg, HttpServletResponse response) {
		// TODO 返回信息
	}

}
