package cn.lb.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lb.bean.QueryMsg;
import cn.lb.service.ServiceManager;
import cn.lb.utils.HttpUtils;
import cn.lb.utils.QueryUtils;

@Controller
@RequestMapping("/MainController")
public class MainController {

//	@Autowired
//	private MainService ms;

	/**
	 * 指引到指定的method name中
	 * 
	 * @throws Exception
	 */
	@RequestMapping("")
	public void Dispatch(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		InputStream in = request.getInputStream();
		String receive = HttpUtils.getInputStreamMsg(in,"UTF-8");
		QueryMsg queryMsg = QueryUtils.parseXmlStringToQueryMsg(receive);
		System.out.println(receive);
		ServiceManager.execute(queryMsg);
	}

	
	
	
	/*@RequestMapping("/InsertUser")
	public void InsertUser() throws Exception {
		DateCollection dc = new DateCollection();
		User user = new User();
		user.setUsername("test1");
		dc.setUser(user);
		ms.insertUser(dc);
		int id = dc.getUser().getUserid();
		System.out.println(id);
	}*/
}
