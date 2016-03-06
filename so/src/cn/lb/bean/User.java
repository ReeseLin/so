package cn.lb.bean;

/**
 * 用户类和数据库表一致
 * @author lin
 *
 */
public class User {
	
	private int userid;
	private String username;

	public User(int userid, String username) {
		super();
		this.userid = userid;
		this.username = username;
	}

	public User() {
		super();
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + "]";
	}
	
}
