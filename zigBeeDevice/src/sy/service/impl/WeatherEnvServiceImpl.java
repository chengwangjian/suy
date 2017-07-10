package sy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sy.dao.BaseDaoI;
import sy.model.WeatherEnv;
import sy.service.WeatherEnvServiceI;
import sy.util.WeatherEnvUtil;

@Service("weatherEnvServiceI")
public class WeatherEnvServiceImpl implements WeatherEnvServiceI {
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public Map add(String houseIeee, String serverIp) throws Exception {
		WeatherEnvUtil weatherEnvUtil = new WeatherEnvUtil();
		if(serverIp.equals("192.168.1.185")){
			serverIp = "218.104.133.242";
		}
		WeatherEnv weather = weatherEnvUtil.getByIp(serverIp);
	    Map<String, Object> param = new HashMap<String, Object>();		   
	    StringBuffer sql2 = new StringBuffer("insert into weatherenv ");
		sql2.append("(areacity, weather, weatherDay, weatherNight, temperatureCurr, temperatureRange, humility, pressure, windDir, windSpeed, sunUp, sunSet, ultraviolet, aqi, pm25, pm10, so2, no2, co, o3, updateTime) values (:areacity, :weather, :weatherDay, :weatherNight, :temperatureCurr, :temperatureRange, :humility, :pressure, :windDir, :windSpeed, :sunUp, :sunSet, :ultraviolet, :aqi, :pm25, :pm10, :so2, :no2, :co, :o3, :updateTime)");
		param.put("areacity", weather.getAreacity());
		param.put("weather", weather.getWeather());
		param.put("weatherDay", weather.getWeatherDay());
		param.put("weatherNight", weather.getWeatherNight());
		param.put("temperatureCurr", weather.getTemperatureCurr());
		param.put("temperatureRange", weather.getTemperatureRange());
		param.put("humility", weather.getHumility());
		param.put("pressure", weather.getPressure());
	    param.put("windDir", weather.getWindDir());
		param.put("windSpeed", weather.getWindSpeed());
		param.put("sunUp", weather.getSunUp());
		param.put("sunSet", weather.getSunSet());
		param.put("ultraviolet", weather.getUltraviolet());
		param.put("aqi", weather.getAqi());
		param.put("pm25", weather.getPm25());
		param.put("pm10", weather.getPm10());
		param.put("so2", weather.getSo2());
		param.put("no2", weather.getNo2());
		param.put("co", weather.getCo());
		param.put("o3", weather.getO3());
		param.put("updateTime", weather.getUpdateTime());
		this.mapDao.executeSql2(sql2.toString(),param);
		Map mapBack = (Map)JSON.parse(JSONObject.toJSONStringWithDateFormat(weather,"yyyy-MM-dd HH:mm:ss"));
		return mapBack;	
	}
	
	@Override
	public Map updateWeather(String houseIeee,String serverIp,String city) throws Exception {		
		    WeatherEnvUtil weatherEnvUtil = new WeatherEnvUtil();
		    WeatherEnv weather = weatherEnvUtil.get(city);
			StringBuilder sql = new StringBuilder("update weatherenv w set ");
			sql.append("areacity='").append( weather.getAreacity()).append("',");
			sql.append("weather='").append(weather.getWeather()).append("',");
			sql.append("weatherDay='").append(weather.getWeatherDay()).append("',");
			sql.append("weatherNight='").append(weather.getWeatherNight()).append("',");
			sql.append("temperatureCurr='").append(weather.getTemperatureCurr()).append("',") ;
			sql.append("temperatureRange='").append(weather.getTemperatureRange()).append("',") ;
			sql.append("humility='").append(weather.getHumility()).append("',") ;
			sql.append("pressure='").append(weather.getPressure()).append("',") ;
			sql.append("windDir='").append(weather.getWindDir()).append("',") ;
			sql.append("windSpeed='").append(weather.getWindSpeed()).append("',") ;
			sql.append("sunUp='").append(weather.getSunUp()).append("',") ;
			sql.append("sunSet='").append(weather.getSunSet()).append("',") ;
			sql.append("ultraviolet='").append(weather.getUltraviolet()).append("',") ;
			sql.append("aqi='").append(weather.getAqi()).append("',");
			sql.append("pm25='").append(weather.getPm25()).append("',") ;
			sql.append("pm10='").append(weather.getPm10()).append("',") ;
			sql.append("so2='").append(weather.getSo2()).append("',") ;
			sql.append("no2='").append(weather.getNo2()).append("',") ;
			sql.append("co='").append(weather.getCo()).append("',") ;
			sql.append("o3='").append(weather.getO3()).append("',") ;
			sql.append("updateTime='").append(weather.getUpdateTime()).append("'") ;
			sql.append(" where w.areacity=:areacity");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("areacity", city);			
			mapDao.executeSql2(sql.toString(),param);
			Map mapBack = (Map)JSON.parse(JSONObject.toJSONStringWithDateFormat(weather,"yyyy-MM-dd HH:mm:ss"));
			return mapBack;	
	}
	
