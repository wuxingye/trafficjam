package cn.shu.jam.lang;

/**
 * 转换GPS坐标形式，String-->GPS类抛出的异常
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-5 下午9:56:26
 * @version 1.0
 */
public class GPSTransformException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public GPSTransformException(String msg) {
		super(msg);
	}
}
