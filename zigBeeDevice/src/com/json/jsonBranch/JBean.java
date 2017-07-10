package com.json.jsonBranch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 14:51:21
 * To change this template use File | Settings | File Templates.
 */
/*
��Աȫ����д��ͷ
*/
public class JBean {
    private int B_int;
    private String B_String;
    private List B_List;
    private JBeanSub1 B_Object;

    private static final String jDate = "{\"b_int\":1988,\"b_String\":\"sheep\",\"b_List\":[\"List1\",\"List2\",\"List3\"],\"b_Object\":{\"sub_int\":2012}}";

    public void initBean(){
        this.setB_int(1988);
        this.setB_String("sheep");
        this.B_List = new ArrayList();
        this.B_Object = new JBeanSub1();

        this.B_List.add("List1");
        this.B_List.add("List2");
        this.B_List.add("List3");

        this.B_Object.setSub_int(2012);
    }

    public String beanToString(){
        StringBuilder sb = new StringBuilder();
        String CRLF = System.getProperty("line.seperator","\n");

        sb.append("B_int:" + this.B_int);
        sb.append(CRLF);
        sb.append("B_String:" + this.B_String);
        sb.append(CRLF);
        sb.append("B_List:" + "����");
        sb.append(CRLF);
        sb.append("B_Object:" + "����");

        return sb.toString();
    }

    public int getB_int() {
        return B_int;
    }

    public void setB_int(int b_int) {
        B_int = b_int;
    }

    public String getB_String() {
        return B_String;
    }

    public void setB_String(String b_String) {
        B_String = b_String;
    }

    public List getB_List() {
        return B_List;
    }

    public void setB_List(List b_List) {
        B_List = b_List;
    }

    public JBeanSub1 getB_Object() {
        return B_Object;
    }

    public void setB_Object(JBeanSub1 b_Object) {
        B_Object = b_Object;
    }

    public static String getjDate() {
        return jDate;
    }
}

class JBeanSub1{
    private int Sub_int;

    public int getSub_int() {
        return Sub_int;
    }

    public void setSub_int(int sub_int) {
        Sub_int = sub_int;
    }
}