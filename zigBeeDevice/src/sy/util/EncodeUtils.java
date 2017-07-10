package sy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public abstract class EncodeUtils {
	private static final Logger log = Logger.getLogger(EncodeUtils.class);
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    public EncodeUtils() {
    }

    public static String urlEncode(String input) {
        return urlEncode(input, "UTF-8");
    }

    public static String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    public static String urlDecode(String input) {
        return urlDecode(input, "UTF-8");
    }

    public static String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }
    
    public static String convertEncoding(String str, String destEncoding, boolean isRead) {
    	
    	// try{
    				// String encoding=request.getCharacterEncoding();
    				// returnStr=new String(returnStr.getBytes("ISO-8859-1"),"GBK");
    				// } catch(UnsupportedEncodingException ex){
    				// ex.printStackTrace();
    				// return "";
    				// }
    	
        String encoding = System.getProperty("file.encoding");
        if (encoding.equals(destEncoding))
            return str;
        if (isRead)
            try {
                return new String(str.getBytes(encoding), destEncoding);
            }
            catch (UnsupportedEncodingException ex) {
                return str;
            }
        try {
            return new String(str.getBytes(destEncoding), encoding);
        }
        catch (UnsupportedEncodingException ex) {
            return str;
        }
    }
    
    /**
	 * 中文字符转码
	 * @param inStr
	 * @return
	 * @throws UnsupportedEncodingException 
	 */	
	public static String encodeStr(String inStr, String firstCharsetName, String endCharsetName) {
		
		try {
//			return new String(inStr.getBytes("iso-8859-1"),"UTF-8");
			return new String(inStr.getBytes(firstCharsetName),endCharsetName);			
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("encodeStr", e);
			return inStr;
		}		
	}
	
	/**
	 * 中文字符转码
	 * @param inStr
	 * @return
	 * @throws UnsupportedEncodingException 
	 */	
	public static String encodeStr(String inStr, String charsetName) {
		
		try {
//			return new String(inStr.getBytes("iso-8859-1"),"UTF-8");
			return new String(inStr.getBytes("iso-8859-1"),charsetName);
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("encodeStr", e);
			return inStr;
		}		
	}

    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
}
