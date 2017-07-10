package zbEnergy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import zbEnergy.service.EnergyServiceI;
import zbHouse.pageModel.General;
import zbHouse.pageModel.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/energyController")
public class EnergyController {

	private static final Logger LOGGER = Logger.getLogger(EnergyController.class);
	private EnergyServiceI energyService;

	public EnergyServiceI getEnergyService() {
		return energyService;
	}
	@Autowired
	public void setEnergyService(EnergyServiceI energyService) {
		this.energyService = energyService;
	}

	@RequestMapping("/add")
	public void showSave(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		General js=JSON.parseObject(json, General.class);
		
		Json j = new Json();
		try {

			j.setResponse_params(energyService.saveOrUpdate(js));
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showSave", e);

		}
		//request.setAttribute("j",j);
			
	}
	
	@RequestMapping("/delete")
	public void showDelete(String json,HttpServletRequest request,HttpServletResponse response) {	

		//request.setAttribute("js",js);			
	       
		Json j = new Json();
		try {			
			//j.setObj(energyService.delete(params));
			//writeJson(j,response);
		
		} catch (Exception e) {
			LOGGER.info("showDelete", e);

		}
		//request.setAttribute("j",j);
		
	}

	@RequestMapping("/find")
	public void showFind(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		General js=JSON.parseObject(json, General.class);	
	       
		Json j = new Json();
		try {
			j.setResponse_params(energyService.find(js));	
			writeJson(j,response);
					
		} catch (Exception e) {
			LOGGER.info("showFind", e);

		}
		//request.setAttribute("j",j);			
			
	}
	
	public void writeJson(Object object,HttpServletResponse response)throws IOException {

		try {	
			String jstr= JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteMapNullValue);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}	
}
