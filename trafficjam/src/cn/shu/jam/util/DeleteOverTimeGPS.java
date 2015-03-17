package cn.shu.jam.util;

import java.util.ArrayList;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;

/**
 * ɾ��ʱ��������ָ��ֵ��GPS
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-21 ����10:32:28
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
		int location = 1;// ��λ��1��ʼ�������ѭ����1��ʼ����Ϊѭ����0��
		for (int i = 0; i < path.size() - 1;) {
			gps1 = path.getGps(i);
			gps2 = path.getGps(i + 1);
			if (gps2.getDate() - gps1.getDate() > timeInterval) {// �������timeInterval΢������ɾ������Ϊ�������̫��
				path.deleteGps(i);
				gps1.setLocation(location); // ��¼ɾ����λ��
				denoisedGps.add(gps1);
				location++;
			} else {
				i += 2; // ����������������ж���һ������������ɾ�������ƶ�λ��
				location += 2;
			} // ��һ��λ��
		}
		if (path.size() >= 2) {
			gps1 = path.getGps(path.size() - 2);
			gps2 = path.getGps(path.size() - 1);
			if (gps2.getDate() - gps1.getDate() > timeInterval) {// �������timeInterval΢������ɾ������Ϊ�������̫��
				path.deleteGps(path.size() - 1);
				gps2.setLocation(location); // ��¼ɾ����λ��
				denoisedGps.add(gps2);
			}
		}
		return denoisedGps;
	}
}
