package dbtest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetSellRemitReqId {
	
	static String sql = null;
	static DBHelper db1 = null;
	static ResultSet ret = null;

	public String getid(String member) {
		sql = "select max(REMIT_REQID) from manage.t_sell_remit_req r1 where MEMBER_CODE='"+member+"' ";//SQL语句
		db1 = new DBHelper(sql);//创建DBHelper对象
		String id = null;

		try {
			ret = db1.pst.executeQuery();//执行语句，得到结果集
			if(ret.next()){			
			id=ret.getString(1);}
			ret.close();
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	public static void main(String[] args) {
		GetSellRemitReqId gi=new GetSellRemitReqId();
		System.out.println(gi.getid("10012016111"));
	}


}
