package sy.util;

/**
 * 常量接口
 * @author R&D1
 *
 */

public interface Constants {
	//告警消息IOS推送
	public final Integer WMODE_DOORLOCK = 47; //门锁
	public final Integer WMODE_CUSTOM = 70;//自定义消息
	public final Integer WMODE_LAYER = 49; //更换滤网
	
	//农业项目图表字段
	public final Integer CHART_AIR_TEMP = 0; //空气温度
	public final Integer CHART_AIR_HUMIDITY = 1; //空气湿度
	public final Integer CHART_SUN_ILL = 2; //阳光照度
	public final Integer CHART_AIR_CO2 = 3; //空气CO2
	public final Integer CHART_SOIL_TEMP = 4; //土壤温度
	public final Integer CHART_SOIL_HUMIDITY = 5; //土壤湿度
	public final Integer CHART_SOIL_PH = 6; //土壤酸碱度
	public final Integer CHART_SOIL_ELETRIC = 7; //土壤导电
	public final Integer CHART_ULTR = 8; //紫外线
	public final Integer CHART_LEFT = 9; //余量
	public final Integer CHART_PM25 = 10; //PM2.5
	
	public final Integer CHART_SUN_EXTREMELY = 13; //日照强度
	
	//串口设备地暖，模板Id
	public final Integer TEMPLATE_START_ID = 10000;
}
