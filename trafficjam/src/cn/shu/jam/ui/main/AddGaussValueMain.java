package cn.shu.jam.ui.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cn.shu.jam.util.AddGaussValue;

public class AddGaussValueMain {
	public static void main(String[] args) {
		String indir = "D:\\a分析GPS数据\\aaa\\";
		String outdir = "D:\\a分析GPS数据\\aaa\\";
		double sigma = 100;

		try {
			System.setErr(new PrintStream(new File("D:/addGauss.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String inFile = indir + 2 + ".txt";
		String outFile = outdir + 2 + "gaosi.txt";
		new AddGaussValue(inFile, outFile, sigma).exe();

	}
}
