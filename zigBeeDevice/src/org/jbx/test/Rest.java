package org.jbx.test;

import org.apache.commons.lang.StringUtils;

import sy.util.AESCodec;

public class Rest {

	public static void main(String[] args) {
		String sign = "D497A0261DA5ECDCC497A8D5FAECF2B83E9B154E9C3C13C9CFBDA944605BA2A103DAD22A694FBAAB9E8CC164F4B1A4459C8EC33B2A5F87ED36A40231B5C9589F";
		String password = "123456";
		byte[] decryptFrom = null;  
		byte[] decryptResult=null;
		String result=null;
		
		while(password.getBytes().length%16!=0)
			password += "0";
		
		try {
//			logger.info(sign);			
			decryptFrom = AESCodec.parseHexStr2Byte(sign);  
			decryptResult = AESCodec.decrypt2(decryptFrom,password);
			
			// 解决中文乱码  不足16位补#
			/*while(content.getBytes().length%16!=0)
			content=content+"#";*/
			result=new String(decryptResult, "utf-8");
//			logger.info("原始结果decrypt:" + result);
			// 移除所有#号
			result=StringUtils.remove(result,'#');
			// 移除所有空格(转发cgi)
			result = StringUtils.remove(result,'\0');
			// 解决中文乱码  不足16位补#
//			result=StringUtils.remove(new String(decryptResult, "utf-8"),'#');
//			logger.info("最终结果decrypt:" + result); 
		} catch (Exception e) {
		
		e.printStackTrace();
			
		}  				
		
      System.out.println(result);
	}

}
