package cn.shu.jam.ui.daytime;

import java.io.File;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;
import cn.shu.jam.util.DayTimeCalculate;

public class DayTimeImp {
	private GPSReader in;
	private GPSWriter out;
	private Path path;

	public DayTimeImp(File in, File out) {
		try {
			this.in = new GPSReader(in);
			this.out = new GPSWriter(out);
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

		DayTimeCalculate dtc = new DayTimeCalculate(path);
		Path pathTimeCalc = dtc.calc();

		try {
			out.write(pathTimeCalc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				out = null;
				this.path = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
