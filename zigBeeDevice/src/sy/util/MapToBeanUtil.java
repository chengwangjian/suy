package sy.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class MapToBeanUtil {
	
	private static Logger log = Logger.getLogger(MapToBeanUtil.class);
	/**
	 * 将Map转为实体对象
	 * @param type
	 * @param map
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static Object convertMap(Class type,Map map)throws IntrospectionException,
		IllegalAccessException,InstantiationException,InvocationTargetException{
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		
		for(int i=0;i<propertyDescriptors.length;i++){
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			
			if(map.containsKey(propertyName)){
				try{
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					
					args[0]=value;
					
					descriptor.getWriteMethod().invoke(obj, args);
				}catch(Exception e){
					log.info(propertyName+" Exception",e);
					continue;
				}
			}
		}
		return obj;
	}
	/**
	 * 将实体对象转为一个Map
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map convertBean(Object bean) 
            throws IntrospectionException, IllegalAccessException, InvocationTargetException { 
        Class type = bean.getClass(); 
        Map returnMap = new HashMap(); 
        BeanInfo beanInfo = Introspector.getBeanInfo(type); 

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
            if (!propertyName.equals("class")) { 
            	Method readMethod = descriptor.getReadMethod(); 
                Object result = readMethod.invoke(bean, new Object[0]); 
                if (result != null) { 
                    returnMap.put(propertyName, result); 
                } else { 
                    returnMap.put(propertyName, ""); 
                } 
            } 
        } 
        return returnMap; 
    } 
}
