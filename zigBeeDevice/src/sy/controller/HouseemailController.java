package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sy.service.HouseServiceI;
import sy.service.HouseemailServiceI;
import sy.model.House;
import sy.model.Houseemail;

@Controller
@RequestMapping("/houseemailController")
public class HouseemailController {
	
	public HouseemailServiceI houseemailService;
	
	public HouseServiceI houseService;
	
	private static final Logger LOGGER = Logger.getLogger(HouseemailController.class);

	public HouseemailServiceI getHouseemailService() {
		return houseemailService;
	}
	@Autowired
	public void setHouseemailService(HouseemailServiceI houseemailService) {
		this.houseemailService = houseemailService;
	}
	
	public HouseServiceI getHouseService() {
		return houseService;
	}
	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}
	@RequestMapping("/add")
	public void add(String json,HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Houseemail houseemail = JSON.parseObject(json,Houseemail.class);
			House house = JSON.parseObject(json,House.class); 
			LOGGER.info("add json:"+json);
			Houseemail t = houseemailService.get(houseemail);
			if (StringUtils.isEmpty(houseemail.getHouseIeee())|| houseemail.getHouseIeee().length()<16) {
				String resultJson = "{\"result\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
    			return;
			}
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t == null) {
				houseemailService.save(houseemail);
				House h = houseService.get(house);
				if (h == null) {
					if (house.getNetworkAddress() == null) {
	        			house.setNetworkAddress("192.168.99.255"); // 默认值
	        			house.setDescription("192.168.99.255"); // 默认值
	        		}
					houseService.save(house);
				}
				resultJson = "{\"result\":1}";//成功1 失败0
			}
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch(Exception e) {
			LOGGER.info("add",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		}
	}
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Houseemail houseemail = JSON.parseObject(json,Houseemail.class);
			LOGGER.info("find json:"+json);
			List<Houseemail> t = houseemailService.findList(houseemail);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch(Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		}
	}
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Houseemail houseemail = JSON.parseObject(json,Houseemail.class);
			House house = JSON.parseObject(json,House.class); 
			LOGGER.info("update json:"+json);
			Houseemail t = houseemailService.get(houseemail);
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t != null) {
				if (houseemail.getHouseIeee()!=null) {
					t.setHouseIeee(houseemail.getHouseIeee());
				}
				if (houseemail.getEmail()!=null) {
					t.setEmail(houseemail.getEmail());
				}
				houseemailService.update(houseemail);
				
				House t2 = houseService.get(house);
				if (t2!=null) {
					if (json !=null && !json.contains("regionCode")) {
	            		house.setRegionCode(null);
	            	}
	            	if (json !=null && !json.contains("isonline")) {
	            		house.setIsonline(null);
	            	}
	        		if (house.getLatitude() != null) {
	        			t2.setLatitude(house.getLatitude());
	        		}
	        		if (house.getLongitude() != null) {
	        			t2.setLongitude(house.getLongitude());
	        		}
	        		if (house.getName() != null) {
	        			t2.setName(house.getName());
	        		}
	        		if(house.getEnableFlag()!=null){
	        			t2.setEnableFlag(house.getEnableFlag());
	        		}if (house.getDescription() != null) {
	        			t2.setDescription(house.getDescription());
	        		}
	        		if (house.getIsonline() != null) {
	        			t2.setIsonline(house.getIsonline());
	        		}
	        		if (house.getRegionCode() != null) {
	        			t2.setRegionCode(house.getRegionCode());
	        		}
	        		if (house.getNetworkAddress() != null) {
	        			t2.setNetworkAddress(house.getNetworkAddress());
	        		}
	        		if (house.getPort() != null) {
	        			t2.setPort(house.getPort());
	        		}
	        		if (house.getClientCode() != null) {
	        			t2.setClientCode(house.getClientCode());
	        		}
	        		houseService.update(house);
				}
				resultJson = "{\"result\":1}";//成功1 失败0
			}
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch(Exception e) {
			LOGGER.info("update",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		}
	}
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Houseemail houseemail = JSON.parseObject(json,Houseemail.class);
			LOGGER.info("delete json:"+json);
			Houseemail t = houseemailService.get(houseemail);
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t != null) {
				if (houseemail.getHouseIeee()!=null) {
					houseemailService.delete(houseemail);
					resultJson = "{\"result\":1}";//成功1 失败0
				}
			}
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch(Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		}
	}
}
