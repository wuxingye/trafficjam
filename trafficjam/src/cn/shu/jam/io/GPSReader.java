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
 * ������Զ�ȡ�ı��е����ݣ���ת��ΪGPS��ĸ�ʽ��
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-5 ����3:49:19
 * @version 1.0
 */
public class GPSReader {
	private BufferedReader bufferedReader;
	private String fileName;
	private int lineNumber;

	/**
	 * ����ָ���ļ�ת��ΪGPSReader
	 * 
	 * @param file
	 *            File����
	 * @throws FileNotFoundException
	 */
	public GPSReader(File file) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.bufferedReader = br;
		this.fileName = file.getName();
		this.lineNumber = 0;
	}

	/**
	 * ����ָ���ļ�ת��ΪGPSReader
	 * 
	 * @param fileName
	 *            String����
	 * @throws FileNotFoundException
	 */
	public GPSReader(String fileName) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		this.bufferedReader = br;
		this.fileName = fileName;
		this.lineNumber = 0;
	}

	/**
	 * ��ȡҪ��ȡ���ļ�
	 * 
	 * @return �ļ���
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * ��ǰ��ȡ�����ļ��е���һ��
	 * 
	 * @return ���ص�ǰ�к�
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * ��ȡһ��GPS����
	 * 
	 * @return ���ض�ȡ����GPS����
	 * @throws IOException
	 * @throws GPSTransformException
	 */
	public GPS read() throws IOException, GPSTransformException {
		String str = bufferedReader.readLine();
		if (str == null) {
			return null;
		}
		GPS gps = GPSUtil.stringToGPS(str); // ����ȡ����Stringת��ΪGPS����
		return gps;
	}

	/**
	 * �ر���
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
