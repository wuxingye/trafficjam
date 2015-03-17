package cn.shu.jam.util;

import java.util.ArrayList;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;

/**
 * �������ڹ���GPS�豸����ɼ������ݣ������ظ��ɼ���ʱ���һ������������󣨳����������귶Χ��
 * <p>
 * Ĭ�Ϲ��˷�Χ100~130��30~50
 * <p>
 * ��Ҳ����ʹ��<tt>setRange</tt>�����趨���˷�Χ
 * 
 * <p>
 * ȥ��ֻ֧��<tt>Path</tt>���͵�����
 * <p>
 * �����Ҫת���ı������ݿ��е����ֻ꣬�轫����뵽<tt>{@link Path}</tt>�����У����˺�������ɡ�
 * 
 * 
 * @see Path
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-9 ����2:23:24
 * @version 1.0
 */
public class Denoising {
	private static double min_x = 100; // ���˵����귶Χ
	private static double max_x = 130; // ���˵����귶Χ
	private static double min_y = 30; // ���˵����귶Χ
	private static double max_y = 50; // ���˵����귶Χ

	/**
	 * ����ȥ�뷶Χ
	 * 
	 * @param min_x
	 *            ��С����
	 * @param max_x
	 *            ��󾭶�
	 * @param min_y
	 *            ��Сγ��
	 * @param max_y
	 *            ���γ��
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
	 * @return ��ɾ����GPS����
	 */
	public static ArrayList<GPS> denoise(Path path) {
		ArrayList<GPS> denoisedGps = new ArrayList<GPS>();
		GPS gps1;
		GPS gps2;
		int location = 2;// ��λ��2��ʼ����һ��λ�ò����ǡ������ѭ����1��ʼ����Ϊѭ����0��
		if (path.size() >= 1) {
			gps1 = path.getGps(0);
			if (gps1.getLongitude() < min_x// С����С����
					|| gps1.getLongitude() > max_x // ������󾭶�
					|| gps1.getLatitude() < min_y // С����Сγ��
					|| gps1.getLatitude() > max_y) {// �������γ��
				path.deleteGps(0); // �������������е�һ����ɾ��
				gps1.setLocation(0); // ��¼ɾ����λ��
				denoisedGps.add(gps1); // ɾ��λ�õ�GPS��Ϣ��¼����
			}
		}
		for (int i = 1; i < path.size();) {
			gps1 = path.getGps(i);
			gps2 = path.getGps(i - 1);
			if (gps1.getLongitude() < min_x// С����С����
					|| gps1.getLongitude() > max_x // ������󾭶�
					|| gps1.getLatitude() < min_y // С����Сγ��
					|| gps1.getLatitude() > max_y// �������γ��
					|| gps1.getDate() - gps2.getDate() == 0) {// ����GPS����ɼ�ʱ��һ��
				path.deleteGps(i); // �������������е�һ����ɾ��
				gps1.setLocation(location); // ��¼ɾ����λ��
				denoisedGps.add(gps1); // ɾ��λ�õ�GPS��Ϣ��¼����
			} else
				i++; // ����������������ж���һ������������ɾ�������ƶ�λ��
			location++; // ��һ��λ��
		}
		return denoisedGps; // ���ر�ɾ����GPS��Ϣ
	}
}
