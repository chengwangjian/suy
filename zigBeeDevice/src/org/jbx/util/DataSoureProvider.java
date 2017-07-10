package org.jbx.util;

import java.util.Map;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSoureProvider {
	private static DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
	private static DriverManagerDataSource dataSource2 = new DriverManagerDataSource();
	private static DriverManagerDataSource dataSource3 = new DriverManagerDataSource();
	private static DriverManagerDataSource dataSource4 = new DriverManagerDataSource();
	
//	源数据源
	public static DriverManagerDataSource getDataSource1() {
		return dataSource1;
	}
	public static void setDataSource1(Map ds) {
//		DataSoureProvider.dataSource1 = dataSource1;
		DataSoureProvider.dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
		DataSoureProvider.dataSource1.setUrl("jdbc:mysql://"+ds.get("urlA").toString()+":3306/zigbeedevice");//"jdbc:mysql://127.0.0.1:3306/zigbeedevice");
		DataSoureProvider.dataSource1.setUsername(ds.get("userA").toString());//"root");
		DataSoureProvider.dataSource1.setPassword(ds.get("pwdA").toString());//"Ntx@$Yanfa247");		
	}
	public static DriverManagerDataSource getDataSource2() {
		return dataSource2;
	}
	public static void setDataSource2(Map ds) {
//		DataSoureProvider.dataSource2 = dataSource2;
		DataSoureProvider.dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
		DataSoureProvider.dataSource2.setUrl("jdbc:mysql://"+ds.get("urlB").toString()+":3306/openfire");//jdbc:mysql://218.104.133.247:3306/zigbeedevice");
		DataSoureProvider.dataSource2.setUsername(ds.get("userB").toString());//"root");
		DataSoureProvider.dataSource2.setPassword(ds.get("pwdB").toString());//"Ntx@$Yanfa247");			
	}
	
//	目标数据源
	public static DriverManagerDataSource getDataSource3() {
		return dataSource3;
	}
	public static void setDataSource3(Map ds) {
		DataSoureProvider.dataSource3.setDriverClassName("com.mysql.jdbc.Driver");
		DataSoureProvider.dataSource3.setUrl("jdbc:mysql://"+ds.get("urlA").toString()+":3306/zigbeedevice");//"jdbc:mysql://218.104.133.247:3306/zigbeedevice");
		DataSoureProvider.dataSource3.setUsername(ds.get("userA").toString());//"root");
		DataSoureProvider.dataSource3.setPassword(ds.get("pwdA").toString());//"Ntx@$Yanfa247");			
	}	
	public static DriverManagerDataSource getDataSource4() {
		return dataSource4;
	}
	public static void setDataSource4(Map ds) {
		DataSoureProvider.dataSource4.setDriverClassName("com.mysql.jdbc.Driver");
		DataSoureProvider.dataSource4.setUrl("jdbc:mysql://"+ds.get("urlB").toString()+":3306/openfire");//"jdbc:mysql://218.104.133.247:3306/zigbeedevice");
		DataSoureProvider.dataSource4.setUsername(ds.get("userB").toString());//"root");
		DataSoureProvider.dataSource4.setPassword(ds.get("pwdB").toString());//"Ntx@$Yanfa247");			
	}	
	
}
