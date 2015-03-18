package cn.shu.jam.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.shu.jam.gps.Path;

/**
 * �����������ļ���д���ݣ�����дGPS�������ݻ���Path��������
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-6 ����10:32:58
 * @version 1.0
 */
public class GPSWriter {
	private BufferedWriter bufferedWriter;
	private String fileName;

	/**
	 * ָ��Ҫд����ļ�������д����
	 * 
	 * @param file
	 *            File����
	 * @throws IOException
	 */
	public GPSWriter(File file) throws IOException {
		bufferedWriter = new BufferedWriter(new FileWriter(file));
		this.fileName = file.getName();
	}

	/**
	 * ָ��Ҫд����ļ�������д����
	 * 
	 * @param file
	 *            String����
	 * @throws IOException
	 */
	public GPSWriter(String fileName) throws IOException {
		bufferedWriter = new BufferedWriter(new FileWriter(fileName));
		this.fileName = fileName;
	}

	/**
	 * ��ȡҪд����ļ���
	 * 
	 * @return д��������ļ���
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * �˷������Խ�GPS���ݻ�Path����д����ָ���ļ���
	 * <p>
	 * ������ʽ��gps.write()��path.write();
	 * 
	 * @throws IOException
	 */
	public void write(Path path) throws IOException {
		bufferedWriter.write(path.toString());
	}

	/**
	 * �ر�GPSWriter��
	 * <p>
	 * <b>һ��Ҫ�ر�GPSWriter������Ȼ������������ڻ������������ļ���</b>
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (bufferedWriter == null) {
			return;
		}
		bufferedWriter.close();
	}

}
