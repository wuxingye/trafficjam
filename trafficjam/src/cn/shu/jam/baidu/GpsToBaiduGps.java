package cn.shu.jam.baidu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.lang.ConvertBaiduGPSException;

/**
 * <p>
 * 将GPS坐标转换为百度GPS坐标，返回json或xml数据默认json
 * <p>
 * 可以指定转换源from坐标类型和目的to坐标类型
 * <p>
 * 最多转换100个
 * <p>
 * <a href="http://developer.baidu.com/map/changeposition.htm"> 百度坐标转换API链接</a>
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-6 下午4:29:31
 * @version 1.0
 */
public class GpsToBaiduGps implements BaiduGpsConstants{

	private ArrayList<String> coords;// 源坐标
	private int from;// 源坐标类型 1，2，3，4，5，6，7，8可用
	private int to;// 目的坐标类型有两种可供选择：5,6
	private String output;
	private String requestURL;
	private int carName;// 转换的是哪一辆车

	/**
	 * 创建一个以json为返回的将设备GPS转换为百度GPS的转换器
	 */
	public GpsToBaiduGps() {
		this(DEVICE, BAIDU, JSON);
		coords = new ArrayList<String>();
	}

	/**
	 * 构造一个DIY的转换器，指定类型和结果格式
	 * 
	 * @param from
	 *            源坐标类型
	 * @param to
	 *            目的坐标类型
	 * @param output
	 *            返回结果格式
	 */
	public GpsToBaiduGps(int from, int to, String output) {
		this.from = from;
		this.to = to;
		this.output = output;
		coords = new ArrayList<String>();
	}

	/**
	 * @return 返回请求的URL，需先调用{@link GpsToBaiduGps#initURL initURL}方法
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param coord
	 *            添加待转换的坐标，要转换的 "116.567,39.678"格式的字符串坐标
	 */
	public void addCoords(String coord) {
		coords.add(coord);
	}

	/**
	 * @param gps
	 *            添加待转换的坐标， 要转换的GPS对象
	 */
	public void addCoords(GPS gps) {
		coords.add(gps.getGpsCoord());
		if (carName == 0) {
			carName = gps.getcarName();
		}
	}

	/**
	 * 
	 * @param path
	 *            添加待转换的坐标，要转换的Path对象
	 */
	public void addCoords(Path path) {
		coords.addAll(path.getPathCoords());
		if (carName == 0) {
			carName = path.getGps(0).getcarName();
		}
	}

	/**
	 * 
	 * @param coords
	 *            添加待转换的 "116.567,39.678"格式的字符串坐标(批量ArrayList)
	 */
	public void addCoords(ArrayList<String> coords) {
		coords.addAll(coords);
	}

	/**
	 * 清除待转换的坐标
	 */
	public void clearCoords() {
		coords.clear();
	}

	/**
	 * 
	 * @return 返回待转换的坐标
	 */
	public ArrayList<String> getCoords() {
		return coords;
	}

	/**
	 * 初始化向百度GPS转换接口请求的URL
	 * <p>
	 * 初始化的请求URL可以用{@link GpsToBaiduGps#getRequestURL getRequestURL}得到
	 * 
	 * @throws ConvertBaiduGPSException
	 */

	public int getCarName() {
		return carName;
	}

	public void initURL() throws ConvertBaiduGPSException {
		if (coords.size() > MAX) { // 此类只能转换100个，如果大于这个数，则抛出转换异常
			throw new ConvertBaiduGPSException("一次最多只能转换100个！当前"
					+ coords.size() + "个！");
		}
		StringBuffer url = new StringBuffer();// 使用StringBuffer拼接请求URL
		url.append(DN).append("/").append(SN).append("/").append(VERSION)
				.append("/?coords="); // 拼接服务地址
										// http://api.map.baidu.com/geoconv/v1/?coords=
		for (String coord : coords) {
			url.append(coord).append(";"); // 拼接转换坐标，形式：114.2189,29.57542;114.2189,29.57542
		}
		url.deleteCharAt(url.length() - 1);// 删除最后一个“;”
		url.append("&output=").append(output).append("&from=").append(from)
				.append("&to=").append(to).append("&ak=").append(AK); // 拼接最后的请求信息&output=json&from=1&to=1&ak=你的密钥
		requestURL = url.toString(); // 生成请求URL
	}

	/**
	 * 转换坐标，结果以指定的json或xml格式返回
	 * 
	 * @return 返回转换坐标的结果，以指定的json或xml格式返回
	 * @throws ConvertBaiduGPSException
	 */
	public String convert() throws ConvertBaiduGPSException {
		if (requestURL == null) { // 请求地址为空，说明为初始化URL，则抛出转换异常
			throw new ConvertBaiduGPSException("没有初始化转换URL！调用方法initURL！");
		}
		if (coords.size() == 0) {// 待转换的坐标为空，转换没有意义，抛出转换异常
			throw new ConvertBaiduGPSException("没有添加待转换的坐标！调用方法addCoords！");
		}
		StringBuffer sb = null; // 用StringBuffer拼接转换结果，作为返回参数

		try {
			URL url = new URL(requestURL); // 从requestURL中获取返回数据
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (IOException e) {// 从requestURL获取数据中可能会出异常
			throw new ConvertBaiduGPSException("转换为百度GPS失败!!!错误消息："
					+ e.getMessage() + "原因：" + e.getCause() + "类："
					+ e.getClass().getName());
		}

		String convertResult = sb.toString();
		return convertResult;// 返回转换结果
	}

	/**
	 * 如果给一个请求URL <br>
	 * 格式：
	 * <p>
	 * http://api.map.baidu.com/geoconv/v1/?coords=114.2189,
	 * 29.57542;114.218,29.575&output=json&from=1&to=5&ak=你的密钥
	 * <p>
	 * 返回转换结果
	 * 
	 * @param requestURL
	 *            格式 ：
	 *            <p>
	 *            http://api.map.baidu.com/geoconv/v1/?coords=114.2189,
	 *            29.57542;114.218,29.575&output=json&from=1&to=5&ak=你的密钥
	 * @return 返回转换坐标的结果，requestURL以指定的output返回格式json或xml格式返回
	 * @throws ConvertBaiduGPSException
	 */
	public String convert(String requestURL) throws ConvertBaiduGPSException {
		StringBuffer sb = null;// 用StringBuffer拼接转换结果，作为返回参数
		try {
			URL url = new URL(requestURL);
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (IOException e) {
			throw new ConvertBaiduGPSException("转换为百度GPS失败!!!错误消息："
					+ e.getMessage() + "原因：" + e.getCause() + "类："
					+ e.getClass().getName());
		}
		String convertResult = sb.toString();
		return convertResult;
	}
}
