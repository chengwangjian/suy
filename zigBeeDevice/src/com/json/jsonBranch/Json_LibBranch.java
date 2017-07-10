package com.json.jsonBranch;

import net.sf.json.JSONArray;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 14:29:33
 * To change this template use File | Settings | File Templates.
 */
public class Json_LibBranch extends JsonBase{
    @Override
    protected void JsonToBean() throws Exception {

    }

    @Override
    protected void BeanToJson() throws Exception {
        JBean2 bean = new JBean2();
        bean.initBean();
        //System.out.println(JSONObject.fromObject(bean));
        System.out.println(JSONArray.fromObject(bean).toString());
    }
}
