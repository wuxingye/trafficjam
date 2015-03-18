package cn.shu.jam.util;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.lang.GPSTransformException;

/**
 * ����GPS�Ĺ�����
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-5 ����11:04:09
 * @version 1.0
 */
public class GPSUtil {
	/**
	 * ��String���͵�GPS����ת��ΪGPS����
	 * 
	 * @param gpsString
	 *            "1,2008-02-02 15:36:08,116.51172,39.92123"���ָ�ʽ������
	 * @return ת�����gps����
	 * @throws GPSTransformException
	 */
	public static GPS stringToGPS(String gpsString)
			throws GPSTransformException {
		String[] gpsData = gpsString.split(",");
		if (gpsData.length != 4) {
			if (gpsData.length == 5) {
				GPS gps = new GPS(Integer.parseInt(gpsData[0]), gpsData[1],
						Double.parseDouble(gpsData[2]),
						Double.parseDouble(gpsData[3]),
						Double.parseDouble(gpsData[4]));
				return gps;
			} else if (gpsData.length == 6) {
				GPS gps = new GPS(Integer.parseInt(gpsData[0]), gpsData[1],
						Double.parseDouble(gpsData[2]),
						Double.parseDouble(gpsData[3]),
						Double.parseDouble(gpsData[4]),
						Double.parseDouble(gpsData[5]));
				return gps;
			} else
				throw new GPSTransformException("GPS�ַ���:\"" + gpsString
						+ "\"��ʽ����");
		}
		GPS gps = new GPS(Integer.parseInt(gpsData[0]), gpsData[1],
				Double.parseDouble(gpsData[2]), Double.parseDouble(gpsData[3]));
		return gps;
	}

	public static double getGaussValue(double dis, double sigma) {
		double gauss = Math.pow(Math.E,
				-(Math.pow(dis, 2) / (2 * Math.pow(sigma, 2))));
		return gauss;
	}
}
