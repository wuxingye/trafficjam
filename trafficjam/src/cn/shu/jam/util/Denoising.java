package cn.shu.jam.util;

import java.util.ArrayList;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;

/**
 * 此类用于过滤GPS设备错误采集的数据，比如重复采集（时间会一样）和坐标错误（超出正常坐标范围）
 * <p>
 * 默认过滤范围100~130，30~50
 * <p>
 * 你也可以使用<tt>setRange</tt>方法设定过滤范围
 * 
 * <p>
 * 去噪只支持<tt>Path</tt>类型的数据
 * <p>
 * 如果你要转换文本或数据库中的坐标，只需将其读入到<tt>{@link Path}</tt>对象中，过滤后输出即可。
 * 
 * 
 * @see Path
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-9 下午2:23:24
 * @version 1.0
 */
public class Denoising {
	private static double min_x = 100; // 过滤的坐标范围
	private static double max_x = 130; // 过滤的坐标范围
	private static double min_y = 30; // 过滤的坐标范围
	private static double max_y = 50; // 过滤的坐标范围

	/**
	 * 设置去噪范围
	 * 
	 * @param min_x
	 *            最小经度
	 * @param max_x
	 *            最大经度
	 * @param min_y
	 *            最小纬度
	 * @param max_y
	 *            最大纬度
	 */
	public static void setRange(double min_x, double max_x, double min_y,
			double max_y) {
		Denoising.min_x = min_x;
		Denoising.max_x = max_x;
		Denoising.min_y = min_y;
		Denoising.max_y = max_y;
	}

	/**
	 * 
	 * @param path
	 * @return 被删除的GPS详情
	 */
	public static ArrayList<GPS> denoise(Path path) {
		ArrayList<GPS> denoisedGps = new ArrayList<GPS>();
		GPS gps1;
		GPS gps2;
		int location = 2;// 从位置2开始，第一个位置不考虑。下面的循环从1开始，因为循环有0。
		if (path.size() >= 1) {
			gps1 = path.getGps(0);
			if (gps1.getLongitude() < min_x// 小于最小经度
					|| gps1.getLongitude() > max_x // 大于最大经度
					|| gps1.getLatitude() < min_y // 小于最小纬度
					|| gps1.getLatitude() > max_y) {// 大于最大纬度
				path.deleteGps(0); // 满足上述条件中的一个则删除
				gps1.setLocation(0); // 记录删除的位置
				denoisedGps.add(gps1); // 删除位置的GPS信息记录下来
			}
		}
		for (int i = 1; i < path.size();) {
			gps1 = path.getGps(i);
			gps2 = path.getGps(i - 1);
			if (gps1.getLongitude() < min_x// 小于最小经度
					|| gps1.getLongitude() > max_x // 大于最大经度
					|| gps1.getLatitude() < min_y // 小于最小纬度
					|| gps1.getLatitude() > max_y// 大于最大纬度
					|| gps1.getDate() - gps2.getDate() == 0) {// 两个GPS坐标采集时间一样
				path.deleteGps(i); // 满足上述条件中的一个则删除
				gps1.setLocation(location); // 记录删除的位置
				denoisedGps.add(gps1); // 删除位置的GPS信息记录下来
			} else
				i++; // 不满足上述则可以判断下一个，满足上述删除后不用移动位置
			location++; // 下一个位置
		}
		return denoisedGps; // 返回被删除的GPS信息
	}
}
