package sy.util.socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sy.service.OperatelogServiceI;

/**
 * @author: zhuangxd
 * 时间：2013-10-17 下午2:09:07
 */
public class SocketListener implements ServletContextListener {
	private ListenSocket socketThread;
	
	/**
	 * 销毁 当servlet容器终止web应用时调用该方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if(null != socketThread && !socketThread.isInterrupted()){
			socketThread.closeSocketServer();//关闭线程
			socketThread.interrupt();//中断线程
		}
	}

	/**
	 * 初始化 当servlet容器启动web应用时调用该方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if(null == socketThread){
			socketThread = new ListenSocket();
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
			OperatelogServiceI operatelogService = (OperatelogServiceI) ctx.getBean("operatelogService"); // 填写要注入的类,注意第一个字母小写
			socketThread.setOperatelogService(operatelogService);
			socketThread.start();
		}
	}

}
