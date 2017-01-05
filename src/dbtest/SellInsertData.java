package dbtest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SellInsertData {

	static String sql = null;
	static DBHelper db1 = null;
	static boolean ret = false;

	public static void main(String[] args) {
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime()); // 设置当前日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		sql = "insert into t_sell_order(sell_order_id,member_code,terminal_id,batch_no,batch_time) values ('1058','10012159865','JHDF002','M1478518387211','2016-11-22 11:11:11')";//SQL语句
		db1 = new DBHelper(sql);//创建DBHelper对象

		try {
			ret = db1.pst.execute();//执行语句，得到结果集
			System.out.println("result :"+ret);
			
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
