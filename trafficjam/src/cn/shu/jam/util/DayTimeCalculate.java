package cn.shu.jam.util;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;

public class DayTimeCalculate {
	private Path path;

	public DayTimeCalculate(Path path) {
		this.path = path;
	}

	public Path calc() {
		GPS gps;
		for (int i = 0; i < path.size() - 2;) {
			gps = path.getGps(i);
			int time = Integer.parseInt(gps.getStringDate().split(" ")[1]
					.split(":")[0]);
			if (time < 6 || time > 22) {
				path.deleteGps(i);
			} else
				i++;
		}
		return path;
	}
}
