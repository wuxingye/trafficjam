package cn.shu.jam.ui.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.lang.GPSTransformException;

/**
 * 根据热力图中坐标个数进行排序
 * 
 * @author wuxingye
 * @version
 */
public class HeatMapSort {
	private static String inFile = "D:\\a分析GPS数据\\生成热力图\\hebing100米.txt";
	private static String outFile = "D:\\a分析GPS数据\\生成热力图\\热力图100米.js";

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
