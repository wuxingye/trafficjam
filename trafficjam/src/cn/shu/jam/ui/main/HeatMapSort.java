package cn.shu.jam.ui.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.lang.GPSTransformException;

/**
 * ��������ͼ�����������������
 * 
 * @author wuxingye
 * @version
 */
public class HeatMapSort {
	private static String inFile = "D:\\a����GPS����\\��������ͼ\\hebing100��.txt";
	private static String outFile = "D:\\a����GPS����\\��������ͼ\\����ͼ100��.js";

	public static void main(String[] args) {
		GPSReader in = null;
		GPS gps = null;
		Path path = new Path();
		try {
			in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while ((gps = in.read()) != null) {
				path.add(gps);
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
		
		
		
	}

}
