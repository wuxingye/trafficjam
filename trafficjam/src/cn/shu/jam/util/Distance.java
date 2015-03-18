package cn.shu.jam.util;

import cn.shu.jam.gps.GPS;

/**
 * 计算两个GPS坐标之间的距离
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-5 下午11:08:45
 * @version 1.0
 */
public class Distance {

	private static double EARTH_RADIUS = 6378137;// 地球半径6370996.81

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 获取两个坐标之间的距离，单位：m
	 * 
	 * @param longitude1
	 *            第一个GPS的经度
	 * @param latitude1
	 *            第一个GPS的纬度
	 * @param longitude2
	 *            第二个GPS的经度
	 * @param latitude2
	 *            第二个GPS的纬度
	 * @return 两个坐标之间的距离，单位：m
	 */
	public static double getByLatLon(double longitude1, double latitude1,
			double longitude2, double latitude2) {
		double radLat1 = rad(latitude1);
		double radLat2 = rad(latitude2);
		double a = radLat1 - radLat2;
		double b = rad(longitude1) - rad(longitude2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		distance = distance * EARTH_RADIUS;
		return distance;
	}

	/**
	 * 获取两个GPS之间的距离，单位：m
	 * 
	 * @param gps1
	 *            第一个GPS对象
	 * @param gps2
	 *            第二个GPS对象
	 * @return 两个GPS之间的距离，单位：m
	 */
	public static double getByGPS(GPS gps1, GPS gps2) {
		double longitude1 = gps1.getLongitude();
		double latitude1 = gps1.getLatitude();
		double longitude2 = gps2.getLongitude();
		double latitude2 = gps2.getLatitude();
		double distance = getByLatLon(longitude1, latitude1, longitude2,
				latitude2);
		return distance;

	}

}