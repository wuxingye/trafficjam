package cn.shu.jam.baidu;

/**
 * 百度GPS转换接口，定义了一些常量
 * <p>
 * {@link GpsToBaiduGps}一次最多可转换100个坐标点，请求一次
 * <p>
 * {@link BatchGpsToBaiduGps}无限转换坐标点，突破100个，请求多次
 * <p>
 * <p>
 * json正确返回格式：<br>
 * {"status":0,"result":[{"x":116.40571096379,"y":39.892912686645},{"x":
 * 116.40520474498,"y":39.892803753098}]} <a
 * href="http://developer.baidu.com/map/changeposition.htm">百度坐标转换API链接</a>
 * <p>
 * xml正确返回格式： // TODO
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN博客<a>
 * @date 2014-12-7 上午11:59:57
 * @version 1.0
 */
public interface BaiduGpsConstants {
	public final static int MAX = 100; // 一次最多可转换100个坐标点

	public final static String XML = "xml";// 返回结果格式 json
	public final static String JSON = "json";// 返回结果格式xml

	public final static int DEVICE = 1;// GPS设备获取的角度坐标;
	public final static int SOUGOU = 2;// GPS获取的米制坐标、sogou地图所用坐标;
	public final static int GOOGLE = 3;// google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标
	public final static int GOOGLE_MI = 4;// 3中列表地图坐标对应的米制坐标
	public final static int BAIDU = 5;// 百度地图采用的经纬度坐标
	public final static int BAIDU_MI = 6;// 百度地图采用的米制坐标
	public final static int MAPBAR = 7;// mapbar地图坐标
	public final static int T51 = 8;// 51地图坐标

	public final static String DN = "http://api.map.baidu.com";// domainName域名
	public final static String SN = "geoconv";// serverName服务名
	public final static String VERSION = "v1";// version服务版本号
	public final static String AK = "uPzQNydVBGB9G88rTbAECDi9";// 开发者密钥
}
