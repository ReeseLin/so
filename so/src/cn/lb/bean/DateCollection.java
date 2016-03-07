package cn.lb.bean;

/**
 * 
 * The name: 数据包装类
 * What to do: 
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
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
