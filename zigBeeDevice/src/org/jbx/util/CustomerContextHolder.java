package org.jbx.util;

public abstract class CustomerContextHolder {
	 
    public final static String DATA_SOURCE_1 = "zigbeedeviceSource";
    public final static String DATA_SOURCE_2 = "openfireSource";
    public final static String DATA_SOURCE_3 = "zigbeedeviceTarget";
    public final static String DATA_SOURCE_4 = "openfireTarget";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
      
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
      
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
}