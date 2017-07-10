package sy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.domain.Warntypetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushedNotifications;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.Warnsend;
import sy.service.WarnsendServiceI;

@Component("pushIOS")
public class NotificationEngine{
	private static Logger logger = Logger.getLogger(NotificationEngine.class);
	// 10 个线程在后台发送通知
	public static final int EXCE_THREAD_POOL_SIZE = 15;
	private WarnsendServiceI warnsendService;
	public WarnsendServiceI getWarnsendService() {
		return warnsendService;
	}
	@Autowired
	public void setWarnsendService(WarnsendServiceI warnsendService) {
		this.warnsendService = warnsendService;
	}

	/**IOS消息推送证书*/
	private static String[][] certArrs = null;

	private void shutdownEngine(){
		logger.info("------------shutdownEngine-----------------------------");
		// 关闭 ExecutorService
		if(!exec.isShutdown()){
			exec.shutdown();
		}
	}
	public void init(){
		// 注册 jvm 关闭时操作
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				NotificationEngine.this.shutdownEngine();
			}
		});
	}

	public void destroy(){
		Thread t = new Thread(){
			@Override
			public void run() {
				NotificationEngine.this.shutdownEngine();
			}
		};
		t.start();
	}

	//创建一个指定大小为EXCE_THREAD_POOL_SIZE的线程池
	public ExecutorService exec = Executors.newFixedThreadPool(EXCE_THREAD_POOL_SIZE);


	private void doSendNotificationList(final String message, final int badge, 
			final String sound, final String keystore, final String password, final boolean production, final List<String> tokens){    
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				logger.info("send "+message+" time:"+System.currentTimeMillis());
				//调用APNS jar包中推送消息的方法
				try {
					Push.combined(
							message, //推送的消息体
							badge, //脚标
							sound, //铃声
							keystore, //推送证书
							password, //证书秘钥
							production, //指定是开发证书还是正式证书
							tokens //目标手机的识别码token列表（同时推送给多个目标）
							);
					logger.info("send "+message+" end:"+System.currentTimeMillis());
				} catch (CommunicationException e) {
					logger.error("communication error", e);
				} catch (KeystoreException e) {
					logger.error("cert keystore error", e);
				}
			}
		};
		// 带返回结果发送动作
		/*FutureTask<List<PushedNotification>> future = new FutureTask<List<PushedNotification>>(
				new Callable<List<PushedNotification>>() {
					@Override
					public List<PushedNotification> call() {
						List<PushedNotification> notifications = null;
						try {
							logger.info("send "+message+" time:"+System.currentTimeMillis());
							//调用APNS jar包中推送消息的方法
							notifications = Push.combined(
									message, //推送的消息体
									badge, //脚标
									sound, //铃声
									keystore, //推送证书
									password, //证书秘钥
									production, //指定是开发证书还是正式证书
									tokens //目标手机的识别码token列表（同时推送给多个目标）
									);
						} catch (CommunicationException e) {
							logger.error("communicate with apns server error ", e);
						} catch (KeystoreException e) {
							logger.error("keystore error ", e);
						}
						return notifications;
					}
				});
		logger.info("submit:"+message+"  time:"+System.currentTimeMillis());*/
		// 提交到执行框架执行
		exec.execute(runnable);
		//exec.submit(future);
		//return future;
	}

	public void sendNotificationList(final String message, final int badge, final String sound, final String keystore, final String password, final boolean production, final List<String> tokens){    
		/*FutureTask<List<PushedNotification>> futrue = */doSendNotificationList(message, badge,sound,keystore,password,production,tokens);
		/*try {
			// 阻塞至接收到返回结果。
			List<PushedNotification> list = futrue.get();
			for (PushedNotification notification : list) {
				if (notification.isSuccessful()) {
					 Apple accepted the notification and should deliver it   
					logger.info("Push notification sent successfully to: " + notification.getDevice().getToken() +";end time:"+System.currentTimeMillis());
					 Still need to query the Feedback Service regularly   
				} else {
					 Add code here to remove invalidToken from your database   
					String token = notification.getDevice().getToken();
					logger.info("Push notification sent faild to: "+ notification.getDevice().getToken());
					 Find out more about what the problem was   
					Exception theProblem = notification.getException();
					logger.error("Push faild info: " + theProblem.toString());

					 If the problem was an error-response packet returned by Apple, get it   
					ResponsePacket theErrorResponse = notification.getResponse();
					if (theErrorResponse != null) {
						//若推送的消息的结果返回是无效token，则根据token删除warnSend表中的相关内容
						if(StringUtils.contains(theErrorResponse.getMessage(), "Invalid token")){
							warnsendService.deleteByDeviceToken(token);
						}
						logger.error("Push error info: " + theErrorResponse.getMessage());
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("---- 1 ----", e);
		} catch (ExecutionException e) {
			logger.error("---- 2 ----", e);
		}*/

	}

	
	
	
	public void pushIOSMessage(final List<Warnsend> warnSendList,final Warntypetable warnType,
			final DevicewarnhistoryHouseidYear devWarn){
		//没有目标设备
		if(warnSendList==null){
			logger.info("no target mobil···");
			return;
		}
		//未知告警类型
		if(warnType==null){
			logger.info("unknown warn type···");
			return;
		}
		//告警详情为空
		if(devWarn==null){
			logger.info("no warn message···");
			return;
		}
		//程序启动后的第一次IOS消息推送时，加载推送证书
		if(certArrs == null){
			try {
				certArrs = getCertarrs();
				if(certArrs == null){
					throw new FileNotFoundException("IOS certificate not found exception");
				}
			} catch (Exception e) {
				logger.error("file not found error", e);
				return;
			}
		}
		int certArrsLength = certArrs.length;
		for(int i=0; i < certArrsLength;i++){
			String[] certArr = certArrs[i];
			int num = i;
			/*new Thread(){
				@Override
				public void run(){*/
			try {
				//true:正式证书  false:开发证书
				boolean production = true;
				if(StringUtils.containsIgnoreCase(certArr[1], "develop"))
					production = false;
				//是否推送开发证书
				String isSendDevelop = PropertiesUtil.getProperty("ios.develop.isSend");
				if(!production&&isSendDevelop!=null&&!Boolean.valueOf(isSendDevelop)){
					continue;
				}
				List<String> tokens = new ArrayList<>();
				int badge = 0;
				String message = warnType.getChinesewarnType();
				logger.info("-------------------apple循环推送-------");
				for(Warnsend wSend : warnSendList) {
					if("China".equalsIgnoreCase(wSend.getLanguage()))
						message = warnType.getChinesewarnType();
					else
						message = warnType.getEnglishwarnType();
					Integer footer = wSend.getFooter();
					logger.info("send cert "+ certArr[1] +", token:"+wSend.getDeviceToken()+"; start time:"+System.currentTimeMillis());
					tokens.add(wSend.getDeviceToken());
					if(Integer.parseInt(wSend.getIsonline()) == 0 && num == 0) {
						footer++;
						wSend.setFooter(footer);
						warnsendService.update(wSend);
					}
					else {
						wSend.setFooter(0);
					}
					badge = footer;
					logger.info("add message:"+message+" to pool at time:"+System.currentTimeMillis());
					sendNotificationList(message, badge, "default", certArr[1], certArr[2], production, tokens);
				}
				
			}
			catch (Exception e) {  
				logger.info("pushiphone error", e);
			}
		}
		/*}.start();
		}*/
	}

	/**
	 * 获取文件中的所有IOS推送证书
	 * @return
	 * @throws Exception 异常
	 */
	public String[][] getCertarrs() throws Exception {
		URL url = this.getClass().getResource("/");
		logger.info("root file path:"+url.getPath());
		List<File> fileList = new ArrayList<>();
		File file = new File(url.getFile());
		if(!file.exists()){
			throw new FileNotFoundException("The file named " + url.getFile() + " is not found");
		}
		if(!file.isDirectory()){
			logger.info("file type exception");
			throw new Exception("file type exception");
		}
		File[] files = file.listFiles();
		for(File f:files){
			//判断是否是IOS推送证书的p12类型文件
			if(StringUtils.endsWithIgnoreCase(f.getName(), ".p12")){
				fileList.add(f);
			}
		}
		if(!fileList.isEmpty()){
			certArrs = new String[fileList.size()][3];
			String password = PropertiesUtil.getProperty("ios.certificate.password");
			if(password==null)
				password = "123456";
			for(int i=0;i<certArrs.length;i++){
				certArrs[i] = new String[]{String.valueOf(i+1),fileList.get(i).getPath(),password};
			}
			return certArrs;
		}
		return null;
	}

	/*public static void main(String[] args) throws Exception{
    	String[][] s = new NotificationEngine().getCertarrs();
    	System.out.println("---------------------------");
    	for(int i=0;i<s.length;i++){
    		System.out.println();
    		System.out.println(s[i][0]);
    		System.out.println(s[i][1]);
    		System.out.println(s[i][2]);
    	}
    }*/
	
	
	public void testPushIOSMessage(String message,List<String> tokens){
		//没有目标设备
		if(tokens==null){
			logger.info("no target mobil···");
			return;
		}
		//程序启动后的第一次IOS消息推送时，加载推送证书
		if(certArrs == null){
			try {
				certArrs = getCertarrs();
				if(certArrs == null){
					throw new FileNotFoundException("IOS certificate not found exception");
				}
			} catch (Exception e) {
				logger.error("file not found error", e);
				return;
			}
		}
		int certArrsLength = certArrs.length;
		for(int i=0; i < certArrsLength;i++){
			String[] certArr = certArrs[i];
			int num = i;
			/*new Thread(){
				@Override
				public void run(){*/
			try {
				//true:正式证书  false:开发证书
				boolean production = true;
				if(StringUtils.containsIgnoreCase(certArr[1], "develop"))
					production = false;
				//是否推送开发证书
				/*String isSendDevelop = PropertiesUtil.getProperty("ios.develop.isSend");
				if(!production&&isSendDevelop!=null&&!Boolean.valueOf(isSendDevelop)){
					continue;
				}*/
				//List<String> tokens = new ArrayList<>();
				int badge = 0;
				logger.info("-------------------apple推送-------");
				//tokens.add(token);
				int footer = 0;
				footer++;
				badge = footer;  
				
				sendNotificationList(message, badge, "default", certArr[1], certArr[2], production, tokens);
			}
			catch (Exception e) {  
				logger.info("pushiphone error", e);
			}
		}
		/*}.start();
		}*/
	}
}