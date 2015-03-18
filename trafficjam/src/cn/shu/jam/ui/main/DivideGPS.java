package cn.shu.jam.ui.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

public class DivideGPS {

	private double flagx[];
	private double flagy[];
	private GPSWriter out[];
	private Path path[];

	// private double lonMin = 116.373317;
	// private double lonMax = 116.433683;
	// private double latMin = 39.902298;
	// private double latMax = 39.9277;
	private double lonMin = 116.3840;
	private double lonMax = 116.3849;
	private double latMin = 39.9180;
	private double latMax = 39.9189;

	private double count;
	private double x;
	private double y;

	private String inFile = "D:\\a分析GPS数据\\aaa\\01440.txt";
	private String outDir = "D:\\a分析GPS数据\\aaa\\";
	private GPSReader in;

	public DivideGPS() {
		this.count = ((int) ((lonMax - lonMin) * 10000))
				* ((int) ((latMax - latMin) * 10000));
		path = new Path[(int) count + 1];
		out = new GPSWriter[(int) count + 1];
		this.x = (int) ((lonMax - lonMin) * 10000);
		System.out.println(x);
		flagx = new double[(int) x];
		this.y = (int) ((latMax - latMin) * 10000);
		flagy = new double[(int) y];
		for (int i = 0; i < x; i++) {
			flagx[i] = lonMin + ((double) i / 10000);
			System.out.println(flagx[i]);
		}
		for (int j = 0; j < y - 1; j++) {
			flagy[j] = latMin + ((double) j / 1000);
			System.out.println(flagy[j]);
		}

		for (int i = 0; i < count; i++) {
			try {
				out[i] = new GPSWriter(outDir + (i + 1) + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < count; i++) {
			this.path[i] = new Path();
		}
		try {
			this.in = new GPSReader(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Path getPath() {
		GPS gps = null;
		Path tempPath = new Path();
		try {
			while ((gps = in.read()) != null) {
				tempPath.add(gps);
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
		return tempPath;
	}

	public void exe() {
		Path path = getPath();
		GPS gps;
		double longitude;
		double latitude;
		boolean flag = false;
		for (int i = 0; i < path.size(); i++) {
			gps = path.getGps(i);
			longitude = gps.getLongitude();
			latitude = gps.getLatitude();
			flag = false;
			for (int j = flagx.length - 1; j >= 0; j--) {
				for (int k = flagy.length - 1; k >= 0; k--) {
					if (longitude > flagx[j] && latitude > flagy[k]) {
						this.path[j * k + k + 1].add(gps);
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
		}
		for (int i = 0; i < this.path.length; i++) {
			System.out.println(i);
			try {
				out[i].write(this.path[i]);
				out[i].close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new DivideGPS().exe();
	}
}
