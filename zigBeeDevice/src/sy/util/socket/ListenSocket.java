package sy.util.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import sy.model.Operatelog;
import sy.service.OperatelogServiceI;

import com.alibaba.fastjson.JSON;

/**
 * @author: zhuangxd
 * 时间：2013-10-17 下午2:11:16
 */
public class ListenSocket extends Thread {
	private static final int SERVER_PORT = 8088; //端口号
	private int count = 0;//连接客户端数
	private ServerSocket ss = null;//服务端socket
	public static Map clientMap = new HashMap();
	private static final Logger LOGGER = Logger.getLogger(ListenSocket.class);
	
	private OperatelogServiceI operatelogService;
	
	public OperatelogServiceI getOperatelogService() {
		return operatelogService;
	}

	@Autowired
	public void setOperatelogService(OperatelogServiceI operatelogService) {
		this.operatelogService = operatelogService;
	}
	
	public ListenSocket(){
		try {
			if(null==ss){
				this.ss = new  ServerSocket(SERVER_PORT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("-------主监听线程----------start----------");
		try {
			while(true){
				Socket client = ss.accept();
				count += 1;
				Thread c_thread = new CreateServerThread(client,count);
				c_thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	class CreateServerThread extends Thread {
		private Socket client; //当前所连接客户端
		private int index;//当前线程编号
		private BufferedReader in = null;//从客户端接受到的信息
		private OutputStream out = null;//返回给客户端的信息
		
		public CreateServerThread(Socket client,int index) throws IOException {
			this.client = client;
			this.index = index;
		}
		
		@Override
		public void run(){
			System.out.println("-------当前连接的客户端数为----------" + index + "----------");
			String ms = "Callback accepted " + client.getInetAddress() + ":" + client.getPort();
			System.out.println(ms);
			
			try {
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));//接收请求的流				
				out = client.getOutputStream();//写入缓存
				int len = 0;//监听到的字符串长度
				String str = "";//监听到的字符串
				char buf[] = new char[4096];
				while((len = in.read(buf)) > 0){
					//读取
//					str += new String(buf,0,len);
					str = new String(buf,0,len);
					System.out.println("---------获取到第"+index+"个客户端的报文："+str);
					if(str!=null && !"".equals(str)){
						if (str.contains("gateway")) {
							Map houseieee = JSON.parseObject(str,Map.class);
							clientMap.put(houseieee.get("gateway"), client);
						}
						Operatelog operatelog = new Operatelog();
						operatelog.setTitle(client.getInetAddress() + ":" + client.getPort());
						operatelog.setType("ClientMsg"); // 监控服务器是否正常
						operatelog.setContent(str);
						operatelog.setRemark("接收到的客户端消息");
						operatelogService.save(operatelog);
//						out.write(("服务端已接受到第"+index+"个客户端发送的报文:" +str).getBytes());
					}else{
						System.out.println("---------第"+index+"个客户端所传报文为空");
//						out.write("-1".getBytes());
						break;
					}
					/*try {
						
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}*/
				}
			} catch (IOException e) {
				System.out.println("---------服务端与第"+index+"个客户端交互时异常："+e.getMessage());
			}finally{
				try {
					if(client!=null){
						client.close();
						count -= 1;
						System.out.println("---------关闭第"+index+"个客户端连接，当前连接的客户端个数为"+count);
					}
				} catch (IOException e) {
					System.out.println("---------第"+index+"个客户端关闭异常："+e.getMessage());
				}
			}
		}
	}
	
	public void closeSocketServer(){
		try{
			if(ss!=null && !ss.isClosed()){
				ss.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		/*new ListenSocket().start();
		
		try {
			ServerSocket socketServer = new ServerSocket(9090);
			while (true) {
				Socket socket = socketServer.accept();
				InputStream in = socket.getInputStream();
				byte[] b = new byte[7];
				int len;
				OutputStream out = socket.getOutputStream();//写入缓存
				String str = "";//监听到的字符串
				try {
					System.out.println("-------当前连接的客户端数为-------------------");
					String ms = "Callback accepted " + socket.getInetAddress() + ":" + socket.getPort();
					System.out.println(ms);
					while ((len = in.read(b)) != -1) {
						String value = new String(b, 0, len);
						double d=Double.parseDouble(value);
						//process data
						System.out.println(d);
						System.out.println("---------获取到第个客户端的报文："+d);
						out.write(("服务端已接受到第个客户端发送的报文:" +d).getBytes());
					}
				} catch (Exception ex) {
					//handle exception
//					ex.printStackTrace();
				} finally{
					try {
						if(socket!=null){
							socket.close();
							System.out.println("---------关闭第个客户端连接，当前连接的客户端个数为");
						}
					} catch (IOException e) {
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		/*try {
			ServerSocket serverSocket=new ServerSocket(1111);
			Socket socket=serverSocket.accept();
			while(true){//保持长连接
			   	try {
			       		Thread.sleep(100);//等待时间
			   	} catch (InterruptedException e1) {
			       		e1.printStackTrace();
			   	}
				if (socket !=null){
			   		try {
			      			String ip = socket.getInetAddress().toString().replace("/", "");
			   	      		System.out.println("====socket.getInetAddress()====="+ip);
			          		socket.setKeepAlive(true);
			          		InputStream is = socket.getInputStream();
			          		OutputStream os = socket.getOutputStream();
			          		System.out.println("服务器端接受请求");
						String tempdata = StreamEazyUse.getContent(is);
						String tempdata = "";
						System.out.println("接收到的数据为："+tempdata);
						if(tempdata.contains("stop")){
							is.close();
							os.close();
						}
						os.flush();
					}catch(Exception e){
						System.out.println("出现了错误");
					}
				}
			} 
			
		} catch (Exception e) {
				// TODO: handle exception
		}*/
		
	}
	
	
}
