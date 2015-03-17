package test;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;

public class Test {

	public static void main(String[] args) throws Exception {
		GPSReader in = new GPSReader("D:\\a分析GPS数据\\aaa\\1440.txt");
		String outDir = "D:\\a分析GPS数据\\aaa\\";
		Path path = new Path();
		GPS gps = null;

		GPSWriter out1 = new GPSWriter(outDir + 1 + ".txt");
		GPSWriter out2 = new GPSWriter(outDir + 2 + ".txt");
		GPSWriter out3 = new GPSWriter(outDir + 3 + ".txt");

		Path path1 = new Path();
		Path path2 = new Path();
		Path path3 = new Path();

		while ((gps = in.read()) != null) {
			path.add(gps);
		}
		in.close();
		GPS gpsTemp;
		double longitude;
//		double latitude;
		for (int i = 0; i < path.size(); i++) {
			gpsTemp = path.getGps(i);
			longitude = gpsTemp.getLongitude();
//			latitude = gpsTemp.getLatitude();
			if (longitude > 116.3845) {
				path1.add(gpsTemp);
			} else if (longitude > 116.3844) {
				path2.add(gpsTemp);
			} else
				path3.add(gpsTemp);
		}

		out1.write(path1);
		out2.write(path2);
		out3.write(path3);
		out1.close();
		out2.close();
		out3.close();
	}
}
