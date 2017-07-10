package org.jbx.util;

public abstract class DataSourceContextHolder {
	 
    public final static String DATA_SOURCE_1 = "zigbeedevice";
    public final static String DATA_SOURCE_2 = "ntx_bugfree";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
 // 设置数据源类型  
    public static void setDataSourceType(String dataSourceType) {  
        contextHolder.set(dataSourceType);  
    }  
  
    // 获取数据源类型  
    public static String getDataSourceType() {  
        return contextHolder.get();  
    }  
  
    // 清除数据源类型  
    public static void clearDataSourceType() {  
        contextHolder.remove();  
    }  
}