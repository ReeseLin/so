package cn.lb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * The name: 网络工具类
 * What to do: 
 *
 * @author ReeseLin 
 * @Email  172053362@qq.com
 *
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

	/**
	 * 对外写数据
	 * @param result
	 * @param response
	 * @throws IOException
	 */
	public static void writeOutMsg(String result, HttpServletResponse response) throws IOException {
		// TODO 对外写数据
		 PrintWriter out =response.getWriter();
		 out.write(result);
	}
}
