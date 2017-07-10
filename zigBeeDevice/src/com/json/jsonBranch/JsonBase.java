package com.json.jsonBranch;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 12:00:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class JsonBase {
    public void run(){
        System.out.println(this.getClass().getName() + ": JSON TO BEAN");
        try{
            this.JsonToBean();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println(this.getClass().getName() + ": BEAN TO JSON");
        try{
            this.BeanToJson();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected abstract void JsonToBean() throws Exception ;
    protected abstract void BeanToJson() throws Exception ;    
}
