package cn.utils;

public enum Enum {
	A("heheheh"),
	B("faf"),
	C("afad");

	final private String msg;
	
	Enum(String name) {
		msg=name;
	}
	
	public String getMsg() {
		return msg;
	}
	
}
