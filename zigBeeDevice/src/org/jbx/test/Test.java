package org.jbx.test;





import sy.util.AESCodec;

public class Test {

	public static void main(String[] args) {
		
     
	//String content ="user_name=zengzxjson={\"houseIeee\":\"00137A0000000001\",\"note\":\"367nihao\"}";
    String content ="user_name=zengzxjson={\"houseIeee\":\"00137a0000020494\",\"beginTime\":\"2017-06-4 00:00:00\",\"endTime\":\"2017-07-2 00:00:00\"}";
	String password = "123456";
	//String content = "user_name=zengzxjson={\"houseIeee\":\"00137a0000020494\",\"handletime\":\"null\",\"note\":\"中华\",\"warnMsgId\":\"[1,2]\"}";
		String encryptResultStr = "";
		while(password.getBytes().length%16!=0)
			password += "0";
		System.out.println(password);
		//加密   
		//logger.info("加密前：" + content);  
		try {
			while(password.getBytes().length % 16!=0)
				password=password+"0";
			while(content.getBytes().length % 16!=0)
				content=content+"#";
			byte[] encryptResult = AESCodec.encrypt2(content, password);  
			encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
			
			//logger.info("加密后：" + encryptResultStr); 
		} catch (Exception e) {
			//logger.info("encrypt exception", e);
		}
		
		System.out.println(encryptResultStr);
	}
	
}

