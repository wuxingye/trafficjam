package cn.shu.jam.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import cn.shu.jam.gps.GPS;
import cn.shu.jam.lang.GPSTransformException;
import cn.shu.jam.util.GPSUtil;

/**
 * 此类可以读取文本中的数据，并转化为GPS类的格式流
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-5 下午3:49:19
 * @version 1.0
 */
public class GPSReader {
	private BufferedReader bufferedReader;
	private String fileName;
	private int lineNumber;

	/**
	 * 根据指定文件转换为GPSReader
	 * 
	 * @param file
	 *            File类型
	 * @throws FileNotFoundException
	 */
	public GPSReader(File file) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.bufferedReader = br;
		this.fileName = file.getName();
		this.lineNumber = 0;
	}

	/**
	 * 根据指定文件转换为GPSReader
	 * 
	 * @param fileName
	 *            String类型
	 * @throws FileNotFoundException
	 */
	public GPSReader(String fileName) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		this.bufferedReader = br;
		this.fileName = fileName;
		this.lineNumber = 0;
	}

	/**
	 * 获取要读取的文件
	 * 
	 * @return 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 当前读取到了文件中的哪一行
	 * 
	 * @return 返回当前行号
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * 读取一个GPS对象
	 * 
	 * @return 返回读取到的GPS对象
	 * @throws IOException
	 * @throws GPSTransformException
	 */
	public GPS read() throws IOException, GPSTransformException {
		String str = bufferedReader.readLine();
		if (str == null) {
			return null;
		}
		GPS gps = GPSUtil.stringToGPS(str); // 将读取到的String转换为GPS对象
		return gps;
	}

	/**
	 * 关闭流
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (bufferedReader == null)
			return;
		bufferedReader.close();
		bufferedReader = null;
	}
}
