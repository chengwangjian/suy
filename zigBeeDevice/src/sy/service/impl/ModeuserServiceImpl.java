package sy.service.impl;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.Modeuser;
import sy.model.Verifycode;
import sy.service.ModeuserServiceI;
import sy.util.Httpproxy;


@Service("modeuserService")
public class ModeuserServiceImpl implements ModeuserServiceI {

	private static final Logger logger = Logger.getLogger(ModeuserServiceImpl.class);

	private BaseDaoI<Modeuser> modeuserDao;
	private BaseDaoI<Verifycode> verifycodeDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Modeuser> getModeuserDao() {
		return modeuserDao;
	}

	@Autowired
	public void setModeuserDao(BaseDaoI<Modeuser> modeuserDao) {
		this.modeuserDao = modeuserDao;
	}
	
	public BaseDaoI<Verifycode> getVerifycodeDao() {
		return verifycodeDao;
	}

	@Autowired
	public void setVerifycodeDao(BaseDaoI<Verifycode> verifycodeDao) {
		this.verifycodeDao = verifycodeDao;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public Modeuser save(Modeuser modeuser) {
		modeuserDao.save(modeuser);
		return modeuser;
	}
	
	/**
	 * 保存验证码
	 * @author: zhuangxd
	 * 时间：2013-11-19 下午2:26:31
	 * @param verifycode
	 * @return
	 */
	@Override
	public Verifycode saveVerifycode(Verifycode verifycode) {
		verifycodeDao.save(verifycode);
		return verifycode;
	}
	
	@Override
	public Modeuser update(Modeuser modeuser) {
		modeuserDao.update(modeuser);
		return modeuser;
	}
	
	@Override
	public Modeuser get(Modeuser modeuser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", modeuser.getUserName());
//		params.put("pwd", modeuser.getPwd());
		/*Modeuser t = modeuserDao.get("from Modeuser t where t.userName = :userName " +
				" and t.enabled = '1' ", params);*/
		params.put("verifyCode", modeuser.getVerifyCode());
		// 邮箱收到的验证码
		Verifycode t2 = verifycodeDao.get("from Verifycode t where t.userName = :userName " +
				" and t.verifyCode = :verifyCode ", params);
		/*if (t != null || t2 == null) {
			return new Modeuser();
		}*/
		if (t2 == null) {
			return new Modeuser();
		}
		return null;
	}
	
	@Override
	public Modeuser getModeuser(Modeuser modeuser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", modeuser.getUserName());
		Modeuser t = modeuserDao.get("from Modeuser t where t.userName = :userName " +
				" and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modeuser getModeuser2(Modeuser modeuser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modeuser.getId());
		Modeuser t = modeuserDao.get("from Modeuser t where t.id = :id " +
				" and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modeuser find(Modeuser modeuser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", modeuser.getUserName());
		params.put("pwd", modeuser.getPwd());
		Modeuser t = modeuserDao.get("from Modeuser t where t.userName = :userName and t.pwd = :pwd and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Modeuser> findList(Modeuser modeuser) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Modeuser t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modeuser.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modeuser.getHouseIeee());
		}
		if (modeuser.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + modeuser.getName() + "%");
		}
		if (modeuser.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", modeuser.getSecretKey());
		}
		if (modeuser.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", modeuser.getVendorCode());
		}
		if (modeuser.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modeuser.getVendorName() + "%");
		}
		if (modeuser.getEncodemethod() != null) {
			hql.append("and t.encodemethod = :encodemethod ");
			params.put("encodemethod", modeuser.getEncodemethod());
		}
		if (modeuser.getXmppIp() != null) {
			hql.append("and t.xmppIp = :xmppIp ");
			params.put("xmppIp", modeuser.getXmppIp());
		}
		if (modeuser.getXmppPort() != null) {
			hql.append("and t.xmppPort = :xmppPort ");
			params.put("xmppPort", modeuser.getXmppPort());
		}
		if (modeuser.getCloudIp1() != null) {
			hql.append("and t.cloudIp1 = :cloudIp1 ");
			params.put("cloudIp1", modeuser.getCloudIp1());
		}
		if (modeuser.getCloudPort1() != null) {
			hql.append("and t.cloudPort1 = :cloudPort1 ");
			params.put("cloudPort1", modeuser.getCloudPort1());
		}
		if (modeuser.getCloudIp2() != null) {
			hql.append("and t.cloudIp2 = :cloudIp2 ");
			params.put("cloudIp2", modeuser.getCloudIp2());
		}
		if (modeuser.getCloudPort2() != null) {
			hql.append("and t.cloudPort2 = :cloudPort2 ");
			params.put("cloudPort2", modeuser.getCloudPort2());
		}
		if (modeuser.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modeuser.getEnableFlag());
		}*/
		List<Modeuser> t = modeuserDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Modeuser modeuser) {
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + modeuser.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Modeuser s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modeuser.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", modeuser.getHouseIeee());
		}
		if (modeuser.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + modeuser.getName() + "%");
		}*/
		List<Object[]> t = modeuserDao.findSql(sql.toString(), params, Modeuser.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public int delete(Modeuser modeuser) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modeuser t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		/*if (modeuser.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modeuser.getHouseIeee());
			params2.put("houseIeee", modeuser.getHouseIeee());
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + modeuser.getHouseIeee() + "_" + year;
			tableName = "deviceOperateHistory_" + modeuser.getHouseIeee() + "_" + year;
			tableName = "deviceWarnHistory_" + modeuser.getHouseIeee() + "_" + year;
		}*/
		/*if (modeuser.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + modeuser.getName() + "%");
		}
		if (modeuser.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", modeuser.getSecretKey());
		}
		if (modeuser.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", modeuser.getVendorCode());
		}
		if (modeuser.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modeuser.getVendorName() + "%");
		}
		if (modeuser.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modeuser.getEnableFlag());
		}		*/
		return modeuserDao.executeHql(hql.toString(), params);
	}
	
	/**
	 * 删除验证码
	 * @author: zhuangxd
	 * 时间：2013-11-19 上午10:35:36
	 * @param modeuser
	 * @return
	 */
	@Override
	public int deleteVerifyCode(Modeuser modeuser) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Verifycode t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(modeuser.getUserName())) {
			hql.append("and t.userName = :userName ");
			params.put("userName", modeuser.getUserName());
		}
		/*if (StringUtils.isNotBlank(modeuser.getVerifyCode())) {
			hql.append("and t.verifyCode = :verifyCode ");
			params.put("verifyCode", modeuser.getVerifyCode());
		}*/
		return verifycodeDao.executeHql(hql.toString(), params);
	}
	
	/**
	 *用spring mail 发送邮件,依赖jar：spring.jar，activation.jar，mail.jar 
	 */
	@Override
	public void sendFileMail(Modeuser modeuser) throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		Properties prop = new Properties();
		//			prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		InputStream is = ModeuserServiceImpl.class.getClassLoader().getResourceAsStream("resources.properties");
		try {
			// 读取配置文件
			prop.load(is);
		} catch (Exception e) {				
		}
		// 设定mail server
		senderImpl.setHost(prop.getProperty("mail.host"));
		senderImpl.setUsername(prop.getProperty("mail.username"));
		// 密码解密
		senderImpl.setPassword(Httpproxy.urlCodec2(prop.getProperty("mail.password"), "0000007785NETVOX"));
		// 建立HTML邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// true表示开始附件模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

		// 设置收件人，寄件人
		messageHelper.setTo(modeuser.getUserName());
		messageHelper.setFrom(prop.getProperty("mail.from"));
		String language = modeuser.getLanguage();
		if ("1".equals(language)) {
			messageHelper.setSubject("Receive the verification code！");
			messageHelper.setText("<html><head></head><body><h1>Received six verification code :" + modeuser.getVerifyCode() + "</h1></body></html>", true);
			senderImpl.send(mailMessage);
		}else if("2".equals(language) || language == null){
			messageHelper.setSubject("收到验证码！");
			messageHelper.setText("<html><head></head><body><h1>收到6位验证码为" + modeuser.getVerifyCode() + "</h1></body></html>", true);
			senderImpl.send(mailMessage);
		}
		
		/*Random random = new Random();
		String sRand="";
		for (int i=0;i<6;i++){
			String rand=String.valueOf(random.nextInt(10));
			sRand+=rand;		
		}*/
		
		// 生成6位验证码
