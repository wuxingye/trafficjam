package cn.shu.jam.ui.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import cn.shu.jam.baidu.BatchGpsToBaiduGps;
import cn.shu.jam.baidu.GpsToBaiduGps;
import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.lang.GPSTransformException;

public class GPStoBaiduMain {
	public static void main(String[] args) throws IOException,
			GPSTransformException {
		System.setOut(new PrintStream(new File("D:/baidu.txt")));
		GPSReader in = new GPSReader("D:\\GPS×ø±ê´¦Àí\\a\\1.txt");
		GPS gps;
		Path path = new Path();
		while ((gps = in.read()) != null) {
			path.add(gps);
		}
		GpsToBaiduGps gpsToBaiduGps = new GpsToBaiduGps();
		BatchGpsToBaiduGps bgtb = new BatchGpsToBaiduGps(gpsToBaiduGps);
		System.out.println(bgtb.convert(path));
	}
}
