package cn.lb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lb.bean.DateCollection;
import cn.lb.bean.User;
import cn.lb.service.MainService;

@Controller
@RequestMapping("/MainController")
public class MainController {

	@Autowired
	private MainService ms;

	@RequestMapping("/InsertUser")
	public void InsertUser() throws Exception {
		DateCollection dc = new DateCollection();
		User user = new User();
		user.setUsername("test1");
		dc.setUser(user);
		ms.insertUser(dc);
		int id = dc.getUser().getUserid();
		System.out.println(id);
	}
}
