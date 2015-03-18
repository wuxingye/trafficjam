package cn.shu.jam.ui.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

/**
 * �õ��찲�Ÿ���������
 * <p>
 * GPS��Ϣ����Χ���� <br>
 * (116.373317, 39.902298)<br>
 * (116.433683, 39.9277)
 * <p>
 * 2827.7377051304766�׸�<br>
 * 5153.197970692598�׿�<br>
 * 14571892.203729315ƽ����
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-24 ����12:11:09
 * @version 1.0
 */
public class TianAnMenGPSMain {

	private static final String inDir = "D:\\a����GPS����\\BeijingTaxiWithBaiduGPS\\";
	private static final String outDir = "D:\\a����GPS����\\BaiduGPS�찲��16����\\";
	private static final double lonmin = 116.373317;
	private static final double lonmax = 116.433683;
	private static final double latmin = 39.902298;
	private static final double latmax = 39.9277;

	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream("D:/GPSReplaceErrLog.txt"));// �����¼����־�ļ�
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		GPSReader in = null;
		GPSWriter out = null;
		Path path = null;
		for (int i = 1; i <= 10357; i++) {
			System.out.println(i);
			try {
				in = new GPSReader(new File(inDir + i + ".txt"));
				out = new GPSWriter(new File(outDir + i + ".txt"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			path = getpath(in);
			getTianAnMenGPS(path, out);
		}
	}

	private static Path getpath(GPSReader in) {
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

	private static void getTianAnMenGPS(Path path, GPSWriter out) {
		Path tamPath = new Path();
		GPS tempGps;
		double longitude;
		double latitude;
		for (int i = 0; i < path.size(); i++) {
			tempGps = path.getGps(i);
			longitude = tempGps.getLongitude();
			latitude = tempGps.getLatitude();
			if (longitude > lonmin && longitude < lonmax && latitude > latmin
					&& latitude < latmax) {
				tamPath.add(tempGps);
			}
		}
		try {
			out.write(tamPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
