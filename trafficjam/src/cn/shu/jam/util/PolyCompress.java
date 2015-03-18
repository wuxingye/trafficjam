package cn.shu.jam.util;

import java.awt.*;
import java.util.Random;
import javax.swing.JFrame;

public class PolyCompress extends JFrame {
	
	private static final long serialVersionUID = -4835525467046795623L;
	
	private static final int NUMBER = 50;// ԭʼ���߽ڵ���
	private static final int TOLERANCE = 10;// ѹ��ʱ�ľ��뷧ֵ
	private int[] source_x = new int[NUMBER];// ԭʼ���߽ڵ�ĺ�����
	private int[] source_y = new int[NUMBER];// ԭʼ���߽ڵ��������
	private int[] result_x;// �洢ѹ��������߽ڵ������
	private int[] result_y;// �洢ѹ��������߽ڵ�������
	private int[] index = new int[NUMBER];// ��¼�����Ľڵ���ԭʼ���߽ڵ����������е�λ��
	private int count = 0;// �����Ľڵ����

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

		System.out.println("ԭʼ���ݵ㣺");
		for (int i = 0; i < NUMBER; i++) {
			System.out.println("source_xy[" + i + "]:" + source_x[i] + ","
					+ source_y[i]);
		}
		// ��ԭʼ���ߵ���β�ڵ㱣��������
		index[count++] = 0;
		index[count++] = NUMBER - 1;

		// ���õݹ麯��
		compress(0, NUMBER - 1);

		sort();

		result_x = new int[count];
		result_y = new int[count];

		for (int i = 0; i < count; i++) {
			result_x[i] = source_x[index[i]];
			result_y[i] = source_y[index[i]];
		}

		System.out.println("ԭ�����нڵ���Ϊ��" + NUMBER);
		System.out.println("�ڵ�ȡ�����ֵΪ��" + TOLERANCE);
		System.out.println("ѹ����������нڵ���Ϊ��" + count);
		System.out.println("�����ڵ���ԭ���߽ڵ������е�λ�����£�");
		System.out.println("����ѹ����Ϊ��" + 100 * count / NUMBER + "��");

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
		// ����ԭʼ����
		g.setColor(Color.gray);
		g.drawLine(0, 100, width, 100);
		g.setColor(Color.red);
		g.drawPolyline(source_x, source_y, NUMBER);

		// ���ѹ����ڵ�����Ϊ0�������ѹ���������
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
