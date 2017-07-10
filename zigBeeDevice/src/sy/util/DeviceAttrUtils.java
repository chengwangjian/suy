package sy.util;

public class DeviceAttrUtils {
	
	/**
	 * 判断设备属性是空气湿度还是土壤湿度
	 * @param solidModelId 
	 * @param isSingleEp 是否是单EP
	 * @return
	 */
	public static Boolean isSoilHum(String solidModelId,boolean isSingleEp){
		String[] solidModelIdArr = null;
		if(isSingleEp)
			solidModelIdArr = new String[]{"Z713CE3ED","Z713CKE3ED","Z721AE3ED","Z721BE3ED","Z725CE3R"};
		else
			solidModelIdArr = new String[]{"Z713GCE3ED","Z725CDE3R"};

		for(String str:solidModelIdArr){
			if(str.equalsIgnoreCase(solidModelId))
				return true;
		}
		return false;
	}
}
