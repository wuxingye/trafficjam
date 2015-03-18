package cn.shu.jam.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.shu.jam.gps.Path;

/**
 * 本类用于向文件中写数据，可以写GPS对象数据或者Path对象数据
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-6 上午10:32:58
 * @version 1.0
 */
public class GPSWriter {
	private BufferedWriter bufferedWriter;
	private String fileName;

	/**
	 * 指定要写入的文件，构造写入流
	 * 
	 * @param file
	 *            File类型
	 * @throws IOException
	 */
	public GPSWriter(File file) throws IOException {
		bufferedWriter = new BufferedWriter(new FileWriter(file));
		this.fileName = file.getName();
	}

	/**
	 * 指定要写入的文件，构造写入流
	 * 
	 * @param file
	 *            String类型
	 * @throws IOException
	 */
	public GPSWriter(String fileName) throws IOException {
		bufferedWriter = new BufferedWriter(new FileWriter(fileName));
		this.fileName = fileName;
	}

	/**
	 * 获取要写入的文件名
	 * 
	 * @return 写入输出的文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 此方法可以将GPS数据或Path数据写出到指定文件中
	 * <p>
	 * 调用形式，gps.write()或path.write();
	 * 
	 * @throws IOException
	 */
	public void write(Path path) throws IOException {
		bufferedWriter.write(path.toString());
	}

	/**
	 * 关闭GPSWriter流
	 * <p>
	 * <b>一定要关闭GPSWriter流，不然会造成数据流在缓冲区读不到文件中</b>
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
