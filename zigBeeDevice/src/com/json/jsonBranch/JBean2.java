package com.json.jsonBranch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-6-22
 * Time: 16:52:19
 * To change this template use File | Settings | File Templates.
 */
/*
��Աȫ��Сд��ͷ
*/
public class JBean2 {
    private int b_int;
    private String b_String;
    private List b_List;
    private JBeanSub2 b_Object;

    private static final String jDate = "{\"b_int\":1988,\"b_String\":\"sheep\",\"b_List\":[\"List1\",\"List2\",\"List3\"],\"b_Object\":{\"sub_int\":2012}}";

    public void initBean(){
        this.setB_int(1988);
        this.setB_String("sheep");
        this.b_List = new ArrayList();
        this.b_Object = new JBeanSub2();

        this.b_List.add("List1");
        this.b_List.add("List2");
        this.b_List.add("List3");

        this.b_Object.setSub_int(2012);
    }

    public String beanToString(){
        StringBuilder sb = new StringBuilder();
        String CRLF = System.getProperty("line.seperator","\n");

        sb.append("B_int:" + this.b_int);
        sb.append(CRLF);
        sb.append("B_String:" + this.b_String);
        sb.append(CRLF);
        sb.append("B_List:" + "����");
        sb.append(CRLF);
        sb.append("B_Object:" + "����");

        return sb.toString();
    }

    public int getB_int() {
        return b_int;
    }

    public void setB_int(int b_int) {
        this.b_int = b_int;
    }

    public String getB_String() {
        return b_String;
    }

    public void setB_String(String b_String) {
        this.b_String = b_String;
    }

    public List getB_List() {
        return b_List;
    }

    public void setB_List(List b_List) {
        this.b_List = b_List;
    }

    public JBeanSub2 getB_Object() {
        return b_Object;
    }

    public void setB_Object(JBeanSub2 b_Object) {
        this.b_Object = b_Object;
    }

    public static String getjDate() {
        return jDate;
    }
}

class JBeanSub2{
    private int sub_int;

    public int getSub_int() {
        return sub_int;
    }

    public void setSub_int(int sub_int) {
        this.sub_int = sub_int;
    }
}
