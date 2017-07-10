package sy.util;

/**
 * 
 * @author R&D1
 *
 */

public class StringUtil {
	/**
	 * 判断是否包含中文
	 * @param word
	 * @return
	 */
	public static boolean hasChinese(String word) {
		char[] chas = word.toCharArray();
		int size = chas.length;
		for(int i = 0; i < size; i++) {
			char c = chas[i];
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			//  GENERAL_PUNCTUATION 判断中文的“号  
		    //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
		    //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号 
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION 
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				return true;  
			}  
		}
		return false;
	}
	
	/**
	 * JAVA字符串转16进制ascii码
	 * @author: zhuangxd
	 * 时间：2015-2-3 下午1:49:00
	 * @param s
	 * @return
	 */
	public static String toHexAscii(String s) {
		byte[] b = s.getBytes();
	     int[] in = new int[b.length];
	     String str = "";
	     for (int i = 0; i < in.length; i++) {
	         in[i] = b[i]&0xff;
	     }
	     for (int j = 0; j < in.length; j++) {
	    	 str += Integer.toString(in[j], 0x10);
	     }
	     return str;	     
	}
	
	/**
	 * 判断是否数字
	 * @param numberStr
	 * @return
	 */
	public static boolean isNumber(String numberStr) {
		boolean isNumber = true;
		int numLen = numberStr.length();
		for(int i = 0; i < numLen; i++) {
			if(!Character.isDigit(numberStr.charAt(i))) {
				isNumber = false;
				break;
			}
		}
		return isNumber;
	}
}
