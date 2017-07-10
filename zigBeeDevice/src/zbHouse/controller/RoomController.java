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

import zbHouse.model.Room;
import zbHouse.pageModel.Json;
import zbHouse.service.RoomServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/roomController")
public class RoomController {

	private static final Logger LOGGER = Logger.getLogger(RoomController.class);
	private RoomServiceI roomService;

	public RoomServiceI getRoomService() {
		return roomService;
	}

	@Autowired
	public void setRoomService(RoomServiceI roomService) {
		 this.roomService =roomService;
	}

	@RequestMapping("/add")
	public void showSave(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		
		Json j = new Json();
		try {
			Room js=JSON.parseObject(json, Room.class);
			roomService.saveOrUpdate(js,params);
			params.put("result", 1);
			j.setResponse_params(params);				
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("add", e);
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
			j.setResponse_params(roomService.delete(params));
			params.put("result", 1);
			j.setResponse_params(params);	
			writeJson(j,response);		
		} catch (Exception e) {
			LOGGER.info("delete", e);
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
			j.setResponse_params(roomService.find(params));
			writeJson(j,response);		
		} catch (Exception e) {
			LOGGER.info("find", e);
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
