/*
 * 创建日期 2005-12-14
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package sy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import sy.util.MacAddressUtil;
import sy.util.PropertiesUtil;

import com.commerceware.cmpp.CMPP;
import com.commerceware.cmpp.cmppe_deliver_result;
import com.commerceware.cmpp.cmppe_result;
import com.commerceware.cmpp.cmppe_submit;
import com.commerceware.cmpp.conn_desc;

/**
 * 单例模式的发送短信
 * @author clore
 * 
 */
public class SendSMS {
	public static SendSMS singleSendSMS =null;
	private static final Logger LOGGER = Logger.getLogger(SendSMS.class);
	private String host = PropertiesUtil.getProperty("SMSHost");
    private int port = Integer.parseInt(PropertiesUtil.getProperty("SMSPort"));
    private String svcType = PropertiesUtil.getProperty("SMSSvcType") + "\0";
    private String loginId = PropertiesUtil.getProperty("SMSLoginId");
    private String icpId = PropertiesUtil.getProperty("SMSIcpId") + "\0";
    private String password = PropertiesUtil.getProperty("SMSPassword");
    private String serviceId = PropertiesUtil.getProperty("SMSServiceId") + "\0";
    private cmppe_result result = null;
    private CMPP cmpp = null;
    private conn_desc connDesc = null;
    private cmppe_submit submitStruct  = null;
    private int i =1;
    
	private SendSMS(){//将连接放在构造方法中，创建实例的时候就连接移动服务器
        // String subNum = "";// "0123";
		cmpp = new CMPP();
		connDesc = new conn_desc();
		submitStruct = new cmppe_submit();
		try {
            cmpp.cmpp_connect_to_ismg(host, port, connDesc);
            LOGGER.info("连接完毕！！！");
            cmpp.cmpp_login(connDesc, loginId, password, (byte) 2, 0x12,
                    (int) System.currentTimeMillis());
            result = cmpp.readResPack(connDesc);
            if (result.stat != CMPP.CMPPE_RSP_SUCCESS) {
            	i = 0;
            	LOGGER.info("登录失败" + result.stat);
                return ;
            } else {
            	 LOGGER.info("登录成功");
            }
        } catch (Exception e) {
        	i = 0;
        	LOGGER.info("登录失败", e);
            e.printStackTrace();
            return;
        }
	}
	
	
	public static SendSMS getSingleSendSMS() {
//		if(singleSendSMS==null){
//			singleSendSMS = new SendSMS();
//		}
		return new SendSMS();
	}
	
	public String sendSMS(String phoneNo, String SMSmsg){
	    // 发送短信
		if(i==0){
			LOGGER.info("生成实例出错");
			return "error";
		}
        String dst = phoneNo+"\0";
        try {
            submitStruct.set_svctype(svcType.getBytes());//设置Submit包的业务类型域
            submitStruct.set_srcaddr(serviceId.getBytes());//设置Submit包的源终端MSISDN号码(没有可以为空)
            submitStruct.set_ducount((byte) 1);//设置Submit包中目的手机的数量（大于0）
            byte[][] dstByte = { { 0 }, { 0 }, { 0 }, { 0 } };
            dstByte[0] = dst.getBytes();
            submitStruct.set_dstaddr(dstByte);//设置提交请求包中目的手机号
            submitStruct.set_msgmode((byte) 1);//设置Submit包的”是否要求返回状态确认报告(0--不需要，1--需要)”域
            submitStruct.set_feetype((byte) 1);//设置Submit包的资费类别域
            // submitStruct.set_infofee(300);
            if(!isMobileNumber(phoneNo))
            	submitStruct.set_feeutype((byte) 2);//也可对联通、电信发送短信
            byte[] message = null;
            if(MacAddressUtil.getOSName().equals("windows"))
            	message = SMSmsg.getBytes();
            else
            	message = SMSmsg.getBytes("gbk");
            submitStruct.set_msg((byte) 15, message.length, message);//
            submitStruct.set_icpid(icpId.getBytes());
            int a  =cmpp.cmpp_submit(connDesc, submitStruct);

            switch (result.pack_id) {
	            case CMPP.CMPPE_NACK_RESP:
	            	 LOGGER.info("Get Nack Pack");
	                break;
	            case CMPP.CMPPE_SUBMIT_RESP:
	            	 LOGGER.info("Get Submit Resp. stat is " + result.stat);
	                break;
	            case CMPP.CMPPE_DELIVER:
	            	 LOGGER.info("Get Deliver");
	            	 cmppe_deliver_result deliverResult = (cmppe_deliver_result) result;
	            	 LOGGER.info("Status Rpt:" + deliverResult.status_rpt);
	            	 LOGGER.info("Src:" + new String(deliverResult.src_addr));
	            	 LOGGER.info("Dst:" + new String(deliverResult.dst_addr));
	            	 LOGGER.info("Message:" + new String(deliverResult.short_msg));
	            	 LOGGER.info("Report:" + deliverResult.status_from_rpt);
	            	 cmpp.cmpp_send_deliver_resp(connDesc, deliverResult.seq, deliverResult.stat);
	                break;
                default:
                	LOGGER.info("退出");
                	break;
            }
        } catch (Exception e) {
        	LOGGER.info("发送异常失败", e);
        	LOGGER.info("重新连接...");
            try {
                cmpp.cmpp_connect_to_ismg(host, port, connDesc);
                LOGGER.info("连接完毕！！！");
                cmpp.cmpp_login(connDesc, loginId, password, (byte) 2, 0x12, (int) System.currentTimeMillis());
                result = cmpp.readResPack(connDesc);

                if (result.stat != CMPP.CMPPE_RSP_SUCCESS) {
                	LOGGER.info("登录失败" + result.stat);
                    return "login error again";
                } else {
                	LOGGER.info("登录成功");
                }
            } catch (Exception e1) {
            	LOGGER.info("登录失败", e1);
                return "login error again";
            }
            
            String s = sendSMS(dst, SMSmsg);
            singleSendSMS = null;
            return s;
        }
        finally{
        	try {
				cmpp.cmpp_logout(connDesc);
				LOGGER.info("退出成功");
			} catch (IOException e) {
				LOGGER.info("退出失败", e);
			}
        	cmpp.cmpp_disconnect_from_ismg(connDesc);
        	LOGGER.info("断开连接");
        }
		return SMSmsg;
	}
	
