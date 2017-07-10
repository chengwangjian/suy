package com.flywind.app.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.flywind.app.entities.Modedevicebind;

public class JSONFilter {
	
	public static void main(String[] args) {
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("id",11);
		m.put("name", "nn");
	
		List<Modedevicebind> l=new ArrayList<Modedevicebind>();
		l.add(new Modedevicebind());
		Xmldevicebind b=new Xmldevicebind();
		b.setMain(l);
		
		A a = new A();
		Object[] ob=new Object[]{m,a,b};
		
		PropertyFilter pfilter = new PropertyFilter() {
		    @Override
			public boolean apply(Object source, String name, Object value) {
		    	if(source instanceof A)return false;
		        return true;
		    }
		};
		 
		NameFilter nfilter = new NameFilter() { 
		    @Override
			public String process(Object source, String name, Object value) {
		        if (name.equals("id")) {
		            return "ID";
		        } 
		        return name;
		    } 
		};
		
		ValueFilter vfilter = new ValueFilter() {
			@Override
			public Object process(Object source, String name, Object value) {
				if (name.equals("name")) {
					return null;
				}
				return value;
			}
		};
				
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getPropertyFilters().add(pfilter);
//		serializer.getNameFilters().add(nfilter);
//		serializer.getValueFilters().add(vfilter);
		serializer.write(ob);
		System.out.println(out.toString());		
		
		JSONSerializerMap mapping = new JSONSerializerMap();
		mapping.put(A.class, new JavaBeanSerializer(A.class, Collections.singletonMap("id", "uid")));		 
		JSONSerializer serializer2 = new JSONSerializer(mapping);
		serializer2.write(a);
		System.out.println(serializer2.toString());	
		
	}
	
	public static class A {
	    private int id;
	    private String name;
	 
	    public int getId() {
	        return id;
	    }
	 
	    public void setId(int id) {
	        this.id = id;
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	}	
	
    public static StringAware filter(Object obj,String[] fields){
        if(obj == null){return null;}
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(obj.getClass(), fields);      
        return new StringAware(JSON.toJSONString(obj,filter));
    }
     
    public static List<StringAware> listFilter(List<?> objs,String[] fields){
        if(objs == null){return null;}         
        List<StringAware> awareList = new ArrayList<StringAware>();     
        if(objs.size()>0){
            Class<?> clazz = objs.get(0).getClass();     
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, fields);
            for(Object obj:objs){
                awareList.add(new StringAware(JSON.toJSONString(obj,filter)));
            }
        }        
        return awareList;
    }
    
    public static class StringAware implements JSONAware {
        private String functionString;
        public StringAware(String functionString) {
            this.functionString = functionString;
        }
        @Override
        public String toJSONString() {
            return this.functionString;
        }
    }
    
}


