package sy.service;

import java.util.Map;

public interface WeatherEnvServiceI {
	//将天气预报存储在数据库中
	public Map add(String houseIeee, String serverIp) throws Exception;	
	//public List<WeatherEnv> getWeather(String houseIeee, String serverIp) throws Exception;
	//请求获取城市天气预报
	public Map Weather(String houseIeee, String serverIp) throws Exception;
	//超过1小时更新天气
	public Map updateWeather(String houseIeee,String serverIp,String city) throws Exception;
	//定时更新城市天气数据（超过1小时就更新）
	public void weatherEnvUpdate() throws Exception;
	
}