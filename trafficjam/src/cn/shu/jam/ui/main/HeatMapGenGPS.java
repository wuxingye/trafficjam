package cn.shu.jam.ui.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

/**
 * 合并同一范围内的点，并记录该范围内的个数
 * <p>
 * 将GPS的carName作为记录GPS个数的字段
 * 
 * @author wuxingye
 * @version
 */
public class HeatMapGenGPS {
	private TreeMap<String, GPS> ts;
	private String inFile = "D:\\a分析GPS数据\\生成热力图0-5\\hebing.txt";
	private String outFile = "D:\\a分析GPS数据\\生成热力图0-5\\hebing50米.txt";

	private Path pathResult;

	public HeatMapGenGPS() {
		this.ts = new TreeMap<String, GPS>();
		pathResult = new Path();
	}

	public void genHeatMap() {
		Path path = getpath();
		GPS gps1;
		GPS gps2;
		String key;
		for (int i = 0; i < path.size(); i++) {
			System.out.println(i);
			gps1 = path.getGps(i);
			gps1.setcarName(1);
			key = String.valueOf(gps1.getLongitude() * 2).substring(0, 7)
					+ String.valueOf(gps1.getLatitude() * 2).substring(0, 6);
			Iterator<Map.Entry<String, GPS>> it = ts.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, GPS> entry = it.next();
				if (key.equals(entry.getKey())) {
					gps2 = entry.getValue();
					if (gps2.getGaussValue() > gps1.getGaussValue()) {
						gps1.setLongitude(gps2.getLongitude());
						gps1.setLatitude(gps2.getLatitude());
					}
					gps1.setcarName(gps2.getcarName() + 1);
					break;
				}
			}
			ts.put(key, gps1);
		}
		Iterator<Map.Entry<String, GPS>> it = ts.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, GPS> entry = it.next();
			pathResult.add(entry.getValue());
		}
		GPSWriter out = null;
		try {
			out = new GPSWriter(outFile);
			out.write(pathResult);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Path getpath() {
		GPSReader in = null;
		try {
			in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Path path = new Path();
		GPS gps = null;
		try {
			while ((gps = in.read()) != null) {
				path.add(gps);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GPSTransformException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static void main(String[] args) {
		new HeatMapGenGPS().genHeatMap();
	}
}
