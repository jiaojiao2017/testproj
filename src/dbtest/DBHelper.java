package dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import base.SysPara;

public class DBHelper {
	
	ResourceBundle resource = SysPara.getResource(); 
	public  String url = resource.getString("dburl");;
	public  String user =resource.getString("user");;
	public  String password =resource.getString("password");;
	//st3
//	public static final String url = "jdbc:mysql://192.168.1.132/manage";
//	public static final String user = "root";
//	public static final String password = "pgistar";
	//huifutest
//	public static final String url = "jdbc:mysql://192.168.21.229/manage";
//	public static final String user = "root";
//	public static final String password = "Pgistar@5";
	public static final String name = "com.mysql.jdbc.Driver";
	

	public Connection conn = null;
	public PreparedStatement pst = null;

	public DBHelper(String sql) {
		try {
			Class.forName(name);//指定连接类型
			conn = DriverManager.getConnection(url, user, password);//获取连接
			pst = conn.prepareStatement(sql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
