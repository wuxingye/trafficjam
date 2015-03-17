package cn.shu.jam.ui.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.shu.jam.gps.GPS;
import cn.shu.jam.gps.Path;
import cn.shu.jam.io.GPSReader;
import cn.shu.jam.io.GPSWriter;
import cn.shu.jam.lang.GPSTransformException;

/**
 * ��һ��Ŀ¼�µ�GPS�ļ��ϲ���һ���ļ�
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-24 ����12:31:14
 * @version 1.0
 */
public class GPSCombine {
	private static final String inDir = "D:\\a����GPS����\\�����ٶȴ���0С��5+��˹\\";
	private static final String outFile = "D:\\a����GPS����\\�����ٶȴ���0С��5+��˹\\hebing.txt";

	public static void main(String[] args) {
		GPSReader in = null;
		Path path = new Path();
		GPS gps = null;
		for (int i = 1; i <= 1439; i++) {
			System.out.println(i);
			try {
				in = new GPSReader(new File(inDir + i + ".txt"));
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
		}
		try {
			GPSWriter out = new GPSWriter(outFile);
			out.write(path);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
