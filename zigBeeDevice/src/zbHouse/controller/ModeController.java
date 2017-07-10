package zbHouse.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zbHouse.model.Mode;
import zbHouse.pageModel.Json;
import zbHouse.service.ModeServiceI;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/modeController")
public class ModeController {

	private ModeServiceI modeService;
	
	private static final Logger LOGGER = Logger.getLogger(ModeController.class);  

	public ModeServiceI getModeService() {
		return modeService;
	}

	@Autowired
	public void setModeService(ModeServiceI modeService) {
		 this.modeService =modeService;
	}

	@RequestMapping("/add")
	public void showSave(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		// http://192.168.1.123:8080/zigBeeDevice/modeController/add.do?callback=android&sign=xx&encodemethod=NONE&houseIeeeSecret=00137A000000DBB5&json={"description":"aaa如果25%明天","houseIeee":"00137A000000DBB5","modeId":1111,"modeName":"aaa","picName":"aaa","roomId":111}
		// get请求，先gbk编码可以，中文不会乱码，"description":"aaa如果25%明天"中的25%不会乱码。
		LOGGER.info("json mode2=" + json);
		LOGGER.info("json mode=" + request.getParameter("json"));
		/*try {
			
			System.out.println("json mode=" + URLDecoder.decode(json,"utf-8"));
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		Json j = new Json();
		if (json == null) {
			params.put("result", 1);
			j.setResponse_params(params);
			writeJson(j,response);	
			return;
		}
		/*if (json.contains("%") && !json.contains("%22%3A%22")) {
			json = EncodeUtils.encodeStr(json,"gbk");
			logger.info("mode json==" + json);
		}*/
		try {
			Mode js=JSON.parseObject(json, Mode.class);
			if (StringUtils.isBlank(js.getHouseIeee())) {
				params.put("result", 1);
				j.setResponse_params(params);
				writeJson(j,response);	
				return;
			}
			modeService.saveOrUpdate(js,params);
			params.put("result", 1);
			j.setResponse_params(params);		
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showSave", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);	

		}
		//request.setAttribute("j",j);
			
	}
	
	@RequestMapping("/delete")
	public void showDelete(String json,HttpServletRequest request,HttpServletResponse response) {	

		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
	       
		Json j = new Json();
		try {
		    params = (Map<String,Object>)JSON.parse(json);
			modeService.delete(params);
			params.put("result", 1);
			j.setResponse_params(params);		
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showDelete", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);
			
		}
		//request.setAttribute("j",j);
		
	}
	
	@RequestMapping("/find")
	public void showFind(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
	       
		Json j = new Json();
		try {
		    params = (Map<String,Object>)JSON.parse(json); 
			j.setResponse_params(modeService.find(params));
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showFind", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);

		}
		//request.setAttribute("j",j);	
				
	}
	public void writeJson(Object object,HttpServletResponse response) {

		try {
			String jstr= JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}
}
