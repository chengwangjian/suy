package sy.util;

import java.util.UUID;

/**
 * @author: zhuangxd
 * 时间：2014-7-19 下午1:46:49
 */
public class GUIDUtil {

	public GUIDUtil() {
	}

	/**
	 * 获取GUID(UUID)值
	 * @author: zhuangxd
	 * 时间：2014-7-19 下午2:18:07
	 * @return
	 */
	public static String getGUID() {
		// da3c8af4-1069-47d7-85b4-90b990a085b5 36位
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println(""+uuid.toString()+"");
	}
}
