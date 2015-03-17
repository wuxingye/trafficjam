package cn.shu.jam.baidu;

/**
 * �ٶ�GPSת���ӿڣ�������һЩ����
 * <p>
 * {@link GpsToBaiduGps}һ������ת��100������㣬����һ��
 * <p>
 * {@link BatchGpsToBaiduGps}����ת������㣬ͻ��100����������
 * <p>
 * <p>
 * json��ȷ���ظ�ʽ��<br>
 * {"status":0,"result":[{"x":116.40571096379,"y":39.892912686645},{"x":
 * 116.40520474498,"y":39.892803753098}]} <a
 * href="http://developer.baidu.com/map/changeposition.htm">�ٶ�����ת��API����</a>
 * <p>
 * xml��ȷ���ظ�ʽ�� // TODO
 * 
 * @author wuxingye <a href="http://blog.csdn.net/kakarot5">CSDN����<a>
 * @date 2014-12-7 ����11:59:57
 * @version 1.0
 */
public interface BaiduGpsConstants {
	public final static int MAX = 100; // һ������ת��100�������

	public final static String XML = "xml";// ���ؽ����ʽ json
	public final static String JSON = "json";// ���ؽ����ʽxml

	public final static int DEVICE = 1;// GPS�豸��ȡ�ĽǶ�����;
	public final static int SOUGOU = 2;// GPS��ȡ���������ꡢsogou��ͼ��������;
	public final static int GOOGLE = 3;// google��ͼ��soso��ͼ��aliyun��ͼ��mapabc��ͼ��amap��ͼ��������
	public final static int GOOGLE_MI = 4;// 3���б��ͼ�����Ӧ����������
	public final static int BAIDU = 5;// �ٶȵ�ͼ���õľ�γ������
	public final static int BAIDU_MI = 6;// �ٶȵ�ͼ���õ���������
	public final static int MAPBAR = 7;// mapbar��ͼ����
	public final static int T51 = 8;// 51��ͼ����

	public final static String DN = "http://api.map.baidu.com";// domainName����
	public final static String SN = "geoconv";// serverName������
	public final static String VERSION = "v1";// version����汾��
	public final static String AK = "uPzQNydVBGB9G88rTbAECDi9";// ��������Կ
}
