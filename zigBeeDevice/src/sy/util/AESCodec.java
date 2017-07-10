package sy.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCodec {
	public static String IV = "AAAAAAAAAAAAAAAA";
	public static String content = "rid=-1callback=android"; /*"test text 123\0\0\0";  Note null padding */
//	AESCodec.password="1234567887654321";//路由器向云端post推送数据，请求.do密钥
	//AESCodec.password="00137A000000DBC3";//android get请求转发cgi密钥
	public static String password = "000000DBB5NETVOX"; /*"0123456789abcdef";*/

	public static void main(String[] args) {
		try {
			
			/*content = "json=%7B%22houseIEEE%22%3A%2200137A0000007785%22%7Dcallback=android";
			password = "0000007785NETVOX";*/
			/*content = "json=%7B%22houseIEEE%22%3A%2200137A0000007785%22%2C%22name%22%3A%22%D3%C3%BB%A72%22%7Dcallback=android";
			password = "0000007785NETVOX";*/
			/*content = "json=%7B%22houseIEEE%22%3A%2200137A0000007785%22%2C%22name%22%3A%22%D3%C3%BB%A7%C0%EE%22%2C%22longitude%22%3A%2222%22%2C%22latitude%22%3A%2233%22%2C%22networkAddress%22%3A%22192.168.1.123%22%2C%22port%22%3A%228081%22%2C%22description%22%3A%22192.168.1.123%22%7Dcallback=android";
			password = "0000007785NETVOX";*/
//			A7D080A2863A83FF7E15FE18AB57D754C3B01908BDC9E236977273F54BA284681860D5F04F2973F685B965EE3B65D99FBB29C6E9E1BA4BC2CA81604B6353EFBDF35C07C1A48E570B2356DB190587461937D1D9667DD9141262A3C4C34D35DBF7ADFDED7103D37E4258F583717A099A0BC7526E1BE7F15D4BBEFA470A8E345226A8C03163C69FE1717A36141B73EEF237
			/*content = "json={\"houseIEEE\":    \"00137A0000007785\",\"roomId\":   \"8\",\"roomName\":     \"公共浴室\",\"roomPic\": \"r_pbath.png\"}&callback=wifiRoute";
			password = "0000007785NETVOX";*/
			/*content = "json={"+
	"\"houseIEEE\":	\"00137A0000007785\","+
	"\"roomId\":	\"8\","+
	"\"roomName\":	\"公共浴室\","+
	"\"roomPic\":	\"r_pbath.png\""+
"}callback=wifiRoute";
			password = "0000007785NETVOX";*/
			/*content = "5717188";
			password = "0000007785NETVOX";
			*/
			
			content = "context=xmldown/d:\\modexml\\mode-userid为4-orderid为514-pymtest-2014-07-29-14-33-16.xml;d:\\modexml\\modeNode-userid为4-orderid为514-pymtest-2014-07-29-14-33-16.xml;d:\\modexml\\mode-userid为4-orderid为514-pymtest-2014-07-29-14-33-16.nddusername=00137a0000012e2b";
			password = "0000012e2bNETVOX";
			
			while(content.getBytes().length%16!=0)
				content=content+"#";
			//加密   
			System.out.println("加密前：" + content);  
			byte[] encryptResult = encrypt2(content, password);  
			String encryptResultStr = parseByte2HexStr(encryptResult);  
			System.out.println("加密后：" + encryptResultStr);  
			//解密
//			encryptResultStr = "B00C009CD1B69E7B2CED93100D1ED0C62048D313688089B7E52F5432CD2279699FB7BD9BD6672374E3A5560290A3F42C44D225844B0F6E7621410A557C165EA4BF67DFC969137C969C5E50A5E5F70CA45C2671B46D946CEB5094CBCAB7296F4CADAADD133D6B62B339E506D52B2131DBF39982A7650D499E7AC4B22C2F99E32014795167780F788FB2965F9CD9CCC77D";
//			encryptResultStr = "F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32E7C1CA30B5E1AA4FFC0832A88EA4F4B568EE4CA4A4E1372CAC7D861214F98C63";
			byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
			byte[] decryptResult = decrypt2(decryptFrom,password);  
			System.out.println("解密后：" + new String(decryptResult));  	
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] encrypt(String content, String password)
			throws Exception {		
		
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(password.getBytes()));  
        SecretKey secretKey = kgen.generateKey();  
        byte[] enCodeFormat = secretKey.getEncoded();  
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器   
        byte[] byteContent = content.getBytes("utf-8");  
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
        byte[] result = cipher.doFinal(byteContent);  
        return result; // 加密 			
		
	}

	public static byte[] decrypt(byte[] content, String password)
			throws Exception {
		
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(password.getBytes()));  
        SecretKey secretKey = kgen.generateKey();  
        byte[] enCodeFormat = secretKey.getEncoded();  
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器   
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
        byte[] result = cipher.doFinal(content);  
        return result; // 加密   
       
	}

	 public static byte[] encrypt2(String content, String password)  
			 throws Exception {	
         SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
         Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
         byte[] byteContent = content.getBytes("utf-8");  
         cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
         byte[] result = cipher.doFinal(byteContent);  
         return result; // 加密   
	 }
	
	 public static byte[] encryptGBK(String content, String password)  
			 throws Exception {	
         SecretKeySpec key = new SecretKeySpec(password.getBytes("GBK"), "AES");  
         Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
         byte[] byteContent = content.getBytes("GBK");  
         cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
         byte[] result = cipher.doFinal(byteContent);  
         return result; // 加密   
	 }
	 
	 public static byte[] decrypt2(byte[] content, String password)
			 throws Exception {	
         SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
         Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
         cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
         byte[] result = cipher.doFinal(content);  
         return result; // 加密           
	 }
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	public static String parseByte2HexStr(byte buf[]) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	}  	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public static byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}  
	
	public static void printByte(byte[] cipher){
	    for (int i=0; i<cipher.length; i++)
	        System.out.print(new Integer(cipher[i])+" ");
	    System.out.println("");
	}
}
