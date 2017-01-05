package dbtest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultQuery {

	static DBHelper db1 = null;
	static ResultSet ret = null;
	
	/**
	 * 根据语句查询出单条查询结果
	 * @param sql 
	 * @return
	 */

	public String getresult(String sql) {
		System.out.println("sql:" + sql);
		db1 = new DBHelper(sql);// 创建DBHelper对象
		String result = null;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			if (ret.next()) {
				result = ret.getString(1);
			}
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 查询sql，直到查询出结果或者查询次数到了设置次数
	 * @param sql 查询语句
	 * @param expect 预期结果
	 * @param retrytime 查询次数
	 * @return 查询结果
	 */

	public String getresultwait(String sql,String expect, int retrytime) {
		System.out.println("sql:" + sql);
		db1 = new DBHelper(sql);// 创建DBHelper对象
		String result = null;
		while (!expect.equals(result) && retrytime > 0) {

			try {
				Thread.sleep(1000);
				ret = db1.pst.executeQuery();// 执行语句，得到结果集
				if (ret.next()) {
					result = ret.getString(1);
				}
				ret.close();
				retrytime--;
				System.out.println("result="+result);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		db1.close();// 关闭连接
		return result;
	}

}
