package sy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * @author hlw 发送邮件类
 * 
 */
public class Mail {
	private static final Logger log = Logger.getLogger(Mail.class);
	private static Mail mail = new Mail();
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 配置文件属性
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	public Mail() {
		loadMailConfig();
		createMimeMessage();
	}

	public static Mail getInstance() {
		return mail;
	}

	public Properties getProps() {
		return props;
	}

	/**
	 * 载入邮件的设置
	 */
	public void loadMailConfig() {
		if (props == null) {
			props = new Properties();
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					"resources.properties");
			try {
				props.load(is);
			} catch (IOException e) {
//				e.printStackTrace();
				log.info("载入resources.properties出错" + e.getMessage());
			}
		}
	}

	/**
	 * 创建MIME邮件对象
	 * 
	 * @return
	 */
	public boolean createMimeMessage() {
		try {
			log.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			log.error("获取邮件会话对象时发生错误！" + e);
			return false;
		}

		log.info("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			log.error("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailSubject
	 * @return
	 */
	public void setSubject(String mailSubject) throws Exception {
		log.info("设置邮件主题！");
		mimeMsg.setSubject(mailSubject);
	}

	/**
	 * 设置邮件正文
	 * 
	 * @param mailBody
	 *            String
	 */
	public void setBody(String mailBody) throws Exception {
		BodyPart bp = new MimeBodyPart();
		if (mailBody!=null) {
			bp.setContent(mailBody, "text/html;charset=GBK");
		} else {
			bp.setContent("", "text/html;charset=GBK");
		}
		//bp.setContent("" + mailBody, "text/html;charset=GBK");
		mp.addBodyPart(bp);
	}

	/**
	 * 添加附件
	 * 
	 * @param filename
	 *            String
	 */
	public void addFileAffix(String filename) throws Exception {
		log.info("增加邮件附件：" + filename);
		BodyPart bp = new MimeBodyPart();
		FileDataSource fileds = new FileDataSource(filename);
		bp.setDataHandler(new DataHandler(fileds));
		bp.setFileName(fileds.getName());
		mp.addBodyPart(bp);
	}

	/**
	 * 设置发信人
	 * 
	 * @param from
	 *            String
	 */
	public void setFrom(String from) throws Exception {
		log.info("设置发信人！");
		mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
	}

	/**
	 * 设置收信人
	 * 
	 * @param to
	 *            String
	 */
	public void setTo(String to) throws Exception {
		if (to == null)
			throw new Exception("to 为null");
		mimeMsg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
	}

	/**
	 * 设置抄送人
	 * 
	 * @param copyto
	 *            String
	 */
	public void setCopyTo(String copyto) throws Exception {
		if (copyto == null)
			throw new Exception("copyto 为null");
		mimeMsg.setRecipients(Message.RecipientType.CC,
				InternetAddress.parse(copyto));
	}

	/**
	 * 发送邮件
	 * 
	 * @throws Exception
	 */
	public void sendOut() throws Exception {
		mimeMsg.setContent(mp);
		mimeMsg.saveChanges();
		log.info("正在发送邮件....");
		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(props.getProperty("mail.host"),
				props.getProperty("mail.username"),
				props.getProperty("mail.password"));
		transport.sendMessage(mimeMsg,
				mimeMsg.getRecipients(Message.RecipientType.TO));
		transport.sendMessage(mimeMsg,
				mimeMsg.getRecipients(Message.RecipientType.CC));
		log.info("发送邮件成功！");
		transport.close();
	}

	/**
	 * 发送邮件(没有抄送及附件)
	 * 
	 * @param to
	 *            收件人地址
	 * @param subject
	 *            邮件主题
	 * @param body
	 *            邮件内容
	 * @return
	 */
	public void sendOutToOnly(String to, String subject, String body)
			throws Exception {
		setSubject(subject);
		setBody(body);
		setFrom(props.getProperty("mail.from"));
		setTo(to);
		mimeMsg.setContent(mp);
		mimeMsg.saveChanges();
		log.info("正在发送邮件....");
		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(props.getProperty("mail.host"),
				props.getProperty("mail.username"),
				Httpproxy.urlCodec2(props.getProperty("mail.password"), "0000007785NETVOX"));
		transport.sendMessage(mimeMsg,
				mimeMsg.getRecipients(Message.RecipientType.TO));
		log.info("发送邮件成功！");
		transport.close();
	}

	/**
	 * 本地测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Mail mail = Mail.getInstance();
		String to = "r&d202@netvox.com.cn";
		String subject = "test";
		String body = "test data  test";
		mail.sendOutToOnly(to, subject, body);
	}

}
