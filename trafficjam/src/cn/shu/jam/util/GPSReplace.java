package cn.shu.jam.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

/**
 * 输入文件是含有GPS坐标的标准格式文件
 * <p>
 * 替换文件是含有百度坐标的JSON文件
 * <p>
 * 输出文件是计算完之后输出的文件
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-24 上午9:30:47
 * @version 1.0
 */
public class GPSReplace {
	private File inFile, replaceFile, outFile;
	private BufferedReader br;
	private GPSReader in;
	private GPSWriter out;
	private Path path;

	public GPSReplace(File inFile, File replaceFile, File outFile) {
		this.inFile = inFile;
		this.replaceFile = replaceFile;
		this.outFile = outFile;

		try {
			this.br = new BufferedReader(new FileReader(replaceFile));
			this.in = new GPSReader(inFile);
			this.out = new GPSWriter(outFile);
		} catch (IOException e) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e.printStackTrace();
		}
		this.path = new Path();
	}

	public void exe() {
		GPS gps = null;
		try {
			while ((gps = in.read()) != null) {
				path.add(gps);
			}
		} catch (IOException e) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e.printStackTrace();
		} catch (GPSTransformException e) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e.printStackTrace();
		}
		if (path.size() == 0) {
			return;
		}
		String string = null;
		try {
			string = br.readLine();
		} catch (IOException e1) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e1.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e2) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e2.printStackTrace();
		}
		JSONObject jo = null;
		JSONArray ja = null;
		try {
			jo = new JSONObject(string);
			ja = jo.getJSONArray("result");
		} catch (JSONException e1) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e1.printStackTrace();
		}
		// TODO
		JSONObject joTemp = null;
		GPS gpsTemp = null;
		Path replacePath = new Path();
		double longitude = -1;
		double latitude = -1;
		for (int i = 0; i < path.size(); i++) {
			try {
				joTemp = ja.getJSONObject(i);
				longitude = joTemp.getDouble("x");
				latitude = joTemp.getDouble("y");
			} catch (JSONException e) {
				System.err.println(inFile.getAbsolutePath());
				System.err.println(replaceFile.getAbsolutePath());
				System.err.println(outFile.getAbsolutePath());
				e.printStackTrace();
			}
			gpsTemp = path.getGps(i);
			gpsTemp.setLongitude(longitude);
			gpsTemp.setLatitude(latitude);

			replacePath.add(gpsTemp);
		}

		try {
			out.write(replacePath);
		} catch (IOException e) {
			System.err.println(inFile.getAbsolutePath());
			System.err.println(replaceFile.getAbsolutePath());
			System.err.println(outFile.getAbsolutePath());
			e.printStackTrace();
		} finally {
			try {
				out.close();
				out = null;
				this.path = null;
			} catch (IOException e) {
				System.err.println(inFile.getAbsolutePath());
				System.err.println(replaceFile.getAbsolutePath());
				System.err.println(outFile.getAbsolutePath());
				e.printStackTrace();
			}
		}
	}
}
