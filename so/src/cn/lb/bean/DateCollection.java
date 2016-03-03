package cn.lb.bean;

/**
 * 数据包装类
 * 
 * @author lin
 * 
 */
public class DateCollection {
	private User user;

	public DateCollection(User user) {
		super();
		this.user = user;
	}

	public DateCollection() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
