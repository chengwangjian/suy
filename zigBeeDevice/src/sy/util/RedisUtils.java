package sy.util;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis操作工具类
 * @author ZhangHC
 * @since 2017-05-03
 *
 */
public class RedisUtils {
	private static Logger logger = Logger.getLogger(RedisUtils.class);
	//redis地址
	private static String host = PropertiesUtil.getProperty("redis.host");
	private static JedisPool pool;

	/**
	 * 将数据存到key对应的redis链表中
	 * @param key 键值
	 * @param attrData json型数据
	 */
	public static void addData(String key,String attrData){
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();		
			jedis.rpush(key, attrData);
		}catch(Exception e){
			logger.error("jedis error",e);
		}finally{
			returnResource(pool,jedis);
		}
	}
	
	/**
	 * 获取redis中列表长度
	 * @return
	 */
	public static Long getLen(String key){
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();			
			return jedis.llen(key);
		}catch(Exception e){
			logger.error("jedis error",e);
		}finally{
			returnResource(pool,jedis);
		}
		return 0L;
	}
	
	/**
	 * 按范围获取redis中存储的list(若需要按顺序取，则需左存右取或右存左取)
	 * @param key 键值
	 * @param start 起始位置
	 * @param end 结束位置
	 * @return
	 */
	public static List<String> getData(String key,int start,int end) throws Exception{
		if(end > 0 && end < start){
			logger.info("end can not bigger than start");
			return null;
		}
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();	
			List<String> list = jedis.lrange(key, start, end);
			return list;
		}catch(Exception e){
			logger.error("jedis error",e);
			throw e;
		}finally{
			returnResource(pool,jedis);
		}
	}
	
	/**
	 * 将值存入不重复无序集合
	 * @param key
	 * @param value
	 */
	public static void addDataSet(String key,String value){
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();
			jedis.sadd(key, value);
		}catch(Exception e){
			logger.error("jedis error",e);
		}finally{
			returnResource(pool,jedis);
		}
	}
	
	/**
	 * 根据键值获取houseIeee列表
	 * @param key
	 * @return
	 */
	public static Set<String> getDataSet(String key){
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();			
			return jedis.smembers(key);
		}catch(Exception e){
			logger.error("jedis error",e);
		}finally{
			returnResource(pool,jedis);
		}
		return null;
	}
	
	/**
	 * 将数据存到key对应的redis链表中
	 * @param key 键值
	 * @param attrData json型数据
	 */
	public static void deleteData(String key){
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();		
			jedis.del(key);
		}catch(Exception e){
			logger.error("jedis error",e);
		}finally{
			returnResource(pool,jedis);
		}
	}
	
	/**
	 * 按范围保留数据（应当与取值方向一致）
	 * @param key 键值
	 * @param start 起始位置
	 * @param end 结束位置
	 */
	public static void ltrimData(String key ,int start, int end){
		if(end > 0 && end < start){
			logger.info("end can not bigger than start");
			return;
		}
		Jedis jedis = null;
		try{
			if(pool == null)
				pool = getPool();
			jedis = pool.getResource();	
			//双向链表左往右保留下标为第start到end的数据,end为-1时，表示最后一个
			jedis.ltrim(key, start, end);
		}catch(Exception e){
			logger.error("jedis error",e);
		}finally{
			returnResource(pool,jedis);
		}
	}
	
	/**
	 * 获取redis连接池对象
	 * @return
	 */
	public static JedisPool getPool() {  
		if (pool == null) {  
			JedisPoolConfig config = new JedisPoolConfig();  
			//控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
			//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
			config.setMaxActive(-1);  
			//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
			config.setMaxIdle(200);  
			//表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
			config.setMaxWait(1000L * 100);  
			//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
			config.setTestOnBorrow(false);
			//空闲检测可用性
			config.setTestWhileIdle(true);
			//配置检测间隔时间
			config.setTimeBetweenEvictionRunsMillis(1000L*120);
			pool = new JedisPool(config,host,6379,30000);
		}  
		return pool;
	}  

	/** 
	 * redis连接返还到连接池 
	 * @param pool  
	 * @param redis 
	 */  
	public static void returnResource(JedisPool pool, Jedis redis) {  
		if (redis != null) {  
			pool.returnResource(redis);  
		}  
	} 
}
