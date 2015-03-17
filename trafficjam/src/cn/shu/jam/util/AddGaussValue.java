package cn.shu.jam.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

public class AddGaussValue {
	private String inFile;// "D:\\a分析GPS数据\\带有速度大于0.5小于1\\11.txt";
	private String outFile;// "D:\\a分析GPS数据\\带有高斯值\\11.txt";
	private double sigma;// 20;

	public AddGaussValue(String inFile, String outFile, double sigma) {
		this.inFile = inFile;
		this.outFile = outFile;
		this.sigma = sigma;
	}

	public void exe() {
		GPSReader in = null;
		GPS gps = null;
		Path path = new Path();
		try {
			in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

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

		GPS gps1 = null;
		GPS gps2 = null;
		Path gaussPath = new Path();
		double distence = -1;
		double gaussValue = 0;
		for (int i = 0; i < path.size(); i++) {
			gps1 = path.getGps(i);
			for (int j = 0; j < path.size(); j++) {
				System.out.println(i + ":" + j);
				gps2 = path.getGps(j);
				distence = Distance.getByGPS(gps1, gps2);
				if (distence < sigma && distence > 0) {
					gaussValue += GPSUtil.getGaussValue(distence, sigma);
				}
			}
			gps1.setGaussValue(gaussValue);
			gaussValue = 0;
			gaussPath.add(gps1);
		}

		GPSWriter out = null;
		try {
			out = new GPSWriter(outFile);
			out.write(gaussPath);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
