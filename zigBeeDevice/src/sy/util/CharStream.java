package sy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;

/**
 * @author lamp
 *
 */
public class CharStream {
	private static final Logger log = Logger.getLogger(CharStream.class);
	/**
	 * �ַ��������Writer
	 */
	public static void writer(String filename){
		try {
			File file = new File(filename);
			OutputStream out = new FileOutputStream(file,true);
//			Writer out  = new FileWriter(file);
			out.write(xmlvalue,0,(int)len);
			out.flush();
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
			log.info("writer", e);
		}
	}
	
	/**
	 * �ַ�������
	 */
	public static void reader(String filename){
		File file = new File(filename);
		len=file.length();
		try {
			InputStream in = new FileInputStream(file);
//			Reader reader = new FileReader(file);
			StringBuffer sb = new StringBuffer();
			xmlvalue = new byte[(int)len];
			len=in.read(xmlvalue);
//			char[] cs = new char[10];
//			while((len=reader.read(cs))!=-1){
//				sb.append(new String(cs,0,len));
//			}
//			sb.append(new String(xmlvalue));
//			System.out.println(sb.toString());			
			in.close();
		} catch (IOException e) {
//			e.printStackTrace();
			log.info("reader", e);
		}
	}
	public static long len=0;
	public static byte[] xmlvalue =null;
	public static void EncryptionXmlCheck(long len,byte[] xmlvalue){
		StringBuffer sb = new StringBuffer();
		byte exdata[] = {0x4E,0x65,0x54,0x76,0x4F,0x78};
		int k = 0;
		for ( k = 0; k < len; k ++)
		{
			xmlvalue[k] = (byte) (xmlvalue[k]^exdata[k%6]);
		}
//		sb.append(new String(xmlvalue));
//		System.out.println(sb.toString());			
	}
	
	public static void main(String[] args) {
		//reader("D:\\mode-userid为1-orderid为4-smarthome7-2014-01-10-19-05-15");
		reader("D:\\三房一厅标准情景模式mode");
		EncryptionXmlCheck(len,xmlvalue);
		System.out.println("len----"+len);
		//writer("D:\\mode-userid为1-orderid为4-smarthome7-2014-01-10-19-05-15.xml");
		writer("D:\\三房一厅标准情景模式mode.xml");
	}

}
