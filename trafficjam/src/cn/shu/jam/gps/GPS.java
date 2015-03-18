package cn.shu.jam.gps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������ <b>���š�GPS�ɼ�ʱ�䡢���ȡ�γ��</b> ��GPS������Ϊ��洢
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-5 ����12:20:52
 * @version 1.0
 */
public class GPS {
	private int location; // GPS���ļ��е�λ��
	private int carName; // GPS��Ӧ�ĳ���
	private double date;// GPS�ɼ�ʱ��
	private double longitude;// GPS����
	private double latitude;// GPSγ��
	private double speed = -1;// GPS��Ӧ�ٶȣ���ʼ��Ϊ-1��
	private double gaussValue = -1;// GPS����ĸ�˹ֵ

	/**
	 * ���캯�������뾭γ�Ⱥͳ��ż���
	 * 
	 * @param carName
	 *            ����
	 * @param longitude
	 *            ����
	 * @param latitude
	 *            γ��
	 */
	public GPS(int carName, double longitude, double latitude) {
		this.carName = carName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * ���캯�������뾭γ�Ⱥͳ��ţ��Լ������͵�ʱ�䣬ʱ����1970������������
	 * 
	 * @param carName
	 *            ����
	 * @param date
	 *            �����͵�ʱ�䣬ʱ����1970��������΢����
	 * @param longitude
	 *            ����
	 * @param latitude
	 *            γ��
	 */
	public GPS(int carName, double date, double longitude, double latitude) {
		this(carName, longitude, latitude);
		this.date = date;
	}

	/**
	 * ���캯�������뾭γ�Ⱥͳ��ţ��Լ��ַ����͵�ʱ�䣬ʱ���ʽ"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param carName
	 *            ����
	 * @param date
	 *            �ַ����͵�ʱ�䣬ʱ���ʽ"yyyy-MM-dd HH:mm:ss"
	 * @param longitude
	 *            ����
	 * @param latitude
	 *            γ��
	 */
	public GPS(int carName, String date, double longitude, double latitude) {
		this(carName, longitude, latitude);
		this.date = dateToSecond(date);
	}

	public GPS(int carName, String date, double longitude, double latitude,
			double speed, double gaussValue) {
		this(carName, longitude, latitude);
		this.date = dateToSecond(date);
		this.speed = speed;
		this.gaussValue = gaussValue;
	}

	public GPS(int carName, String date, double longitude, double latitude,
			double speed) {
		this(carName, longitude, latitude);
		this.date = dateToSecond(date);
		this.speed = speed;
	}

	/**
	 * ����GPS���ݳ��ֵ�λ�ã������ļ��е�λ�ã����ú����ͨ��{@link GPS#getLocation getLocation}��ȡ
	 * 
	 * @see getLocation
	 */
	public void setLocation(int location) {
		this.location = location;
	}

	/**
	 * ��ȡGPS���ݳ��ֵ�λ�ã������ļ��е�λ�ã�����ʹ��{@link GPS#setLocation setLocation}���òſ���
	 * 
	 * @see setLocation
	 * @return ����GPS���ֵ��ļ��л򡣡����е�λ��
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * ���þ���
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * ����γ��
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setPosition(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * ȡ�ó���
	 * 
	 * @return ���������ţ�
	 */
	public int getcarName() {
		return carName;
	}

	/**
	 * ���ó���
	 * 
	 * @param carName
	 */
	public void setcarName(int carName) {
		this.carName = carName;
	}

	/**
	 * ȡ��GPS����ɼ�ʱ��,ʱ����1970������������
	 * 
	 * @return GPS����ɼ�ʱ��,ʱ����1970������������
	 */
	public double getDate() {
		return date;
	}

	/**
	 * ȡ��GPS����ɼ�ʱ�䣬ʱ���ʽ"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return GPS����ɼ�ʱ�䣬ʱ���ʽ"yyyy-MM-dd HH:mm:ss"
	 */
	public String getStringDate() {
		String stringDate = secondToDate(date);
		return stringDate;
	}

	/**
	 * ȡ�þ���
	 * 
	 * @return GPS����
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * ȡ��γ��
	 * 
	 * @return GPSγ��
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * ȡ�þ�γ�ȣ��ַ�����ʽ����
	 * 
	 * @return �ַ�����γ��
	 */
	public String getGpsCoord() {
		return longitude + "," + latitude;
	}

	/**
	 * ��ȡGPS������Ӧ���ٶ�
	 * 
	 * @return GPS������Ӧ�������ٶ�
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * ����GPS�������ٶ�
	 * 
	 * @param speed
	 *            double���͵��ٶ�ֵ
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getGaussValue() {
		return gaussValue;
	}

	public void setGaussValue(double gaussValue) {
		this.gaussValue = gaussValue;
	}

	/**
	 * "yyyy-MM-dd HH:mm:ss"��ʽ��ʱ��ת��Ϊlong��ʱ��
	 * 
	 * @param date
	 *            String���͵�ʱ��
	 * @return long���͵�ʱ��
	 */
	public static double dateToSecond(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double seconds = 0;
		try {
			seconds = sdf.parse(date).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return seconds;
	}

	/**
	 * long��ʱ��ת��Ϊ"yyyy-MM-dd HH:mm:ss"��ʽ��ʱ��
	 * 
	 * @param long���͵�ʱ��
	 * @return String���͵�ʱ��
	 */
	public static String secondToDate(double seconds) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date((long) (seconds * 1000)));
		return date;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return GPS���ַ�����ʽ
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (location != 0) {
			sb.append(location).append(",");
		}
		sb.append(carName);
		sb.append(",").append(secondToDate(date)).append(",").append(longitude)
				.append(",").append(latitude);
		if (speed != -1) {
			sb.append(",").append(speed);
		}
		if (gaussValue != -1) {
			sb.append(",").append(gaussValue);
		}
		return sb.toString();
	}
}