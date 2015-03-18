package cn.shu.jam.gps;

import java.util.ArrayList;

/**
 * 将有关系的GPS对象组合为路径
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-5 下午3:27:48
 * @version 1.0
 */
public class Path {
	private String carName; // 路径车名（车号）
	private int pathId;// 路径的ID，路径ID不可以为0
	private ArrayList<GPS> gpsList;// 路径中的GPS链表（数据结构考虑Set）

	/**
	 * 构造函数
	 */
	public Path() {
		gpsList = new ArrayList<GPS>();
	}

	/**
	 * 根据路径的ID构造路径
	 * 
	 * @param pathId
	 *            指定路径的ID
	 */
	public Path(int pathId) {
		this.pathId = pathId;
		gpsList = new ArrayList<GPS>();
	}

	/**
	 * 根据路径车名（车号），路径的ID构造路径
	 * 
	 * @param carName
	 *            路径车名（车号）
	 * @param pathId
	 *            路径的ID，路径ID不可以为0
	 */
	public Path(String carName, int pathId) {
		this(pathId);
		this.carName = carName;
	}

	/**
	 * 取得车名
	 * 
	 * @return 车名
	 */
	public String getCarName() {
		return carName;
	}

	/**
	 * 设置车名
	 * 
	 * @param taxiName
	 *            车名
	 */
	public void setCarName(String taxiName) {
		this.carName = taxiName;
	}

	/**
	 * 获取路径ID
	 * 
	 * @return 路径ID
	 */
	public int getPathId() {
		return pathId;
	}

	/**
	 * 获取路径中包含的GPS
	 * 
	 * @return 路径中包含的GPS
	 */
	public ArrayList<GPS> getGpsList() {
		return gpsList;
	}

	/**
	 * 根据路径Path中的位置获取GPS
	 * 
	 * @param index
	 *            路径Path位置
	 * @return 指定位置处的GPS
	 */
	public GPS getGps(int index) {
		return gpsList.get(index);
	}

	/**
	 * 向路径中添加GPS坐标
	 * 
	 * @param gps
	 *            GPS对象
	 */
	public void add(GPS gps) {
		gpsList.add(gps);
	}

	/**
	 * 删除指定路径Path位置处的GPS坐标
	 * 
	 * @param index
	 *            路径Path位置
	 */
	public void deleteGps(int index) {
		gpsList.remove(index);
	}

	/**
	 * 获取路径中包含的GPS对象个数，也就是路径大小
	 * 
	 * @return GPS对象个数
	 */
	public int size() {
		return gpsList.size();
	}

	/**
	 * 获取Path中的坐标，字符串链表格式返回
	 * 
	 * @return 字符串链表格式的Path中的所有坐标
	 */
	public ArrayList<String> getPathCoords() {
		ArrayList<String> coords = new ArrayList<String>();
		for (GPS gps : gpsList) {
			coords.add(gps.getGpsCoord());
		}
		return coords;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (carName != null) {
			sb.append("carName:").append(carName).append(" ");
		}
		if (pathId != 0) { // 如果是0，toString不能将其添加，所以路径ID不能为0
			sb.append("pathId:").append(pathId);
		}
		for (GPS gps : gpsList) {
			sb.append(gps.toString()).append("\r\n");
		}
		return sb.toString();
	}
}
