package sy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
  
  
/** 
* @title: MacAddressUtil  
* @description：获取MAC地址 
* @author:hlw
* @date： 2013-5-5 下午04:42:50 
*/  

public class MacAddressUtil{  
    private static final Logger log = Logger.getLogger(MacAddressUtil.class); 
    
    /** 
    * @MethodName: getOSName  
    * @description : 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等 
    * @author：hlw 
    * @date： 2013-5-5 下午04:43:36 
    * @return String 
    */  
    public static String getOSName() {  
        return System.getProperty("os.name").toLowerCase();  
    }  
      
    /** 
     * @MethodName: getWindowMACAddress  
     * @description : 获取widnowXp网卡的mac地址 
     * @author：hlw 
     * @date： 2013-5-5 下午04:45:12 
     * @param execStr 
     * @return String 
     */  
//    getWindowXPMACAddress
     public static List<String> getWindowMACAddress(String execStr) {  
         String mac = null;  
         BufferedReader bufferedReader = null;  
         Process process = null;  
         List<String> macList = new ArrayList<>();
         
         try {  
             // windows下的命令，显示信息中包含有mac地址信息  
             process = Runtime.getRuntime().exec(execStr);  
             bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));  
             String line = null;  
             int index = -1;  
             while ((line = bufferedReader.readLine()) != null) {  
                 if(line.indexOf("本地连接") != -1)     //排除有虚拟网卡的情况  
                     continue;  
                   
                 // 寻找标示字符串[physical address]  
                 index = line.toLowerCase().indexOf("physical address");  
                 index = (index == -1? line.indexOf("物理地址") : index);
                 if (index != -1) {  
                     index = line.indexOf(":");  
                     if (index != -1) {  
                         //取出mac地址并去除2边空格  
                         mac = line.substring(index + 1).trim();
                         macList.add(mac);
                     }  
                     break;  
                 }     
             }  
         } catch (IOException e) {  
             e.printStackTrace();  
         } finally {  
             try {  
                 if (bufferedReader != null) {  
                     bufferedReader.close();  
                 }  
             } catch (IOException e1) {  
                 e1.printStackTrace();  
             }  
             bufferedReader = null;  
             process = null;  
         }  
         return macList;  
     }  
      
    /** 
    * @MethodName: getWindow7MACAddress  
    * @description : 获取widnow7网卡的mac地址 
    * @author：hlw 
    * @date： 2013-5-5 下午04:46:56 
    * @param execStr 
    * @return String 
    */  
    public static String getWindow7MACAddress() {  
        //获取本地IP对象  
        InetAddress ia = null;  
        try {  
            ia = InetAddress.getLocalHost();  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        }  
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
        byte[] mac = null;  
        try {  
            mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();  
        } catch (SocketException e) {  
            e.printStackTrace();  
        }  
        //下面代码是把mac地址拼装成String  
        StringBuffer sb = new StringBuffer();   
        for(int i=0;i<mac.length;i++){  
            if(i!=0){  
                sb.append("-");  
            }  
            //mac[i] & 0xFF 是为了把byte转化为正整数  
            String s = Integer.toHexString(mac[i] & 0xFF);  
            sb.append(s.length()==1?0+s:s);  
        }  
        //把字符串所有小写字母改为大写成为正规的mac地址并返回  
        return sb.toString().toUpperCase();  
    }  
   

    
    public static List<String> getWindow7MACAddress2 () {  
    	List<String> macList = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces = null;  
   
        try {  
            // 获得所有网络接口  
            netInterfaces = NetworkInterface.getNetworkInterfaces();  
            while (netInterfaces.hasMoreElements()) {  
//                log.info("==============================================");  
                String mac = "";  
                StringBuffer sb = new StringBuffer();  
                NetworkInterface ni = netInterfaces.nextElement();
                if(ni.isVirtual()) {
                	continue;
                }
//                log.info("------MAC DisplayName: " + ni.getDisplayName());  
//                log.info("------MAC Name: " + ni.getName());  
   
                byte[] macs = ni.getHardwareAddress();  
                // 该interface不存在HardwareAddress，继续下一次循环  
                if (macs == null || macs.length == 0 || macs.length != 6) {  
                    continue;  
                }  
   
                for (int i = 0; i < macs.length; i++) {  
                    mac = Integer.toHexString(macs[i] & 0xFF);  
                    if (mac.length() == 1) {  
                        mac = '0' + mac;  
                    }  
                    sb.append(mac + "-");  
                }  
                mac = sb.toString();  
                mac = mac.substring(0, mac.length() - 1).toUpperCase();
                macList.add(mac);
//                log.info(mac);  
//                Enumeration<InetAddress> ips = ni.getInetAddresses();  
//                while (ips.hasMoreElements()) {  
//                    log.info("IP: " + ips.nextElement().getHostAddress());  
//                }  
            }  
        } catch (SocketException e) {  
        	log.info("getWindow7MACAddress2", e);
//            e.printStackTrace();  
        }  
        return macList;
    }  
    
    /** 
    * @MethodName: getLinuxMACAddress  
    * @description : 获取Linux网卡的mac地址 
    * @author：hlw 
    * @date： 2013-5-5 下午04:49:13 
    * @return String 
    */  
    public static List<String> getLinuxMACAddress() {  
        List<String> macList = new ArrayList<>();  
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
        	int eth0Index = 0;
            // linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
//            process = Runtime.getRuntime().exec("ifconfig eth0");  
//            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
//            String line = null;  
//            int index = -1;  
//            while ((line = bufferedReader.readLine()) != null) {  
//                index = line.toLowerCase().indexOf("硬件地址"); 
//                index = (index == -1? line.toLowerCase().indexOf("hwaddr") : index);
//                if (index != -1) {  
//                    // 取出mac地址并去除2边空格  
//                    String mac = line.substring(index + 4).trim(); 
//                    macList.add(mac);
//                    break;  
//                }  
//            }  

        	while(true) {
        		process = Runtime.getRuntime().exec("ifconfig eth" + eth0Index);  
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));  
                String line = null;  
                int index = -1;  
                boolean isHardArrNotExist = true;
                while ((line = bufferedReader.readLine()) != null) {  
                    index = line.toLowerCase().indexOf("硬件地址"); 
                    index = (index == -1? line.toLowerCase().indexOf("hwaddr") : index);
                    if (index != -1) {  
                        // 取出mac地址并去除2边空格  
                        String mac = line.substring(index + 4).trim(); 
                        macList.add(mac);
                        isHardArrNotExist = false;
                        break;  
                    }  
                }
                if(isHardArrNotExist) {
                	break;
                }
                eth0Index++;
            }
        } catch (IOException e) {  
        	log.info("getLinuxMACAddress", e);
//            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            bufferedReader = null;  
            process = null;  
        }  
        return macList;  
    }  
      
    /** 
    * @MethodName: getUnixMACAddress  
    * @description : 获取Unix网卡的mac地址 
    * @author：hlw 
    * @date： 2013-5-5 下午04:49:59 
    * @return String 
    */  
    public static String getUnixMACAddress() {  
        String mac = null;  
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
            // Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息  
            process = Runtime.getRuntime().exec("ifconfig eth0");  
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
            String line = null;  
            int index = -1;  
            while ((line = bufferedReader.readLine()) != null) {  
                // 寻找标示字符串[hwaddr]  
            	index = line.toLowerCase().indexOf("硬件地址"); 
                index = (index == -1? line.toLowerCase().indexOf("hwaddr") : index);  
                if (index != -1) {  
                    // 取出mac地址并去除2边空格  
                    mac = line.substring(index + "hwaddr".length() + 1).trim();  
                    break;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            bufferedReader = null;  
            process = null;  
        }  
  
        return mac;  
    }  
      
    /** 
     * @MethodName: getSystemRoot  
     * @description :jdk1.4获取系统命令路径 ：SystemRoot=C:\WINDOWS 
     * @author：hlw 
     * @date： 2013-5-5 下午04:56:27 
     * @return String 
     */  
     public static String getSystemRoot(){  
         String cmd = null;  
         String os = null;  
         String result = null;  
         String envName = "windir";  
         os = System.getProperty("os.name").toLowerCase();  
         if(os.startsWith("windows")) {  
             cmd = "cmd /c SET";  
         }else {  
             cmd = "env";  
         }  
         try {  
             Process p = Runtime.getRuntime().exec(cmd);  
             InputStreamReader isr = new InputStreamReader(p.getInputStream());  
             BufferedReader commandResult = new BufferedReader(isr);  
             String line = null;  
             while ((line = commandResult.readLine()) != null) {  
                 line=line.toLowerCase();//重要(同一操作系统不同电脑有些返回的是小写,有些是大写.因此需要统一转换成小写)  
                 if (line.indexOf(envName) > -1) {  
                     result =  line.substring(line.indexOf(envName)  
                             + envName.length() + 1);  
                     return result;  
                 }  
             }  
         } catch (Exception e) {  
             log.info("获取系统命令路径 error: " + cmd + ":" + e);  
         }  
         return null;  
     } 
    
    /** 
    * @MethodName: getMacAddress  
    * @description :获取MAC地址 
    * @author：hlw 
    * @date： 2013-5-5 下午04:53:25 
    * @return String 
    */  
    public static List<String> getMacAddress(){  
        String os = getOSName();  
        log.info("-----osName:" + os);
        String execStr = getSystemRoot()+"/system32/ipconfig /all";  
        List<String> macList = null;  
        if(os.startsWith("windows")) {  
//            if(os.equals("windows xp")){//xp  
//            	macList = getWindowXPMACAddress(execStr);    
//            }
//            else if(os.equals("windows 2003")){//2003  
//            	macList = getWindowXPMACAddress(execStr);     
//            }
//            else{//win7  
//                macList = getWindow7MACAddress2();   
//            }  
        	macList = getWindowMACAddress(execStr);
        }
//        else if (os.startsWith("linux")) {  
//        	macList = getLinuxMACAddress();  
//        }
//        else{  
//            mac = getUnixMACAddress();  
//        }  
        else {
        	macList = getLinuxMACAddress();
        }
        return macList;  
    }  
      
    
  
    
    public static void main(String[] args) {
    	System.out.println(getMacAddress());
    }
}  
