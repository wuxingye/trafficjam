package cn.shu.jam.ui.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.lang.GPSTransformException;

/**
 * �����ٶ���ĳһ����Χ��GPS��
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-24 ����12:32:01
 * @version 1.0
 */
public class GPSWithSpeed {
	private static String inFile = "D:\\a����GPS����\\hebing.txt";
	private static String outFile = "D:\\a����GPS����\\�����ٶȴ���0.5С��1.txt";

	public static void main(String[] args) {
		GPSReader in = null;
		GPS gps = null;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(outFile)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();

		try {
			in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int flag = 0;
		try {
			while ((gps = in.read()) != null) {
				System.out.println(++flag);
				if (gps.getSpeed() > 0.5 && gps.getSpeed() < 1) {
					sb.append(gps.toString()).append("\r\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GPSTransformException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