	//批量发送信息
	public boolean SendMultiSMS(List<Map> messList) {
		if(messList.isEmpty())
			return true;
		try {
			submitStruct.set_svctype(svcType.getBytes());//设置Submit包的业务类型域
            submitStruct.set_srcaddr(serviceId.getBytes());//设置Submit包的源终端MSISDN号码(没有可以为空)
            submitStruct.set_ducount((byte) 1);//设置Submit包中目的手机的数量（大于0）
            submitStruct.set_msgmode((byte) 1);//设置Submit包的”是否要求返回状态确认报告(0--不需要，1--需要)”域
            submitStruct.set_feetype((byte) 1);//设置Submit包的资费类别域
            submitStruct.set_icpid(icpId.getBytes());
			for(Map pMap : messList) {
				try {
					String phoneNo = (String) pMap.get("phoneNo");
					String dst = phoneNo + "\0";
					String SMSmsg = (String) pMap.get("SMSmsg");
		            byte[][] dstByte = { { 0 }, { 0 }, { 0 }, { 0 } };
		            dstByte[0] = dst.getBytes();
		            if(!isMobileNumber(phoneNo))
		            	submitStruct.set_feeutype((byte) 2);//也可对联通、电信发送短信
		            submitStruct.set_dstaddr(dstByte);//设置提交请求包中目的手机号
		            byte[] message = null;
		            if(MacAddressUtil.getOSName().equals("windows"))
		            	message = SMSmsg.getBytes();
		            else
		            	message = SMSmsg.getBytes("gbk");
		            submitStruct.set_msg((byte) 15, message.length, message);
		            int a  =cmpp.cmpp_submit(connDesc, submitStruct);

		            switch (result.pack_id) {
			            case CMPP.CMPPE_NACK_RESP:
			            	 LOGGER.info("Get Nack Pack");
			                break;
			            case CMPP.CMPPE_SUBMIT_RESP:
			            	 LOGGER.info("Get Submit Resp. stat is " + result.stat);
			                break;
			            case CMPP.CMPPE_DELIVER:
			            	 LOGGER.info("Get Deliver");
			            	 cmppe_deliver_result deliverResult = (cmppe_deliver_result) result;
			            	 LOGGER.info("Status Rpt:" + deliverResult.status_rpt);
			            	 LOGGER.info("Src:" + new String(deliverResult.src_addr));
			            	 LOGGER.info("Dst:" + new String(deliverResult.dst_addr));
			            	 LOGGER.info("Message:" + new String(deliverResult.short_msg));
			            	 LOGGER.info("Report:" + deliverResult.status_from_rpt);
			                cmpp.cmpp_send_deliver_resp(connDesc, deliverResult.seq, deliverResult.stat);
			                break;
		                default:
		                	LOGGER.info("退出");
		                	break;
	            	}
	            	pMap.put("sendResult", 1);
				}
				catch (Exception e) {
		        	LOGGER.info("发送异常失败", e);
		        	pMap.put("sendResult", 0);
		        }
			}
		} catch(Exception e) {
			LOGGER.info("SendMultiSMS fail", e);
			return false;
		}
        finally{
        	try {
				cmpp.cmpp_logout(connDesc);
				LOGGER.info("退出成功");
			} catch (IOException e) {
				LOGGER.info("退出失败", e);
			}
        	cmpp.cmpp_disconnect_from_ismg(connDesc);
        	LOGGER.info("断开连接");
        }
		return true;
	}
	
