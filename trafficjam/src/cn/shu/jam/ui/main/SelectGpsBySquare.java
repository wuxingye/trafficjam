package cn.shu.jam.ui.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

public class SelectGpsBySquare {

	private static String inFile = "D:\\a分析GPS数据\\带有速度所有点\\带有速度.txt";
	private static String outFile = "D:\\a分析GPS数据\\带有速度所有点\\3.txt";
	private static double xMin = 116.433268;
	private static double xMax = 116.43368;
	private static double yMin = 39.910649;
	private static double yMax = 39.9109352;

	// private static String outFile =
	// "D:\\a分析GPS数据\\带有速度所有点\\116380885-39912288.txt";

	public static void main(String[] args) {
		GPSReader in = null;
		Path path = new Path();
		GPS gps1 = null;
		GPS gps2 = new GPS(0, 116.40576483519, 39.913055501727);
		// GPS gps2 = new GPS(0, 116.380885, 39.912288);
		try {
			in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int i = 0;
		double x;
		double y;
		try {
			while ((gps1 = in.read()) != null) {
				System.out.println(++i);
				x = gps1.getLongitude();
				y = gps1.getLatitude();
				if (x > xMin && x < xMax && y > yMin && y < yMax
						&& gps1.getSpeed() < 50) {
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
