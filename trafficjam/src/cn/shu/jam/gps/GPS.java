package cn.shu.jam.gps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将含有 <b>车号、GPS采集时间、经度、纬度</b> 的GPS数据作为类存储
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-5 下午12:20:52
 * @version 1.0
 */
public class GPS {
	private int location; // GPS在文件中的位置
	private int carName; // GPS对应的车号
	private double date;// GPS采集时间
	private double longitude;// GPS经度
	private double latitude;// GPS纬度
	private double speed = -1;// GPS对应速度，初始化为-1。
	private double gaussValue = -1;// GPS坐标的高斯值

	/**
	 * 构造函数，传入经纬度和车号即可
	 * 
	 * @param carName
	 *            车号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 */
	public GPS(int carName, double longitude, double latitude) {
		this.carName = carName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * 构造函数，传入经纬度和车号，以及长整型的时间，时间是1970年以来的秒数
	 * 
	 * @param carName
	 *            车号
	 * @param date
	 *            长整型的时间，时间是1970年以来的微秒数
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 */
	public GPS(int carName, double date, double longitude, double latitude) {
		this(carName, longitude, latitude);
		this.date = date;
	}

	/**
	 * 构造函数，传入经纬度和车号，以及字符串型的时间，时间格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param carName
	 *            车号
	 * @param date
	 *            字符串型的时间，时间格式"yyyy-MM-dd HH:mm:ss"
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
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
	 * 设置GPS数据出现的位置，比如文件中的位置，设置后可以通过{@link GPS#getLocation getLocation}获取
	 * 
	 * @see getLocation
	 */
	public void setLocation(int location) {
		this.location = location;
	}

	/**
	 * 获取GPS数据出现的位置，比如文件中的位置，必须使用{@link GPS#setLocation setLocation}设置才可以
	 * 
	 * @see setLocation
	 * @return 返回GPS出现的文件中或。。。中的位置
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * 设置经度
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 设置纬度
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
	 * 取得车名
	 * 
	 * @return 车名（车号）
	 */
	public int getcarName() {
		return carName;
	}

	/**
	 * 设置车名
	 * 
	 * @param carName
	 */
	public void setcarName(int carName) {
		this.carName = carName;
	}

	/**
	 * 取得GPS坐标采集时间,时间是1970年以来的秒数
	 * 
	 * @return GPS坐标采集时间,时间是1970年以来的秒数
	 */
	public double getDate() {
		return date;
	}

	/**
	 * 取得GPS坐标采集时间，时间格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return GPS坐标采集时间，时间格式"yyyy-MM-dd HH:mm:ss"
	 */
	public String getStringDate() {
		String stringDate = secondToDate(date);
		return stringDate;
	}

	/**
	 * 取得经度
	 * 
	 * @return GPS经度
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 取得纬度
	 * 
	 * @return GPS纬度
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 取得经纬度，字符串格式返回
	 * 
	 * @return 字符串经纬度
	 */
	public String getGpsCoord() {
		return longitude + "," + latitude;
	}

	/**
	 * 获取GPS坐标点对应的速度
	 * 
	 * @return GPS坐标点对应的宿舍速度
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * 设置GPS坐标点的速度
	 * 
	 * @param speed
	 *            double类型的速度值
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
	 * "yyyy-MM-dd HH:mm:ss"格式的时间转换为long的时间
	 * 
	 * @param date
	 *            String类型的时间
	 * @return long类型的时间
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
	 * long型时间转换为"yyyy-MM-dd HH:mm:ss"格式的时间
	 * 
	 * @param long类型的时间
	 * @return String类型的时间
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
	 * @return GPS的字符串形式
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