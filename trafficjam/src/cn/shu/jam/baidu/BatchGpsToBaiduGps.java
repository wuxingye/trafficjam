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
 * ��GPS����ת��Ϊ�ٶ�GPS���꣬����json��ʽ
 * <p>
 * <b>����ֵ��ʱ��֧��xml��ʽ</b>
 * <p>
 * ���췽����Ҫָ��һ��{@link GpsToBaiduGps}�࣬���Դ����൱��һ����������ʹ������ת��ͻ��100��
 * <p>
 * <b> ת���������� </b>ͻ��100������
 * <p>
 * <a href="http://developer.baidu.com/map/changeposition.htm"> �ٶ�����ת��API����</a>
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-7 ����11:59:17
 * @version 1.0
 */
public class BatchGpsToBaiduGps implements BaiduGpsConstants {

	private ArrayList<String> coords;// Դ����
	private ArrayList<String> requestURLs;
	private GpsToBaiduGps gpsToBaiduGps;
	private int carName;

	/**
	 * <p>
	 * ���췽����Ҫָ��һ��{@link GpsToBaiduGps}�࣬���Դ����൱��һ����������ʹ������ת��ͻ��100��
	 * 
	 * @param gpsToBaiduGps
	 */
	public BatchGpsToBaiduGps(GpsToBaiduGps gpsToBaiduGps) {
		this.gpsToBaiduGps = gpsToBaiduGps;
		coords = new ArrayList<String>();
		requestURLs = new ArrayList<String>();
	}

	/**
	 * ��ȡ�����URL�����ڻ��ж�����������ж������URL�������Ի�ȡ��
	 * 
	 * @return ���ض�������URL
	 */
	public ArrayList<String> getRequestURLs() {
		return requestURLs;
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
	 * @return ���ش�ת��������
	 */
	public ArrayList<String> getCoords() {
		return coords;
	}

	/**
	 * ��ʼ����ٶ�GPSת���ӿ������URLs
	 * <p>
	 * ��ʼ��������URL������{@link BatchGpsToBaiduGps#getRequestURLs getRequestURLs}�õ�
	 */
	public void initURL() {
		int count = coords.size() / MAX;// ȡ��
		int step = coords.size() - count * MAX;// ȡģ
		if (step == 0) { // ģΪ0�����һ�ξ�Ӧ��׼��MAX����ת��count��
			step = MAX;
		} else {// ģ��Ϊ0�����һ��ת��step������ת��count+1��
			count++;
		}
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < step; j++) {// ��step����ת���������
				gpsToBaiduGps.addCoords(coords.get(i + j));
			}
			try {
				gpsToBaiduGps.initURL();// ��ʼ������ת����URL
			} catch (ConvertBaiduGPSException e) {
				e.printStackTrace();
			}
			String requestURL = gpsToBaiduGps.getRequestURL();
			requestURLs.add(requestURL);// ������URL��¼
			gpsToBaiduGps.clearCoords();// �������Ĵ�ת�����꣬�Ա��´�ת������ͻ
			step = MAX;
		}
	}

	/**
	 * ת�����꣬�����ָ����json��ʽ����
	 * <p>
	 * ��ʱ��֧��xml
	 * 
	 * @return ����ת������Ľ������ָ����json��ʽ����
	 */
	public String convert() {
		try {
			if (requestURLs.size() == 0) {// �����ַΪ�գ�˵��Ϊ��ʼ��URL�����׳�ת���쳣
				throw new ConvertBaiduGPSException("û�г�ʼ��ת��URL�����÷���initURL��");
			}
			if (coords.size() == 0) {// ��ת��������Ϊ�գ�ת��û�����壬�׳�ת���쳣
				throw new ConvertBaiduGPSException("û����Ӵ�ת�������꣡���÷���addCoords��");
			}
		} catch (ConvertBaiduGPSException e1) { // �ڷ����ڲ�ץ�쳣
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();// ��StringBufferƴ��ת���������Ϊ���ز���
		String convert;
		int i = 0;
		for (String requestURL : requestURLs) {// ���������URL����
			try {
				convert = gpsToBaiduGps.convert(requestURL);
				JSONObject jo = new JSONObject(convert);
				if (!jo.getString("status").equals("0")) {
					throw new ConvertBaiduGPSException("�ٶ�ת�����ڲ��ɹ�");
				}
				JSONArray ja = jo.getJSONArray("result");
				System.out.println(i++);// TODO
				String jas = ja.toString();
				sb.append(jas.substring(1, jas.length() - 1)).append(",");
			} catch (JSONException e) {
				System.err.println("����:" + carName);
				e.printStackTrace();
			} catch (ConvertBaiduGPSException e) {
				System.err.println("����:" + carName);
				e.printStackTrace();
			}
		}
		String arrayResult = sb.toString();
		String convertResult = "{\"status\":0,\"result\":[" // ƴ�ӽ����ƴ�ӳ�json����
				+ arrayResult.substring(0, arrayResult.length() - 1) + "]}";
		return convertResult;// ���ؽ��
	}

	/**
	 * ��path�е�����ת��Ϊ�ٶȵ�
	 * <p>
	 * ���޸���
	 * 
	 * @param path
	 * @return ����json��ʽ����
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
