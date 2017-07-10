package com.json.jsonBranch;

import com.google.gson.Gson;
import com.json.jsonBranch.JsonBase;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 14:29:06
 * To change this template use File | Settings | File Templates.
 */
public class GsonBranch extends JsonBase {
    private static final Gson gson = new Gson();

    @Override
    protected void JsonToBean() throws Exception {
        //JBean2 bean = gson.fromJson(JBean.getjDate(), new TypeToken<JBean2>(){}.getType());
        JBean2 bean = gson.fromJson(JBean.getjDate(), JBean2.class);
        System.out.println(bean.beanToString());
    }

    @Override
    protected void BeanToJson() throws Exception {
        JBean2 bean = new JBean2();
        bean.initBean();
        System.out.println(gson.toJson(bean));
    }
}
