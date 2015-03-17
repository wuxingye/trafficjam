package cn.shu.jam.baidu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaiduGPSToJs {
	/**
	 * 加载海量点
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void hailiangdian(File in, File out) throws IOException,
			JSONException {
		BufferedReader br = new BufferedReader(new FileReader(in));
		System.setOut(new PrintStream(out));
		JSONObject jo = new JSONObject(br.readLine());
		JSONArray ja = jo.getJSONArray("result");
		JSONObject joTemp = null;
		String x;
		String y;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ja.length(); i++) {
			joTemp = ja.getJSONObject(i);
			x = joTemp.getString("x");
			y = joTemp.getString("y");
			sb.append("[").append(x).append(",").append(y).append(",10],");
		}
		System.out.println(sb);
		System.setOut(System.out);
	}
}
