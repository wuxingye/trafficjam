package cn.shu.jam.ui.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cn.shu.jam.util.GPSReplace;

public class GPSReplaceMain {

	private static final String inDir = "D:\\GPS坐标处理\\BeijingTaxiDenoisedSpeedDayTime\\";
	private static final String replaceDir = "D:\\GPS坐标处理\\BeijingTaxiDenoisedSpeedDayTimeBaiduGPS\\";
	private static final String outDir = "D:\\GPS坐标处理\\BeijingTaxiWithBaiduGPS\\";

	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream("D:/GPSReplaceErrLog.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int i = 1; i <= 10357; i++) {
			System.out.println(i);
			File inFile = new File(inDir + i + ".txt");
			File replaceFile = new File(replaceDir + i + ".txt");
			File outFile = new File(outDir + i + ".txt");
			new GPSReplace(inFile, replaceFile, outFile).exe();
		}
	}
}
