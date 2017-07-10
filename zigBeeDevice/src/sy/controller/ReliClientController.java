package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.ReliClient;
import sy.service.RegionServiceI;
import sy.service.ReliClientServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/reliClientController")
public class ReliClientController {
	private ReliClientServiceI reliClientService;
	private RegionServiceI regionService;
	
	private static final Logger LOGGER = Logger.getLogger(HouseController.class);

	public ReliClientServiceI getReliClientService() {
		return reliClientService;
	}
	@Autowired
	public void setReliClientService(ReliClientServiceI reliClientService) {
		this.reliClientService = reliClientService;
	}
	public RegionServiceI getRegionService() {
		return regionService;
	}
	@Autowired
	public void setRegionService(RegionServiceI regionService) {
		this.regionService = regionService;
	}
	/**
	 * 获取所有地区的列表
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getRegisonList")
	public void getRegionList(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("application/json;charset=GBK");
		PrintWriter out = response.getWriter();
		List<Map> regionList = regionService.findRegionList(null);
		String resultJson = null;
		try{
			if(regionList!=null){
				String regionStr = JSON.toJSONStringWithDateFormat(regionList, "yyyy-MM-dd hh:mm:ss", SerializerFeature.WriteMapNullValue);
				
				resultJson = "{\"result\":1,\"regionStr\":"+regionStr+"}";//1：表示查询成功
			}else{
				resultJson = "{\"result\",0}";//0:表示查询成功，但表中无数据
			}
			out.print("{\"request_id\":1234,\"response_param\":"+resultJson+"}");
		}catch(Exception e){
			LOGGER.info("getRegion",e);
			resultJson = "{\"result\",-1}";//-1:表示查询失败，抛出异常
			out.print("{\"request_id\":1234,\"response_param\":"+resultJson+"}");
		}
	}
	
	
	/**
	 * 添加客户代码
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addReliClient")
	public void add(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("application/json;charset=GB2312");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = null;
		int result;
		try{
			ReliClient reliClient = JSON.parseObject(json,ReliClient.class);
			System.out.println(reliClient.getClientCode());
			if(reliClientService.findList(reliClient).size()>0){
				resultJson = "{\"result\":-1}";//-1表示要添加的用户代码已经存在
			}else{
				result = reliClientService.add(reliClient);
				resultJson = "{\"result\":1}";// result>0 表示添加成功
			}
			out.print(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}catch(Exception e){
			LOGGER.info("add",e);
			resultJson = "{\"result\":0}";//表示数据库中没有该数据，但添加失败
			out.print(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}
	}
	/**
	 * 查询客户代码
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findReliClient")
	public void find(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("application/json;charset=GBK");
		PrintWriter out = response.getWriter();
		String resultJson = null;
		try{
			ReliClient reliClient = JSON.parseObject(json,ReliClient.class);
			List<Map> list = reliClientService.findReliClient(reliClient);
			 if(list!=null){
				 String reliClientStr = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd hh:mm:ss", SerializerFeature.WriteMapNullValue);
				 resultJson = "{\"result\":1,\"reliClientStr\":"+reliClientStr+"}";//查询成功
			 }else{
				 resultJson = "{\"result\":0,\"reliClientStr\":[]}";//0 表示查询成功，但没有相应的数据
			 }
			 out.print("{\"request_id\":1234,\"response_param\":"+resultJson+"}");
		}catch(Exception e){
			LOGGER.info("find",e);
			resultJson = "{\"result\":-1}";
			out.print("{\"request_id\":1234,\"response_param\":"+resultJson+"}");
		}
	}
	/**
	 * 删除客户代码
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/deleteReliClient")
	public void delete(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		PrintWriter out = response.getWriter();
		String resultJson = null;
		int result = 0;
		ReliClient reliClient = JSON.parseObject(json, ReliClient.class);
		String callback = request.getParameter("callback");
		try{
			result = reliClientService.delete(reliClient);
			resultJson = "{\"result\":"+result+"}";
			out.println(callback + "({\"request_id\": 1234, \"response_param\":" + resultJson + "})"); 
		}catch(Exception e){
			LOGGER.info("delete", e);
			resultJson = "{\"result\":-1}";
			out.println(callback + "({\"request_id\": 1234, \"response_param\":" + resultJson + "})"); 
		}
	}
	/**
	 * 更新客户代码
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateReliClient")
	public void update(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("application/json;charset=GBK");
		PrintWriter out = response.getWriter();
		String resultJson = null;
		String callback = request.getParameter("callback");
		int result;
		try{
			ReliClient reliClient  = JSON.parseObject(json, ReliClient.class);
			ReliClient oldReliClient = reliClientService.find(reliClient);
			//进行唯一性验证
			if(reliClientService.findList(new ReliClient(reliClient.getClientCode(),reliClient.getRegion())).size()==0
					||(oldReliClient!=null&&reliClient.getClientCode().equals(oldReliClient.getClientCode()))){
				result = reliClientService.update(reliClient);
				resultJson = "{\"result\":"+result+"}";//修改成功
			}else{
				resultJson = "{\"result\":-1}";//-1表示新输入的客户代码已经存在
			}
			out.print(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}catch(Exception e){
			LOGGER.info("update",e);
			resultJson = "{\"result\":0}";//0表示用户输入的客户代码具有唯一性，但修改失败（抛出异常）
			out.print(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}
	}
	@RequestMapping("/getCount")
	public void getCount(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		ReliClient reliClient = JSON.parseObject(json, ReliClient.class);
		String resultJson = null;
		int totle = 0;
		try{
			totle = reliClientService.getCount(reliClient);
			if(totle>0){
				resultJson = "{\"result\":1,\"totle\":"+totle+"}";//查询成功成功
			}else{
				resultJson = "{\"result\":0}";
			}
			out.println(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}catch(Exception e){
			LOGGER.info("getCount", e);
			resultJson = "{\"result\":-1}";//查询失败
			out.println(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}
	}
	@RequestMapping("/findClientList")
	public void findClientList(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	//	response.setContentType("text/html,charset=utf-8");
		PrintWriter out = response.getWriter();
		String resultJson = null;
		
		ReliClient reliClient = JSON.parseObject(json,ReliClient.class);
		
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
		String callback = request.getParameter("callback");
		
		try{
			List<Map> t = reliClientService.getClientList(reliClient, startRow, pageSize, orderBy);
			if(t.size()>0){
				String clientStr = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd hh:mm:ss", SerializerFeature.WriteMapNullValue);
				resultJson = "{\"result\":1,\"clientStr\":"+clientStr+"}";//查询成功
			}else{
				resultJson = "{\"result\":0}";//查无数据
			}			
			out.println(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}catch(Exception e){
			LOGGER.info("findClientList",e);
			resultJson = "{\"result\":-1}";//查询失败
			out.println(callback+"({\"request_id\":1234,\"response_param\":"+resultJson+"})");
		}
	}
}
