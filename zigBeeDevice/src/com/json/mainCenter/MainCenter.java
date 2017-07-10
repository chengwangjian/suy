package com.json.mainCenter;

import com.json.jsonBranch.GsonBranch;
import com.json.jsonBranch.JacksonBranch;
import com.json.jsonBranch.Json_LibBranch;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 10:04:10
 * To change this template use File | Settings | File Templates.
 */
public class MainCenter {
    public static void main(String[] args){
        JacksonBranch jBranch = new JacksonBranch();//jackson
        jBranch.run();

        System.out.println("=====================================");
        
        GsonBranch gBranch = new GsonBranch();//gson
        gBranch.run();

        System.out.println("=====================================");

        Json_LibBranch lBranch = new Json_LibBranch();//json_lib
        lBranch.run();        
    }
}

