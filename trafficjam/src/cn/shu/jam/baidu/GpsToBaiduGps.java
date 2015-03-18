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
 * ��GPS����ת��Ϊ�ٶ�GPS���꣬����json��xml����Ĭ��json
 * <p>
 * ����ָ��ת��Դfrom�������ͺ�Ŀ��to��������
 * <p>
 * ���ת��100��
 * <p>
 * <a href="http://developer.baidu.com/map/changeposition.htm"> �ٶ�����ת��API����</a>
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-6 ����4:29:31
 * @version 1.0
 */
public class GpsToBaiduGps implements BaiduGpsConstants{

	private ArrayList<String> coords;// Դ����
	private int from;// Դ�������� 1��2��3��4��5��6��7��8����
	private int to;// Ŀ���������������ֿɹ�ѡ��5,6
	private String output;
	private String requestURL;
	private int carName;// ת��������һ����

	/**
	 * ����һ����jsonΪ���صĽ��豸GPSת��Ϊ�ٶ�GPS��ת����
	 */
	public GpsToBaiduGps() {
		this(DEVICE, BAIDU, JSON);
		coords = new ArrayList<String>();
	}

	/**
	 * ����һ��DIY��ת������ָ�����ͺͽ����ʽ
	 * 
	 * @param from
	 *            Դ��������
	 * @param to
	 *            Ŀ����������
	 * @param output
	 *            ���ؽ����ʽ
	 */
	public GpsToBaiduGps(int from, int to, String output) {
		this.from = from;
		this.to = to;
		this.output = output;
		coords = new ArrayList<String>();
	}

	/**
	 * @return ���������URL�����ȵ���{@link GpsToBaiduGps#initURL initURL}����
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param coord
	 *            ��Ӵ�ת�������꣬Ҫת���� "116.567,39.678"��ʽ���ַ�������
	 */
	public void addCoords(String coord) {
		coords.add(coord);
	}

	/**
	 * @param gps
	 *            ��Ӵ�ת�������꣬ Ҫת����GPS����
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
	 *            ��Ӵ�ת�������꣬Ҫת����Path����
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
	 *            ��Ӵ�ת���� "116.567,39.678"��ʽ���ַ�������(����ArrayList)
	 */
	public void addCoords(ArrayList<String> coords) {
		coords.addAll(coords);
	}

	/**
	 * �����ת��������
	 */
	public void clearCoords() {
		coords.clear();
	}

	/**
	 * 
	 * @return ���ش�ת��������
	 */
	public ArrayList<String> getCoords() {
		return coords;
	}

	/**
	 * ��ʼ����ٶ�GPSת���ӿ������URL
	 * <p>
	 * ��ʼ��������URL������{@link GpsToBaiduGps#getRequestURL getRequestURL}�õ�
	 * 
	 * @throws ConvertBaiduGPSException
	 */

	public int getCarName() {
		return carName;
	}

	public void initURL() throws ConvertBaiduGPSException {
		if (coords.size() > MAX) { // ����ֻ��ת��100���������������������׳�ת���쳣
			throw new ConvertBaiduGPSException("һ�����ֻ��ת��100������ǰ"
					+ coords.size() + "����");
		}
		StringBuffer url = new StringBuffer();// ʹ��StringBufferƴ������URL
		url.append(DN).append("/").append(SN).append("/").append(VERSION)
				.append("/?coords="); // ƴ�ӷ����ַ
										// http://api.map.baidu.com/geoconv/v1/?coords=
		for (String coord : coords) {
			url.append(coord).append(";"); // ƴ��ת�����꣬��ʽ��114.2189,29.57542;114.2189,29.57542
		}
		url.deleteCharAt(url.length() - 1);// ɾ�����һ����;��
		url.append("&output=").append(output).append("&from=").append(from)
				.append("&to=").append(to).append("&ak=").append(AK); // ƴ������������Ϣ&output=json&from=1&to=1&ak=�����Կ
		requestURL = url.toString(); // ��������URL
	}

	/**
	 * ת�����꣬�����ָ����json��xml��ʽ����
	 * 
	 * @return ����ת������Ľ������ָ����json��xml��ʽ����
	 * @throws ConvertBaiduGPSException
	 */
	public String convert() throws ConvertBaiduGPSException {
		if (requestURL == null) { // �����ַΪ�գ�˵��Ϊ��ʼ��URL�����׳�ת���쳣
			throw new ConvertBaiduGPSException("û�г�ʼ��ת��URL�����÷���initURL��");
		}
		if (coords.size() == 0) {// ��ת��������Ϊ�գ�ת��û�����壬�׳�ת���쳣
			throw new ConvertBaiduGPSException("û����Ӵ�ת�������꣡���÷���addCoords��");
		}
		StringBuffer sb = null; // ��StringBufferƴ��ת���������Ϊ���ز���

		try {
			URL url = new URL(requestURL); // ��requestURL�л�ȡ��������
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (IOException e) {// ��requestURL��ȡ�����п��ܻ���쳣
			throw new ConvertBaiduGPSException("ת��Ϊ�ٶ�GPSʧ��!!!������Ϣ��"
					+ e.getMessage() + "ԭ��" + e.getCause() + "�ࣺ"
					+ e.getClass().getName());
		}

		String convertResult = sb.toString();
		return convertResult;// ����ת�����
	}

	/**
	 * �����һ������URL <br>
	 * ��ʽ��
	 * <p>
	 * http://api.map.baidu.com/geoconv/v1/?coords=114.2189,
	 * 29.57542;114.218,29.575&output=json&from=1&to=5&ak=�����Կ
	 * <p>
	 * ����ת�����
	 * 
	 * @param requestURL
	 *            ��ʽ ��
	 *            <p>
	 *            http://api.map.baidu.com/geoconv/v1/?coords=114.2189,
	 *            29.57542;114.218,29.575&output=json&from=1&to=5&ak=�����Կ
	 * @return ����ת������Ľ����requestURL��ָ����output���ظ�ʽjson��xml��ʽ����
	 * @throws ConvertBaiduGPSException
	 */
	public String convert(String requestURL) throws ConvertBaiduGPSException {
		StringBuffer sb = null;// ��StringBufferƴ��ת���������Ϊ���ز���
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
			throw new ConvertBaiduGPSException("ת��Ϊ�ٶ�GPSʧ��!!!������Ϣ��"
					+ e.getMessage() + "ԭ��" + e.getCause() + "�ࣺ"
					+ e.getClass().getName());
		}
		String convertResult = sb.toString();
		return convertResult;
	}
}
