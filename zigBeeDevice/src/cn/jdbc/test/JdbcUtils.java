package cn.jdbc.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 
 * 2008-12-6
 * 
 * @author <a href="mailto:liyongibm@hotmail.com">����</a>
 * 
 */
public final class JdbcUtils {
	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String user = "root";
	private static String password = "root";
	private static DataSource myDataSource = null;

	private JdbcUtils() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// myDataSource = new MyDataSource2();
			Properties prop = new Properties();
			prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
			//prop.setProperty("user", "root");
			//prop.setProperty("password", "root");
			InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			prop.load(is);
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DataSource getDataSource() {
		return myDataSource;
	}

	public static Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
						// myDataSource.free(conn);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
}