	//批量发送信息
	public boolean SendMultiSMS(String smsMessage, List<String> mobileList) {
		if(mobileList.isEmpty())
			return true;
		try {
			submitStruct.set_svctype(svcType.getBytes());//设置Submit包的业务类型域
            submitStruct.set_srcaddr(serviceId.getBytes());//设置Submit包的源终端MSISDN号码(没有可以为空)
            submitStruct.set_ducount((byte) 1);//设置Submit包中目的手机的数量（大于0）
            submitStruct.set_msgmode((byte) 1);//设置Submit包的”是否要求返回状态确认报告(0--不需要，1--需要)”域
            submitStruct.set_feetype((byte) 1);//设置Submit包的资费类别域
            submitStruct.set_icpid(icpId.getBytes());
            byte[] message = null;
            if(MacAddressUtil.getOSName().equals("windows"))
            	message = smsMessage.getBytes();
            else
            	message = smsMessage.getBytes("gbk");
            submitStruct.set_msg((byte) 15, message.length, message);
			for(String phoneNo : mobileList) {
				try {
					String dst = phoneNo + "\0";
					if(!isMobileNumber(phoneNo))
		            	submitStruct.set_feeutype((byte) 2);//也可对联通、电信发送短信
		            byte[][] dstByte = { { 0 }, { 0 }, { 0 }, { 0 } };
		            dstByte[0] = dst.getBytes();
		            submitStruct.set_dstaddr(dstByte);//设置提交请求包中目的手机号
		            int a  =cmpp.cmpp_submit(connDesc, submitStruct);

		            switch (result.pack_id) {
			            case CMPP.CMPPE_NACK_RESP:
			            	 LOGGER.info("Get Nack Pack");
			                break;
			            case CMPP.CMPPE_SUBMIT_RESP:
			            	 LOGGER.info("Get Submit Resp. stat is " + result.stat);
			                break;
			            case CMPP.CMPPE_DELIVER:
			            	 LOGGER.info("Get Deliver");
			            	 cmppe_deliver_result deliverResult = (cmppe_deliver_result) result;
			            	 LOGGER.info("Status Rpt:" + deliverResult.status_rpt);
			            	 LOGGER.info("Src:" + new String(deliverResult.src_addr));
			            	 LOGGER.info("Dst:" + new String(deliverResult.dst_addr));
			            	 LOGGER.info("Message:" + new String(deliverResult.short_msg));
			            	 LOGGER.info("Report:" + deliverResult.status_from_rpt);
			                cmpp.cmpp_send_deliver_resp(connDesc, deliverResult.seq, deliverResult.stat);
			                break;
		                default:
		                	LOGGER.info("退出");
		                	break;
	            	}
				}
				catch (Exception e) {
		        	LOGGER.info("发送异常失败", e);
		        }
			}
		} catch(Exception e) {
			LOGGER.info("SendMultiSMS fail", e);
			return false;
		}
        finally {
        	try {
				cmpp.cmpp_logout(connDesc);
				LOGGER.info("退出成功");
			} catch (IOException e) {
				LOGGER.info("退出失败", e);
			}
        	cmpp.cmpp_disconnect_from_ismg(connDesc);
        	LOGGER.info("断开连接");
        }
		return true;
	}
	
	private boolean isMobileNumber(String number) {
		if(
//			移动
			number.startsWith("134")
			|| number.startsWith("135")
			|| number.startsWith("136")
			|| number.startsWith("137")
			|| number.startsWith("138")
			|| number.startsWith("139")
			|| number.startsWith("147")
			|| number.startsWith("150")
			|| number.startsWith("151")
			|| number.startsWith("152")
			|| number.startsWith("157")
			|| number.startsWith("158")
			|| number.startsWith("159")
			|| number.startsWith("182")
			|| number.startsWith("187")
			|| number.startsWith("188"))
			return true;
		return false;
	}
	
//	public static void main(String[] args) {
//		System.out.println(SendSMS.getSingleSendSMS().isMobileNumber("1890000"));
//	}
}

