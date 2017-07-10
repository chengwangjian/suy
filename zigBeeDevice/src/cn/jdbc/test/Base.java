package cn.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * 2008-12-6
 * 
 * @author <a href="mailto:liyongibm@hotmail.com">����</a>
 * 
 */
public class Base {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			Connection conn = JdbcUtils.getConnection();
			System.out.println(conn.getClass().getName());
			JdbcUtils.free(null, null, conn);
		}
		// template();
	}

	static void template() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.�������
			st = conn.createStatement();

			// 4.ִ�����
			rs = st.executeQuery("select * from user");

			// 5.������
			while (rs.next()) {
				// �����е�1,2,3,4��ָsql�е�������
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2)
						+ "\t" + rs.getObject(3) + "\t" + rs.getObject(4));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}

	}

	static void test() throws SQLException, ClassNotFoundException {
		// 1.ע����
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");// �Ƽ���ʽ

		// 2.��������
		String url = "jdbc:mysql://localhost:3306/jdbc";
		String user = "root";
		String password = "";
		Connection conn = DriverManager.getConnection(url, user, password);

		// 3.�������
		Statement st = conn.createStatement();

		// 4.ִ�����
		ResultSet rs = st.executeQuery("select * from user");

		// 5.������
		while (rs.next()) {
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t"
					+ rs.getObject(3) + "\t" + rs.getObject(4));
		}

		// 6.�ͷ���Դ
		rs.close();
		st.close();
		conn.close();
	}
}
