package cn.shu.jam.baidu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.lang.ConvertBaiduGPSException;

/**
 * <p>
 * 将GPS坐标转换为百度GPS坐标，返回json格式
 * <p>
 * <b>返回值暂时不支持xml格式</b>
 * <p>
 * 构造方法需要指定一个{@link GpsToBaiduGps}类，所以此类相当于一个适配器，使得坐标转换突破100个
 * <p>
 * <b> 转换个数不限 </b>突破100个限制
 * <p>
 * <a href="http://developer.baidu.com/map/changeposition.htm"> 百度坐标转换API链接</a>
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-7 上午11:59:17
 * @version 1.0
 */
public class BatchGpsToBaiduGps implements BaiduGpsConstants {

	private ArrayList<String> coords;// 源坐标
	private ArrayList<String> requestURLs;
	private GpsToBaiduGps gpsToBaiduGps;
	private int carName;

	/**
	 * <p>
	 * 构造方法需要指定一个{@link GpsToBaiduGps}类，所以此类相当于一个适配器，使得坐标转换突破100个
	 * 
	 * @param gpsToBaiduGps
	 */
	public BatchGpsToBaiduGps(GpsToBaiduGps gpsToBaiduGps) {
		this.gpsToBaiduGps = gpsToBaiduGps;
		coords = new ArrayList<String>();
		requestURLs = new ArrayList<String>();
	}

	/**
	 * 获取请求的URL，由于会有多次请求，所以有多个请求URL，都可以获取到
	 * 
	 * @return 返回多个请求的URL
	 */
	public ArrayList<String> getRequestURLs() {
		return requestURLs;
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
	 * @return 返回待转换的坐标
	 */
	public ArrayList<String> getCoords() {
		return coords;
	}

	/**
	 * 初始化向百度GPS转换接口请求的URLs
	 * <p>
	 * 初始化的请求URL可以用{@link BatchGpsToBaiduGps#getRequestURLs getRequestURLs}得到
	 */
	public void initURL() {
		int count = coords.size() / MAX;// 取商
		int step = coords.size() - count * MAX;// 取模
		if (step == 0) { // 模为0，则第一次就应该准换MAX个，转换count次
			step = MAX;
		} else {// 模不为0，则第一次转换step摸个，转换count+1次
			count++;
		}
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < step; j++) {// 将step个待转换坐标添加
				gpsToBaiduGps.addCoords(coords.get(i + j));
			}
			try {
				gpsToBaiduGps.initURL();// 初始化请求转换的URL
			} catch (ConvertBaiduGPSException e) {
				e.printStackTrace();
			}
			String requestURL = gpsToBaiduGps.getRequestURL();
			requestURLs.add(requestURL);// 将请求URL记录
			gpsToBaiduGps.clearCoords();// 清除请求的待转换坐标，以便下次转换不冲突
			step = MAX;
		}
	}

	/**
	 * 转换坐标，结果以指定的json格式返回
	 * <p>
	 * 暂时不支持xml
	 * 
	 * @return 返回转换坐标的结果，以指定的json格式返回
	 */
	public String convert() {
		try {
			if (requestURLs.size() == 0) {// 请求地址为空，说明为初始化URL，则抛出转换异常
				throw new ConvertBaiduGPSException("没有初始化转换URL！调用方法initURL！");
			}
			if (coords.size() == 0) {// 待转换的坐标为空，转换没有意义，抛出转换异常
				throw new ConvertBaiduGPSException("没有添加待转换的坐标！调用方法addCoords！");
			}
		} catch (ConvertBaiduGPSException e1) { // 在方法内部抓异常
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();// 用StringBuffer拼接转换结果，作为返回参数
		String convert;
		int i = 0;
		for (String requestURL : requestURLs) {// 将待请求的URL请求
			try {
				convert = gpsToBaiduGps.convert(requestURL);
				JSONObject jo = new JSONObject(convert);
				if (!jo.getString("status").equals("0")) {
					throw new ConvertBaiduGPSException("百度转换存在不成功");
				}
				JSONArray ja = jo.getJSONArray("result");
				System.out.println(i++);// TODO
				String jas = ja.toString();
				sb.append(jas.substring(1, jas.length() - 1)).append(",");
			} catch (JSONException e) {
				System.err.println("车号:" + carName);
				e.printStackTrace();
			} catch (ConvertBaiduGPSException e) {
				System.err.println("车号:" + carName);
				e.printStackTrace();
			}
		}
		String arrayResult = sb.toString();
		String convertResult = "{\"status\":0,\"result\":[" // 拼接结果，拼接成json数据
				+ arrayResult.substring(0, arrayResult.length() - 1) + "]}";
		return convertResult;// 返回结果
	}

	/**
	 * 将path中的坐标转换为百度的
	 * <p>
	 * 不限个数
	 * 
	 * @param path
	 * @return 返回json格式数据
	 */
	public String convert(Path path) {
		if (carName == 0) {
			carName = path.getGps(0).getcarName();
		}
		addCoords(path);
		initURL();
		return convert();
	}
}
