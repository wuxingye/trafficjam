package cn.shu.jam.ui.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;
import cn.shu.jam.util.Distance;

public class SelectGpsByRange {
	private static String inFile = "D:\\a分析GPS数据\\带有速度所有点\\带有速度.txt";
	private static String outFile = "D:\\a分析GPS数据\\带有速度所有点\\3.116.40576483519,39.913055501727.txt";
//	private static String outFile = "D:\\a分析GPS数据\\带有速度所有点\\116380885-39912288.txt";

	public static void main(String[] args) {
		GPSReader in = null;
		Path path = new Path();
		GPS gps1 = null;
		GPS gps2 = new GPS(0, 116.40576483519,39.913055501727);
		// GPS gps2 = new GPS(0, 116.380885, 39.912288);
		try {
			in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int i = 0;
		try {
			while ((gps1 = in.read()) != null) {
				System.out.println(++i);
				if (Distance.getByGPS(gps1, gps2) < 50 && gps1.getSpeed() < 50) {
					path.add(gps1);
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
		GPSWriter out = null;
		try {
			out = new GPSWriter(outFile);
			out.write(path);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
