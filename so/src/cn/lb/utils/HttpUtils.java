package cn.lb.utils;

import java.io.InputStream;

/**
 * 网络工具类
 * @author lin
 *
 */
public class HttpUtils {
	
	/**
	 * 得到一个InputStream中的数据
	 * @param in输入流
	 * @param charset编码格式
	 * @return
	 * @throws Exception
	 */
	public static String getInputStreamMsg(InputStream in,String charset) throws Exception {
		byte[] buffer = new byte[1024];
		int i = 0;
		StringBuffer sb = new StringBuffer();
		while((i=in.read(buffer))!=-1){
			String msg = new String(buffer, 0, i,charset);
			sb.append(msg);
		}
		return sb.toString();
	}
}
