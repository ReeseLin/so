package cn.lb.mapper;

import cn.lb.bean.DateCollection;



public interface UserMapper {
	
	 /**
     * 添加一个联系人，返回生成唯一id
     * @return
     * @throws Exception
     */
	public void insertUser(DateCollection dc)throws Exception;
}
