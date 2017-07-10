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

import sy.model.Modeeplib;
import sy.service.ModeeplibServiceI;

import com.alibaba.fastjson.JSON;
import com.flywind.app.entities.Deviceattrlib;

@Controller
@RequestMapping("/modeeplibController")
public class ModeeplibController {

	private static final Logger LOGGER = Logger.getLogger(ModeeplibController.class);
	
	private ModeeplibServiceI modeeplibService;

	public ModeeplibServiceI getModeeplibService() {
		return modeeplibService;
	}
	
	@Autowired
	public void setModeeplibService(ModeeplibServiceI modeeplibService) {
		this.modeeplibService = modeeplibService;
	}
	
	@RequestMapping("/add")
	public void add(String json,String entities, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Map<String, Object> params = JSON.parseObject(json,Map.class);
			Map<String, List> param = JSON.parseObject(entities,Map.class);
			Modeeplib modeeplib = JSON.parseObject(json,Modeeplib.class);
			LOGGER.info("add json:"+json);
			modeeplibService.save(modeeplib);
			if(param.get("actCheck").size()>0){
				modeeplibService.saveModeEpactlib(param.get("actCheck"));
			}
			
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("add",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/find")
	public void GetModeNodeLibDataByID(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> params=JSON.parseObject(json,Map.class);
		try {
			Modeeplib modeeplib = JSON.parseObject(json,Modeeplib.class);
			LOGGER.info("find json:"+json);
			String resultJson="";
			String act = "";
				List<Map> t = modeeplibService.findList(modeeplib);
				List<Map> tact = modeeplibService.findActList(modeeplib);
				resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
				act= JSON.toJSONStringWithDateFormat(tact, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"act\":" + act + "})");  
		} catch (Exception e) {
			LOGGER.info("GetModeNodeLibDataByID",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/update")
	public void update(String json,String entities,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Map<String, Object> params = JSON.parseObject(json,Map.class);
			String language = (String)params.get("language");
			Modeeplib modeeplib = JSON.parseObject(json,Modeeplib.class);
			String resultJson = "{\"result\":0}";//成功1 失败0
				Modeeplib t = modeeplibService.get(modeeplib);
				if (t != null) {
					if (modeeplib.getDeviceNameEn()!=null) {
						t.setDeviceNameEn(modeeplib.getDeviceNameEn());
					}
					if (modeeplib.getClusterNameEn()!=null) {
						t.setClusterNameEn(modeeplib.getClusterNameEn());
					}
					if (modeeplib.getDeviceName()!=null) {
						t.setDeviceName(modeeplib.getDeviceName());
					}
					if (modeeplib.getClusterName()!=null) {
						t.setClusterName(modeeplib.getClusterName());
					}
					
					if (modeeplib.getDeviceId()!=null) {
						t.setDeviceId(modeeplib.getDeviceId());
					}
	        		if (modeeplib.getInternelModelId()!=null) {
						t.setInternelModelId(modeeplib.getInternelModelId());
					}
	        		if (modeeplib.getClusterId()!=null) {
						t.setClusterId(modeeplib.getClusterId());
					}
	        		if (modeeplib.getEp()!=null) {
						t.setEp(modeeplib.getEp());
					}
	        		if (modeeplib.getDestType()!=null) {
						t.setDestType(modeeplib.getDestType());
					}
	        		if (modeeplib.getDeviceType()!=null) {
						t.setDeviceType(modeeplib.getDeviceType());
					}
	        		if (modeeplib.getSolidModelID()!=null) {
						t.setSolidModelID(modeeplib.getSolidModelID());
					}
//	        		if (modeeplib.getGroupable()!=0) {
	        			t.setGroupable(modeeplib.getGroupable());
//					}
					modeeplibService.update(t);
					
					Map<String, List> param = JSON.parseObject(entities,Map.class);
					if(!param.isEmpty()||param.size()>0){
						modeeplibService.updateModeEpactlib(param.get("actCheck"),t);
					}
					resultJson = "{\"result\":1}";//成功1 失败0
				}
//			}
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("update",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/delete")
	public void delete(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> params = JSON.parseObject(json,Map.class);
			Modeeplib modeeplib = JSON.parseObject(json,Modeeplib.class);
			modeeplibService.delete(modeeplib);
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/getAttrLib")
	public void getAttrLib(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			List<Deviceattrlib> devlis= modeeplibService.getAttrLib();
			String resultJson =JSON.toJSONString(devlis);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("getAttrLib",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/listAttrLib")
	public void listAttrLib(HttpServletRequest request,Integer page,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			List<Deviceattrlib> devlis= modeeplibService.listAttrLib(page);
			String resultJson =JSON.toJSONString(devlis);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("getAttrLib",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/getcountattr")
	public void getcountattr(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			List<Deviceattrlib> devlis= modeeplibService.listAttrLib(0);
			String resultJson =JSON.toJSONString(devlis);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("getAttrLib",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
}
