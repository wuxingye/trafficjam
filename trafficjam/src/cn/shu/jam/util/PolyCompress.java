package cn.shu.jam.util;

import java.awt.*;
import java.util.Random;
import javax.swing.JFrame;

public class PolyCompress extends JFrame {
	
	private static final long serialVersionUID = -4835525467046795623L;
	
	private static final int NUMBER = 50;// 原始曲线节点数
	private static final int TOLERANCE = 10;// 压缩时的距离阀值
	private int[] source_x = new int[NUMBER];// 原始曲线节点的横坐标
	private int[] source_y = new int[NUMBER];// 原始曲线节点的纵坐标
	private int[] result_x;// 存储压缩后的曲线节点横坐标
	private int[] result_y;// 存储压缩后的曲线节点纵坐标
	private int[] index = new int[NUMBER];// 记录保留的节点在原始曲线节点坐标数组中的位置
	private int count = 0;// 保留的节点个数

	private int width, height;

	public PolyCompress() {
		setSize(400, 300);
		setBackground(Color.white);
		Dimension srcSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = getWidth();
		height = getHeight();
		setLocation((srcSize.width - width) / 2, (srcSize.height - height) / 2);
		setVisible(true);

		Random random = new Random();
		source_x[0] = 50;
		source_y[0] = 100;
		for (int i = 1; i < NUMBER; i++) {
			source_x[i] = source_x[i - 1] + random.nextInt(10);
			source_y[i] = 75 + random.nextInt(50);
		}

		System.out.println("原始数据点：");
		for (int i = 0; i < NUMBER; i++) {
			System.out.println("source_xy[" + i + "]:" + source_x[i] + ","
					+ source_y[i]);
		}
		// 将原始曲线的首尾节点保留下来。
		index[count++] = 0;
		index[count++] = NUMBER - 1;

		// 调用递归函数
		compress(0, NUMBER - 1);

		sort();

		result_x = new int[count];
		result_y = new int[count];

		for (int i = 0; i < count; i++) {
			result_x[i] = source_x[index[i]];
			result_y[i] = source_y[index[i]];
		}

		System.out.println("原曲线中节点数为：" + NUMBER);
		System.out.println("节点取舍的阈值为：" + TOLERANCE);
		System.out.println("压缩后的曲线中节点数为：" + count);
		System.out.println("保留节点在原曲线节点数据中的位置如下：");
		System.out.println("本次压缩比为：" + 100 * count / NUMBER + "％");

	}

	public double distance(int start, int end, int current) {

		double a = (double) (source_y[end] - source_y[start]);
		double b = (double) (source_x[end] - source_x[start]);
		double c = (double) (source_y[end] - source_y[start])
				- (double) (source_x[end] - source_x[start]);

		double dist = Math.abs(a * source_x[current] + b * source_y[current]
				+ c)
				/ Math.sqrt(a * a + b * b);
		return dist;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 绘制原始曲线
		g.setColor(Color.gray);
		g.drawLine(0, 100, width, 100);
		g.setColor(Color.red);
		g.drawPolyline(source_x, source_y, NUMBER);

		// 如果压缩后节点数不为0，则绘制压缩后的曲线
		if (count != 0) {
			g.setColor(Color.gray);
			g.drawLine(0, 200, width, 200);
			g.setColor(Color.green);
			g.drawPolyline(result_x, result_y, count);
		}
	}

	public void sort() {
		for (int i = 0; i < count; i++) {
			for (int j = i + 1; j < count; j++) {
				if (index[j] < index[i]) {
					int temp = index[j];
					index[j] = index[i];
					index[i] = temp;
				}
			}
		}
	}

	public void compress(int i, int j) {
		double temp_dist;
		double max = 0;
		int temp_p = 0;

		for (int k = i + 1; k < j; k++) {
			temp_dist = distance(i, j, k);
			if (max < temp_dist) {
				max = temp_dist;
				temp_p = k;
			}
		}

		if (max > TOLERANCE) {
			index[count++] = temp_p;
			compress(i, temp_p);
			compress(temp_p, j);
		}
	}

	public static void main(String[] args) {
		new PolyCompress();
	}
}
