package sy.model;

import java.util.Date;
/**
 * 天气和生活指标封装类
 * @author dengcq
 *
 */
public class WeatherEnv {
	/**
	 * 天气情况
	 */
	private String weather;
	/**
	 * 白天天气情况
	 */
	private String weatherDay;
	/**
	 * 晚上天气情况
	 */
	private String weatherNight;
	
	/**
	 * 当前温度
	 */
	private String temperatureCurr;
	/**
	 * 温度最低
	 */
	private String temperatureRange;
	
	/**
	 * 湿度
	 */
	private String humility;
	/**
	 * 气压
	 */
	private String pressure;
	/**
	 * 风向
	 */
	private String windDir;
	/**
	 * 风速
	 */
	private String windSpeed;
	/**
	 * 日升时间
	 */
	private String sunUp;
	/**
	 * 日落时间
	 */
	private String sunSet;
	/**
	 * 紫外线
	 */
	private String ultraviolet;
	/**
	 * 空气质量
	 */
	private String aqi;
	/**
	 * PM2.5值
	 */
	private String pm25;
	/**
	 * PM10值
	 */
	private String pm10;
	/**
	 * 二氧化碳值
	 */
	//private String co2;
	/**
	 * 二氧化硫值
	 */
	private String so2;
	/**
	 * 二氧化氮值
	 */
	private String no2;
	/**
	 * 一氧化碳值
	 */
	private String co;
	/**
	 * 臭氧值
	 */
	private String o3;
	/**
	 * 更新时间
	 */
	//private Date updateTime;
	private String updateTime;
	/**
	 * 城市区域
	 */
	private String areacity;
		
	
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeatherDay() {
		return weatherDay;
	}
	public void setWeatherDay(String weatherDay) {
		this.weatherDay = weatherDay;
	}
	public String getWeatherNight() {
		return weatherNight;
	}
	public void setWeatherNight(String weatherNight) {
		this.weatherNight = weatherNight;
	}
	public String getTemperatureCurr() {
		return temperatureCurr;
	}
	public void setTemperatureCurr(String temperatureCurr) {
		this.temperatureCurr = temperatureCurr;
	}
	public String getTemperatureRange() {
		return temperatureRange;
	}
	public void setTemperatureRange(String temperatureRange) {
		this.temperatureRange = temperatureRange;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getWindDir() {
		return windDir;
	}
	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}
	public String getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String getSunUp() {
		return sunUp;
	}
	public void setSunUp(String sunUp) {
		this.sunUp = sunUp;
	}
	public String getSunSet() {
		return sunSet;
	}
	public void setSunSet(String sunSet) {
		this.sunSet = sunSet;
	}
	public String getUltraviolet() {
		return ultraviolet;
	}
	public void setUltraviolet(String ultraviolet) {
		this.ultraviolet = ultraviolet;
	}
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public String getPm10() {
		return pm10;
	}
	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}
//	public String getCo2() {
//		return co2;
//	}
//	public void setCo2(String co2) {
//		this.co2 = co2;
//	}
	public String getSo2() {
		return so2;
	}
	public void setSo2(String so2) {
		this.so2 = so2;
	}
	public String getNo2() {
		return no2;
	}
	public void setNo2(String no2) {
		this.no2 = no2;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getO3() {
		return o3;
	}
	public void setO3(String o3) {
		this.o3 = o3;
	}
	public String getHumility() {
		return humility;
	}
	public void setHumility(String humility) {
		this.humility = humility;
	}
/*	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}*/
	
		
	public String getAreacity() {
		return areacity;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public void setAreacity(String areacity) {
		this.areacity = areacity;
	}
	@Override
	public String toString() {
		return "WeatherEnv [temperatureCurr=" + temperatureCurr + ", temperatureRange=" + temperatureRange
				+ ", humility=" + humility + ", pressure=" + pressure + ", windDir=" + windDir + ", windSpeed="
				+ windSpeed + ", sunUp=" + sunUp + ", sunSet=" + sunSet + ", ultraviolet=" + ultraviolet + ", aqi="
				+ aqi + ", pm25=" + pm25 + ", pm10=" + pm10 +/* ", co2=" + co2 +*/ ", so2=" + so2 + ", no2=" + no2 + ", co="
				+ co + ", o3=" + o3 + ", updateTime=" + updateTime + ", areacity=" + areacity + "]";
	}
	
	
	
}
