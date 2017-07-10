package com.json.jsonBranch;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 11:54:40
 * To change this template use File | Settings | File Templates.
 */
public class JacksonBranch extends JsonBase {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    protected void JsonToBean() throws Exception {        
        JBean2 bean = mapper.readValue(JBean.getjDate(), JBean2.class);
        System.out.println(bean.beanToString());
    }

    @Override
    protected void BeanToJson() throws Exception {
        JBean2 bean = new JBean2();
        bean.initBean();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
        mapper.writeValue(gen, bean);
        gen.close();
        String json = sw.toString();
        System.out.println(json);
    }
}
