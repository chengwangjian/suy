package sy.service;

import java.util.List;
import java.util.Map;

public interface SMSServiceI {
	public int addSMSandWE(String houseIeee, String phoneNO, String warnEmail,String behavior);//添加短信、邮箱
	
	public List<Map> findSMS(String houseIeee);
	
	public List<Map> findEmail(String houseIeee); 
	
	public int deleteSW(Map<String, Object> params,String behavior);
}
