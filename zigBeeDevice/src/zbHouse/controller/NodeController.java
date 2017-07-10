package zbHouse.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zbHouse.model.Node;
import zbHouse.pageModel.Json;
import zbHouse.service.NodeServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/nodeController")
public class NodeController {

	private static final Logger LOGGER = Logger.getLogger(NodeController.class);
	private NodeServiceI nodeService;

	public NodeServiceI getNodeService() {
		return nodeService;
	}

	@Autowired
	public void setNodeService(NodeServiceI nodeService) {
		 this.nodeService =nodeService;
	}

	@RequestMapping("/add")
	public void showSave(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		
		Json j = new Json();
		try {
			Node js=JSON.parseObject(json, Node.class);
			nodeService.saveOrUpdate(js,params);
			params.put("result", 1);
			j.setResponse_params(params);			
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showSave",e);
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
			nodeService.delete(params);
			params.put("result", 1);
			j.setResponse_params(params);		
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showDelete",e);
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
			j.setResponse_params(nodeService.find(params));
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showFind",e);
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
			LOGGER.info("writeJson",e);
		}
	}	
}
