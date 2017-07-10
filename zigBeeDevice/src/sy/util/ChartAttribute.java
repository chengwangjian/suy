package sy.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备图表属性
 * @author R&D1
 *
 */
public enum ChartAttribute {
	CHART_AIR_TEMP(Constants.CHART_AIR_TEMP, "0402", "0000", new String[][]{
			{"Z711AE3ED","01"},{"Z713DE3ED","01"},{"Z713GCE3ED","01"},{"Z713GE3ED","01"},{"Z713HE3ED","01"},{"Z725AE3R","01"},{"Z725CDE3R","01"},{"Z869E3R","01"},
			{"ZB11C1E3ED","03"},{"Z711E3ED","01"},{"Z713E3ED","01"},{"Z716AE3ED","01"},{"Z716BE3ED","01"},{null,null}}), 
	CHART_AIR_HUMIDITY(Constants.CHART_AIR_HUMIDITY, "0405", "0000", new String[][]{
			{"Z713DE3ED","01"},{"Z713GCE3ED","01"},{"Z713GE3ED","01"},{"Z713HE3ED","01"},{"Z725AE3R","01"},{"Z725CDE3R","01"},{"Z869E3R","01"},{"Z711E3ED","01"},
			{"Z713E3ED","01"},{"Z716AE3ED","01"},{null,null}}), 
	CHART_SUN_ILL(Constants.CHART_SUN_ILL, "0400", "0000", new String[][]{}), 
	CHART_AIR_CO2(Constants.CHART_AIR_CO2, "NA", "NA", new String[][]{}), 
	CHART_SOIL_TEMP(Constants.CHART_SOIL_TEMP, "FE60", "000D", new String[][]{
			{"Z713CKE3ED","01"},{"Z721AE3ED","01"},{"Z721BE3ED","01"}}), 
	CHART_SOIL_HUMIDITY(Constants.CHART_SOIL_HUMIDITY, "FE60", "000E", new String[][]{
			{"Z713CE3ED","01"},{"Z713CKE3ED","01"},{"Z713GCE3ED","02"},{"Z721AE3ED","01"},{"Z721BE3ED","01"},{"Z725CDE3R","02"},{"Z725CE3R","01"}}),
	CHART_SOIL_PH(Constants.CHART_SOIL_PH, "FE60", "0005", new String[][]{}), 
	CHART_SOIL_ELETRIC(Constants.CHART_SOIL_ELETRIC, "FE60", "0006", new String[][]{}), 
	CHART_ULTR(Constants.CHART_ULTR, "FE60", "0000", new String[][]{}), 
	CHART_LEFT(9, "NA", "NA", new String[][]{}),
	CHART_PM25(Constants.CHART_PM25, "FE60","0002", new String[][]{{"ZK6008E3R","01"}}),
	
	CHART_SUN_EXTREMELY(Constants.CHART_SUN_EXTREMELY, "FE60" , "000F", new String[][]{}); ////日照强度
	
	private Integer index;
	private String clusterId;
	private String attributeId;
	private String[][] solidModelIdArr;
	
	private ChartAttribute(Integer index, String clusterId, String attributeId, String[][] solidModelIdArr) {
		this.index = index;
		this.clusterId = clusterId;
		this.attributeId = attributeId;
		this.solidModelIdArr = solidModelIdArr;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	
	public String[][] getSolidModelIdArr() {
		return solidModelIdArr;
	}

	public void setSolidModelIdArr(String[][] solidModelIdArr) {
		this.solidModelIdArr = solidModelIdArr;
	}

	public static Map<String, Object> getValue(Integer index) {
		for(ChartAttribute ca : ChartAttribute.values()) {
			if(ca.getIndex().intValue() == index.intValue()) {
				Map<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("clusterId", ca.getClusterId());
				attrMap.put("attributeId", ca.getAttributeId());
				attrMap.put("solidModelIdArr", ca.getSolidModelIdArr());
				return attrMap;
			}
		}
		return null;
	}
}