//		String sRand = Common.createRandom(true, 6);
		
		// true 表示启动HTML格式的邮件
		

		//			FileSystemResource file1 = new FileSystemResource(new File("d:/logo.jpg"));
		//			FileSystemResource file2 = new FileSystemResource(new File("d:/读书.txt"));
		// 添加2个附件
		//			messageHelper.addAttachment("logo.jpg", file1);

		/*try {
				//附件名有中文可能出现乱码
				messageHelper.addAttachment(MimeUtility.encodeWord("读书.txt"), file2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new MessagingException();
			}*/
		// 发送邮件
		logger.info("收件人为：" + modeuser.getUserName() + ",邮件发送成功,验证码为" + modeuser.getVerifyCode());
	}
	
	@Override
	public void sendPwdMail(Modeuser modeuser) throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		Properties prop = new Properties();
		//			prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		InputStream is = ModeuserServiceImpl.class.getClassLoader().getResourceAsStream("resources.properties");
		try {
			// 读取配置文件
			prop.load(is);
		} catch (Exception e) {				
		}
		// 设定mail server
		senderImpl.setHost(prop.getProperty("mail.host"));
		senderImpl.setUsername(prop.getProperty("mail.username"));
		// 密码解密
		senderImpl.setPassword(Httpproxy.urlCodec2(prop.getProperty("mail.password"), "0000007785NETVOX"));
		// 建立HTML邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// true表示开始附件模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

		// 设置收件人，寄件人
		messageHelper.setTo(modeuser.getUserName());
		messageHelper.setFrom(prop.getProperty("mail.from"));
		
		String language = modeuser.getLanguage();
		if ("1".equals(language)) {
			messageHelper.setSubject("Receive the login code！");
			messageHelper.setText("<html><head></head><body><h1>the sign-in code :" + modeuser.getPwd() + "</h1></body></html>", true);
			senderImpl.send(mailMessage);
		}else if("2".equals(language) || language == null){
			messageHelper.setSubject("收到登录密码！");
			messageHelper.setText("<html><head></head><body><h1>您的登录密码为" + modeuser.getPwd() + "</h1></body></html>", true);
			senderImpl.send(mailMessage);
		}
		
		
		// true 表示启动HTML格式的邮件

		//			FileSystemResource file1 = new FileSystemResource(new File("d:/logo.jpg"));
		//			FileSystemResource file2 = new FileSystemResource(new File("d:/读书.txt"));
		// 添加2个附件
		//			messageHelper.addAttachment("logo.jpg", file1);

		/*try {
				//附件名有中文可能出现乱码
				messageHelper.addAttachment(MimeUtility.encodeWord("读书.txt"), file2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new MessagingException();
			}*/
		// 发送邮件
		logger.info("收件人为：" + modeuser.getUserName() + ",邮件发送成功");
	}

	@Override
	public Modeuser login(Modeuser modeuser) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(modeuser.getPwd()));
