package cn.shu.jam.util;

import java.util.ArrayList;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;

/**
 * 删除时间间隔超过指定值的GPS
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-21 上午10:32:28
 * @version 1.0
 */
public class DeleteOverTimeGPS {
	private long timeInterval;

	public DeleteOverTimeGPS(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public ArrayList<GPS> delete(Path path) {
		ArrayList<GPS> denoisedGps = new ArrayList<GPS>();
		GPS gps1;
		GPS gps2;
		int location = 1;// 从位置1开始。下面的循环从1开始，因为循环有0。
		for (int i = 0; i < path.size() - 1;) {
			gps1 = path.getGps(i);
			gps2 = path.getGps(i + 1);
			if (gps2.getDate() - gps1.getDate() > timeInterval) {// 如果大于timeInterval微秒秒则删除，因为采样间隔太大
				path.deleteGps(i);
				gps1.setLocation(location); // 记录删除的位置
				denoisedGps.add(gps1);
				location++;
			} else {
				i += 2; // 不满足上述则可以判断下一个，满足上述删除后不用移动位置
				location += 2;
			} // 下一个位置
		}
		if (path.size() >= 2) {
			gps1 = path.getGps(path.size() - 2);
			gps2 = path.getGps(path.size() - 1);
			if (gps2.getDate() - gps1.getDate() > timeInterval) {// 如果大于timeInterval微秒秒则删除，因为采样间隔太大
				path.deleteGps(path.size() - 1);
				gps2.setLocation(location); // 记录删除的位置
				denoisedGps.add(gps2);
			}
		}
		return denoisedGps;
	}
}
