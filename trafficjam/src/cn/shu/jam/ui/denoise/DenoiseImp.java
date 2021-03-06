package cn.shu.jam.ui.denoise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;
import cn.shu.jam.util.Denoising;

public class DenoiseImp {
	private GPSReader in;
	private GPSWriter out;
	private Path path;

	private GPSWriter outLog;

	public DenoiseImp(File in, File out) {
		try {
			this.in = new GPSReader(in);
			this.out = new GPSWriter(out);
			this.path = new Path();
			File logDIr = new File(in.getParent() + "Denoise" + File.separator
					+ "Log");
			logDIr.mkdirs();
			this.outLog = new GPSWriter(new File(in.getParent() + "Denoise"
					+ File.separator + "Log" + File.separator + in.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void denoise() {
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
		ArrayList<GPS> denoisedGps = Denoising.denoise(path);
		try {
			out.write(path);
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
		Path logPath = new Path();
		for (GPS gps2 : denoisedGps) {
			logPath.add(gps2);
			gps2 = null;
		}
		try {
			outLog.write(logPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outLog.close();
				outLog = null;
				denoisedGps = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
