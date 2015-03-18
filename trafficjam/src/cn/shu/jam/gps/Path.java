package cn.shu.jam.gps;

import java.util.ArrayList;

/**
 * ���й�ϵ��GPS�������Ϊ·��
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-5 ����3:27:48
 * @version 1.0
 */
public class Path {
	private String carName; // ·�����������ţ�
	private int pathId;// ·����ID��·��ID������Ϊ0
	private ArrayList<GPS> gpsList;// ·���е�GPS�������ݽṹ����Set��

	/**
	 * ���캯��
	 */
	public Path() {
		gpsList = new ArrayList<GPS>();
	}

	/**
	 * ����·����ID����·��
	 * 
	 * @param pathId
	 *            ָ��·����ID
	 */
	public Path(int pathId) {
		this.pathId = pathId;
		gpsList = new ArrayList<GPS>();
	}

	/**
	 * ����·�����������ţ���·����ID����·��
	 * 
	 * @param carName
	 *            ·�����������ţ�
	 * @param pathId
	 *            ·����ID��·��ID������Ϊ0
	 */
	public Path(String carName, int pathId) {
		this(pathId);
		this.carName = carName;
	}

	/**
	 * ȡ�ó���
	 * 
	 * @return ����
	 */
	public String getCarName() {
		return carName;
	}

	/**
	 * ���ó���
	 * 
	 * @param taxiName
	 *            ����
	 */
	public void setCarName(String taxiName) {
		this.carName = taxiName;
	}

	/**
	 * ��ȡ·��ID
	 * 
	 * @return ·��ID
	 */
	public int getPathId() {
		return pathId;
	}

	/**
	 * ��ȡ·���а�����GPS
	 * 
	 * @return ·���а�����GPS
	 */
	public ArrayList<GPS> getGpsList() {
		return gpsList;
	}

	/**
	 * ����·��Path�е�λ�û�ȡGPS
	 * 
	 * @param index
	 *            ·��Pathλ��
	 * @return ָ��λ�ô���GPS
	 */
	public GPS getGps(int index) {
		return gpsList.get(index);
	}

	/**
	 * ��·�������GPS����
	 * 
	 * @param gps
	 *            GPS����
	 */
	public void add(GPS gps) {
		gpsList.add(gps);
	}

	/**
	 * ɾ��ָ��·��Pathλ�ô���GPS����
	 * 
	 * @param index
	 *            ·��Pathλ��
	 */
	public void deleteGps(int index) {
		gpsList.remove(index);
	}

	/**
	 * ��ȡ·���а�����GPS���������Ҳ����·����С
	 * 
	 * @return GPS�������
	 */
	public int size() {
		return gpsList.size();
	}

	/**
	 * ��ȡPath�е����꣬�ַ��������ʽ����
	 * 
	 * @return �ַ��������ʽ��Path�е���������
	 */
	public ArrayList<String> getPathCoords() {
		ArrayList<String> coords = new ArrayList<String>();
		for (GPS gps : gpsList) {
			coords.add(gps.getGpsCoord());
		}
		return coords;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (carName != null) {
			sb.append("carName:").append(carName).append(" ");
		}
		if (pathId != 0) { // �����0��toString���ܽ�����ӣ�����·��ID����Ϊ0
			sb.append("pathId:").append(pathId);
		}
		for (GPS gps : gpsList) {
			sb.append(gps.toString()).append("\r\n");
		}
		return sb.toString();
	}
}
