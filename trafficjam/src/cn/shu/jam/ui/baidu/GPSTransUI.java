package cn.shu.jam.ui.baidu;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GPSTransUI extends JFrame implements Runnable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 6888550553954947460L;

	public static final int RUN = 1;
	public static final int STOP = 2;
	public static final int EXIT = 0;

	private int state = RUN;
	private File[] files;
	private String denoiseDir;
	private int fileCount;
	private long starttime;
	private long endtime;
	private Thread denoiseThread1;

	private JButton selectDir, startDenoise, stopDenoise, exitDenoise, openDir;
	private JLabel gpsDirLabel, gpsInfoLabel, denoiseDetailLabel, resultLabel;
	private JTextField gpsDirTf, gpsInfoTf;
	private JTextArea denoiseDetailTa;
	private JScrollPane scrollPane;
	private GridBagLayout gridbag = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	private Container contentPane;

	private JPanel denoisePane;

	public GPSTransUI(String title) {
		super(title);
		
		try {
			System.setErr(new PrintStream("D:/GPSTransErrLog.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		contentPane = getContentPane();
		contentPane.setLayout(gridbag);
		c.fill = GridBagConstraints.HORIZONTAL;

		gpsDirLabel = new JLabel("待转换GPS所在目录：");
		gpsDirTf = new JTextField(45);
		gpsDirTf.setEditable(false);
		selectDir = new JButton("选择目录");
		selectDir.addActionListener(new SelectDirListener());
		addComponent(gpsDirLabel, 0, 0, 1, 1);
		addComponent(gpsDirTf, 0, 1, 1, 1);
		c.fill = GridBagConstraints.NONE;
		addComponent(selectDir, 0, 2, 1, 1);
		c.fill = GridBagConstraints.HORIZONTAL;
		// System.out.println(selectDir.getParent());

		gpsInfoLabel = new JLabel("待转换GPS目录信息：");
		gpsInfoTf = new JTextField(50);
		gpsInfoTf.setEditable(false);
		denoisePane = new JPanel();
		denoisePane.setLayout(new FlowLayout());
		startDenoise = new JButton("开始转换");
		stopDenoise = new JButton("暂停转换");
		exitDenoise = new JButton("停止转换");
		stopDenoise.setEnabled(false);
		exitDenoise.setEnabled(false);
		denoisePane.add(startDenoise);
		denoisePane.add(stopDenoise);
		denoisePane.add(exitDenoise);
		startDenoise.addActionListener(new StartDenoiseListener());
		stopDenoise.addActionListener(new StopDenoiseListener());
		exitDenoise.addActionListener(new ExitDenoiseListener());
		addComponent(gpsInfoLabel, 1, 0, 1, 1);
		addComponent(gpsInfoTf, 1, 1, 2, 1);
		addComponent(denoisePane, 2, 0, 3, 1);

		denoiseDetailLabel = new JLabel("转换详情：日志文件在D:/GPSTransErrLog.txt");
		addComponent(denoiseDetailLabel, 3, 0, 1, 1);

		denoiseDetailTa = new JTextArea(10, 30);
		scrollPane = new JScrollPane(denoiseDetailTa,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		addComponent(scrollPane, 4, 0, 3, 1);

		resultLabel = new JLabel(" ");
		openDir = new JButton("打开目录");
		openDir.addActionListener(new OpenDirListener());
		addComponent(resultLabel, 5, 0, 3, 1);
		addComponent(openDir, 6, 0, 3, 1);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void addComponent(Component component, int row, int column,
			int width, int height) {
		c.gridx = column;
		c.gridy = row;
		c.gridwidth = width;
		c.gridheight = height;
		gridbag.setConstraints(component, c);
		contentPane.add(component);
	}

	private File[] getFileInfo(File dir) {
		File files[] = dir.listFiles();
		this.files = files;
		return files;
	}

	public void run() {
		synchronized (this) {
			File out = null;
			for (int i = 0; i < files.length; i++) {
				if (state == RUN && fileCount < files.length) {
					out = new File(denoiseDir + File.separator
							+ files[fileCount].getName());
					GPSTransImp gti = new GPSTransImp(files[fileCount], out);
					gti.exe();
					denoiseDetailTa.insert(files[fileCount].getAbsolutePath()
							+ "			转换完成！" + "\n", 0);
					resultLabel.setText("正在处理第" + (fileCount+1) + "个");
					files[fileCount] = null;
					fileCount++;
				} else if (state == STOP) {
					try {
						this.wait();
						// System.out.println("wait");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					break;
				}
			}

			files = null;
			endtime = new Date().getTime();
			resultLabel.setText("用时：" + (endtime - starttime) + "毫秒");
		}
	}

	synchronized private void notifyStart() {
		synchronized (this) {
			notifyAll();
		}
	}

	class StartDenoiseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (state != RUN) {
				state = RUN;
				notifyStart();
				startDenoise.setEnabled(false);
				stopDenoise.setEnabled(true);
				exitDenoise.setEnabled(true);
				return;
			}
			if (files == null) {
				JOptionPane.showMessageDialog(GPSTransUI.this, "请选择目录！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			startDenoise.setEnabled(false);
			stopDenoise.setEnabled(true);
			exitDenoise.setEnabled(true);
			fileCount = 0;
			starttime = new Date().getTime();
			denoiseDir = files[0].getParent() + "BaiduGPS";
			new File(denoiseDir).mkdirs();
			denoiseDetailTa.setText("");
			denoiseThread1 = new Thread(GPSTransUI.this);
			// System.out.println(denoiseThread1.isAlive());
			// System.out.println(denoiseThread1);
			denoiseThread1.start();
			// System.out.println(denoiseThread1.isAlive());
		}
	}

	class StopDenoiseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (files == null) {
				JOptionPane.showMessageDialog(GPSTransUI.this, "请选择目录！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			state = STOP;
			startDenoise.setEnabled(true);
			stopDenoise.setEnabled(false);
			exitDenoise.setEnabled(true);
		}
	}

	class ExitDenoiseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (files == null) {
				JOptionPane.showMessageDialog(GPSTransUI.this, "请选择目录！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			state = EXIT;
			notifyStart();
			startDenoise.setEnabled(false);
			stopDenoise.setEnabled(false);
			exitDenoise.setEnabled(true);
		}
	}

	class SelectDirListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			state = RUN;
			startDenoise.setEnabled(true);
			stopDenoise.setEnabled(false);
			exitDenoise.setEnabled(false);
			denoiseDetailTa.setText("");
			JFileChooser jc = new JFileChooser();
			jc.setFileSelectionMode(1);
			int state = jc.showOpenDialog(GPSTransUI.this);// ((Component)e.getSource()).getParent()
			if (state == JFileChooser.APPROVE_OPTION) {
				File dir = jc.getSelectedFile();
				gpsDirTf.setText(dir.getAbsolutePath());
				File[] files = getFileInfo(dir);
				gpsInfoTf.setText("共有" + String.valueOf(files.length)
						+ "个待转换文件，文件夹不可转换！");
				for (int i = 0; i < files.length; i++) {
					denoiseDetailTa.append(files[i].getAbsolutePath() + "\n");
				}
				denoiseDetailTa.paintImmediately(denoiseDetailTa.getBounds());
			}
		}
	}

	class OpenDirListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (denoiseDir == null) {
				JOptionPane.showMessageDialog(GPSTransUI.this,
						"未找到目录，请执行转换后打开目录！", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				Runtime.getRuntime()
						.exec("cmd /k start "
								+ denoiseDir.substring(0,
										denoiseDir.lastIndexOf(File.separator)));
				// System.out.println(denoiseDir.substring(0,denoiseDir.lastIndexOf(File.separator)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		new GPSTransUI("GPS坐标转换————转换为百度地图坐标");
		// Metal :javax.swing.plaf.metal.MetalLookAndFeel
		// Nimbus :javax.swing.plaf.nimbus.NimbusLookAndFeel
		// CDE/Motif :com.sun.java.swing.plaf.motif.MotifLookAndFeel
		// Windows :com.sun.java.swing.plaf.windows.WindowsLookAndFeel
		// Windows Classic
		// :com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}