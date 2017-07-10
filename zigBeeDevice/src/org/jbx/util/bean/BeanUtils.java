package org.jbx.util.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BeanUtils {
	/**
	 * 根据注解类获取含有该注解的成员变量的名字，如果有多个只取第一个。没有的话返回null
	 * @param annClass
	 * @param objClass
	 * @return
	 */
	public static Field getAnnotFieldByAnnotClass(Class<? extends Annotation> annClass,Class<?> objClass){
		Field[] fields = objClass.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(annClass)){
				return field;
			}
		}
		return null;
	}

//	/**
//	 * 获取注解为Column且名字为name的成员变量名
//	 * @param name     Column注解的名字属性值
//	 * @param objClass 被注解的对象
//	 * @return
//	 */
//	public static String getColumnFieldName(String name,Class<?> objClass){
//		Field[] fields = objClass.getDeclaredFields();
//		for(Field field : fields){
//			Column col = field.getAnnotation(Column.class);
//			if(col!= null && col.name().equals(name)){
//				return field.getName();
//			}
//		}
//		return null;
//	}
	
    /**
     * 返回方法数组（methods）中方法名为name的方法
     * 
     * @param methods
     * @param name
     * @return
     */
    private static Method getMethodByName(Method[] methods, String name) {
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }

	/**
	 * 将bean对象转换成Map键值对的形式
	 * @param bean
	 * @return
	 */
	public static Map<String,Object> convert2Map(Object obj){
		Map<String, Object> retMap = new HashMap<String, Object>();

		if(obj == null){
			return retMap;
		}
		
        // 获得对象的所有属性
        Class<? extends Object> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getMethods();

        for (int i = 0; i < fields.length; i++) {
            // 获取数组中对应的属性
            Field field = fields[i];
            String fieldName = field.getName();

            // 获得相应属性的getXXX和setXXX方法名称
            String getName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

            // 获取相应的方法
            try {
                Method getMethod = getMethodByName(methods,getName);
                if (getMethod != null) {
                	Object value = getMethod.invoke(obj);
                	if(value instanceof String){
                		value = StringUtils.trimToNull((String)value);
                	}
                    retMap.put(fieldName, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retMap;
	}
}
