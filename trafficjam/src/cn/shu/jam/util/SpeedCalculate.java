package cn.shu.jam.util;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;

public class SpeedCalculate {
	private Path path;

	public SpeedCalculate(Path path) {
		this.path = path;
	}

	public Path calc() {
		GPS gps1;
		GPS gps2;
		GPS gps3;
		double t1;
		double t2;
		double speed1;
		double speed2;
		for (int i = 0; i < path.size() - 2;) {
			gps1 = path.getGps(i);
			gps2 = path.getGps(i + 1);
			gps3 = path.getGps(i + 2);
			t1 = gps3.getDate() - gps2.getDate();
			t2 = gps2.getDate() - gps1.getDate();
			speed1 = Distance.getByGPS(gps1, gps2) / t1;
			speed2 = Distance.getByGPS(gps2, gps3) / t2;
			gps2.setSpeed((speed1 + speed2) / 2 * 3.6);
			i++;
		}
		return path;
	}
}