	@Override
	public Map Weather(String houseIeee, String serverIp) throws Exception {
		Map weather = null;
		String currcity = WeatherEnvUtil.getCity(serverIp);
		if (StringUtils.isBlank(currcity)) throw new IllegalArgumentException("根据 请求Ip获取城市失败!");
		Map<String, Object> parames = new HashMap<String,Object>(); 
		parames.put("areacity", currcity);
		StringBuffer sql = new StringBuffer("select * from weatherenv w where w.areacity =:areacity");
		List<Map> weatherList = mapDao.executeSql(sql.toString(), parames);
		if (weatherList != null && weatherList.size() >0 ) {
			weather = weatherList.get(0);
			Date updatetime = (Date)weatherList.get(0).get("updateTime");
			long diff =(new Date().getTime() - updatetime.getTime())/(1000*60*60);
			if (true) weather = updateWeather(houseIeee, serverIp, currcity);
		}else {
		    weather = add(houseIeee,serverIp);
		}
		return weather; 
	}
	
	@Override
	public void weatherEnvUpdate() throws Exception {
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select * from weatherenv w where 1=1");
		List<Map> t = mapDao.executeSql(sql2.toString());
		for(int i = 0; i < t.size(); i ++) {
			String updatecity = (String)t.get(i).get("areacity");
			SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currtime = new Date();
			Date updatetime = (Date)t.get(i).get("updateTime");
			long diff =(currtime.getTime() - updatetime.getTime())/(1000*60*60);
			if(diff > 1){
				    WeatherEnvUtil weatherEnvUtil = new WeatherEnvUtil();
				    WeatherEnv weather = weatherEnvUtil.get(updatecity);
					StringBuilder sql = new StringBuilder("update weatherenv w set ");
					sql.append("areacity='").append(weather.getAreacity()).append("',");
					sql.append("weather='").append(weather.getWeather()).append("',");
					sql.append("weatherDay='").append(weather.getWeatherDay()).append("',");
					sql.append("weatherNight='").append(weather.getWeatherNight()).append("',");
					sql.append("temperatureCurr='").append(weather.getTemperatureCurr()).append("',") ;
					sql.append("temperatureRange='").append(weather.getTemperatureRange()).append("',") ;
					sql.append("humility='").append(weather.getHumility()).append("',") ;
					sql.append("pressure='").append(weather.getPressure()).append("',") ;
					sql.append("windDir='").append(weather.getWindDir()).append("',") ;
					sql.append("windSpeed='").append(weather.getWindSpeed()).append("',") ;
					sql.append("sunUp='").append(weather.getSunUp()).append("',") ;
					sql.append("sunSet='").append(weather.getSunSet()).append("',") ;
					sql.append("ultraviolet='").append(weather.getUltraviolet()).append("',") ;
					sql.append("aqi='").append(weather.getAqi()).append("',");
					sql.append("pm25='").append(weather.getPm25()).append("',") ;
					sql.append("pm10='").append(weather.getPm10()).append("',") ;
					sql.append("so2='").append(weather.getSo2()).append("',") ;
					sql.append("no2='").append(weather.getNo2()).append("',") ;
					sql.append("co='").append(weather.getCo()).append("',") ;
					sql.append("o3='").append(weather.getO3()).append("',") ;
					sql.append("updateTime='").append(weather.getUpdateTime()).append("'") ;
					sql.append(" where w.areacity=:areacity");
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("areacity", updatecity);			
					mapDao.executeSql2(sql.toString(),param);
			}
		}
		
	}

}
