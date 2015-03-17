package cn.shu.jam.lang;

/**
 * 转换成百度坐标抛出的异常
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-9 下午4:21:11
 * @version 1.0
 */
public class ConvertBaiduGPSException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConvertBaiduGPSException(String message) {
		super(message);
	}
}