//		params.put("name", modeuser.getName());
		Modeuser t = modeuserDao.get("from Modeuser t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return modeuser;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Modeuser modeuser) {
		DataGrid dg = new DataGrid();
		String hql = "from Modeuser t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(modeuser, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(modeuser, hql);
		List<Modeuser> l = modeuserDao.find(hql, params, modeuser.getPage(), modeuser.getRows());
		List<Modeuser> nl = new ArrayList<Modeuser>();
		changeModel(l, nl);
		dg.setTotal(modeuserDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Modeuser> l, List<Modeuser> nl) {
		if (l != null && l.size() > 0) {
			for (Modeuser t : l) {
				Modeuser u = new Modeuser();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Modeuser modeuser, String hql) {
		/*if (modeuser.getSort() != null) {
			hql += " order by " + modeuser.getSort() + " " + modeuser.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Modeuser modeuser, String hql, Map<String, Object> params) {
		/*if (modeuser.getName() != null && !modeuser.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + modeuser.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Modeuser t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		modeuserDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	@Override
	public int getUserCount(Map<String, Object> condition) throws Exception { 
		StringBuilder sql = new StringBuilder(
				"select count(*) as user_count from modeuser t where t.enabled = '1' and ");
		if (condition != null) {
			Iterator itor = condition.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
				sql.append(key).append("='").append(condition.get(key))
						.append("' and ");
			}
			sql.delete(sql.length() - 5, sql.length() - 1);
		}
		List<Map> cList = mapDao.executeSql(sql.toString());
		return ((BigInteger) cList.get(0).get("user_count")).intValue();
	}
	
	@Override
	public List<Map> queryUser(Map<String, Object> condition, int startRow,
			int pageSize) throws Exception {
		StringBuilder sql = new StringBuilder(
				"select a.id as id,a.userName as userName,b.level_name as level_name,a.level_id as level_id from modeuser a left join modelevel b on a.level_id=b.id ");
		if (condition != null && condition.size() > 0) {
			sql.append("where ");
			Iterator itor = condition.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
				sql.append(key).append("='").append(condition.get(key))
						.append("' and ");
			}
			sql.delete(sql.length() - 5, sql.length() - 1);
		}
		sql.append("order by registTime desc limit ");
		sql.append(startRow).append(",").append(pageSize);
		List<Map> uList = mapDao.executeSql(sql.toString());
		return uList;
	}
	
	@Override
	public boolean isPwdRight(Long id, String pwd) throws Exception {
		StringBuilder sql = new StringBuilder("select 1 from modeuser where ");
		sql.append("id=").append(id).append(" and pwd='").append(pwd)
				.append("'");
		List<Map> pwdList = mapDao.executeSql(sql.toString());
		if (pwdList.isEmpty())
			return false;
		else
			return true;
	}
	
	@Override
	public void updateModeuser(Map<String, Object> paramMap) throws Exception {
		StringBuilder sql = new StringBuilder("update modeuser set ");
		if(paramMap != null && paramMap.size() > 1) {
			Iterator itor = paramMap.keySet().iterator();
			while(itor.hasNext()) {
				String key = (String) itor.next();
				if(!key.equals("id")) {
					String value = paramMap.get(key).toString();
					sql.append(key).append("='").append(value).append("',");
				}
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" where id = ").append(paramMap.get("id"));
			mapDao.executeSql2(sql.toString());
		}		
	}
	
	@Override
	public void deleteUser(Long id) throws Exception {
		StringBuilder sql = new StringBuilder("delete from modeuser where id=");
		sql.append(id);
		mapDao.executeSql2(sql.toString());
	}
	
	@Override
	public List<Map> getPrivilege(Long id) throws Exception {
		StringBuilder sql = new StringBuilder(
				"select a.privilege_code as privilege_code from modeprivilege a left join modelevelprivilege b ");
		sql.append("on a.id = b.privilege_id left join modeuser c on b.level_id = c.level_id where c.id =");
		sql.append(id);
		List<Map> pList = mapDao.executeSql(sql.toString());
		return pList;
	}
}
