package cn.shu.jam.util;

import cn.shu.jam.gps.GPS;

/**
 * ��������GPS����֮��ľ���
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-5 ����11:08:45
 * @version 1.0
 */
public class Distance {

	private static double EARTH_RADIUS = 6378137;// ����뾶6370996.81

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * ��ȡ��������֮��ľ��룬��λ��m
	 * 
	 * @param longitude1
	 *            ��һ��GPS�ľ���
	 * @param latitude1
	 *            ��һ��GPS��γ��
	 * @param longitude2
	 *            �ڶ���GPS�ľ���
	 * @param latitude2
	 *            �ڶ���GPS��γ��
	 * @return ��������֮��ľ��룬��λ��m
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
	 * ��ȡ����GPS֮��ľ��룬��λ��m
	 * 
	 * @param gps1
	 *            ��һ��GPS����
	 * @param gps2
	 *            �ڶ���GPS����
	 * @return ����GPS֮��ľ��룬��λ��m
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