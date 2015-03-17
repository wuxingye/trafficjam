package cn.shu.jam.ui.baidu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.shu.jam.baidu.BatchGpsToBaiduGps;
import cn.shu.jam.baidu.GpsToBaiduGps;
import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.lang.GPSTransformException;

public class GPSTransImp {
	private GPSReader in;
	private Path path;
	private BufferedWriter bw;

	public GPSTransImp(File in, File out) {
		try {
			this.bw = new BufferedWriter(new FileWriter(out));
			this.in = new GPSReader(in);
			this.path = new Path();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exe() {
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

		GpsToBaiduGps gpsToBaiduGps = new GpsToBaiduGps();
		BatchGpsToBaiduGps bgtg = new BatchGpsToBaiduGps(gpsToBaiduGps);
		try {
			bw.write(bgtg.convert(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
