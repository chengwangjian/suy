package sy.util.socket;

/**
 * @author: zhuangxd
 * 时间：2013-10-18 下午3:19:47
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*{  user:jiangwh }*/

public class SocketClient {

	public static final Object locked = new Object();
	public static final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(
			1024 * 100);

	class SendThread extends Thread{
		private Socket socket;
		public SendThread(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			while(true){
				try {
					String send = getSend();			
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					pw.write(send);
					pw.flush();
					Thread.sleep(1000000000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		public String getSend() throws InterruptedException{
//			Thread.sleep(1000);
//			return "<SOAP-ENV:Envelope>"+System.currentTimeMillis()+"</SOAP-ENV:Envelope>";
			return "{\"gateway\":\"00137A0000013499\"}";

		}
	}

	class ReceiveThread extends Thread{
		private Socket socket;
		
		public ReceiveThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			while(true){
				try {					
					Reader reader = new InputStreamReader(socket.getInputStream());
					CharBuffer charBuffer = CharBuffer.allocate(8192);
					int index = -1;
					while((index=reader.read(charBuffer))!=-1){
						charBuffer.flip();
						System.out.println("client:"+charBuffer.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void start() throws UnknownHostException, IOException{
		Socket socket = new Socket("127.0.0.1",8088);
		new SendThread(socket).start();
		new ReceiveThread(socket).start();
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		new SocketClient().start();
	}
}

