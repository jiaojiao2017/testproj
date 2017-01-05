/**
 * 
 */
package httptest;

import helppac.Pkipair;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import page.BankMock;
import base.SysPara;

import common.util.DateUtil;

import dbtest.ResultQuery;

/**
 * @author jiaojiao.ma
 * 海关申报HTTP请求
 *
 */
public class HttpSendHG {
	
	private static ResourceBundle resource = SysPara.getResource(); 
	private static Date d = new Date();
	private static String orderid = DateUtil.formatDateTime("yyyyMMddHHmmssSSS", d);

	public static void sendrequest() throws Exception {	


		String env = "stage2";
		String mer = "10012016111";
		String terminal="hg0002";
		String pwd = "99bill";
		String AliasName = "test-alias";
		String oriamt = "2330";
		String custom = "GZHG";
		String ordertime = DateUtil.formatDateTime("yyyyMMddHHmmss", d);

		String requ = "version=1.0&bgUrl=http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverBg.do&signType=4&merchantAcctId="
				+ mer
				+ "01&terminalId="+terminal+"&customCode="+custom+"&merCustomCode=1111&merCustomName=测试商户&payerIdType=1&payerName=李财水&payerIdNumber=320125198805232313&orderId="
				+ orderid
				+ "&orderCurrency=CNY&orderAmt="
				+ oriamt
				+ "&freightAmt=0&goodsAmt="+oriamt+"&taxAmt=0&offsetAmt=0";

		try {
			// 加密

			Pkipair pkisign = new Pkipair();

			String signMsg = pkisign.signMsg(requ, env, mer, pwd, AliasName);
			// 将+转换为%2B，避免传输时被转换为空格
			String signMsg2 = signMsg.replaceAll("\\+", "%2B");
			
			//有部分内容不在加签范围内，加签完成后再加上去
			requ = requ + "&signMsg=" + signMsg2+"&ecpDomainName=www.test.com&ecpShortName=3214&competCustom=9968&iaqInstCode=9999";

			System.out.println("拼接报文结束" );

			// 发送 POST 请求
//			String sr = HttpRequest.sendPost(
//					"http://192.168.1.139:8081/pay/receivehw.htm", requ);
			String sr = HttpRequest.sendPost(
					resource.getString("customurl"), requ);
			String errormsg=sr.substring(sr.lastIndexOf("="));
			
//			System.out.println("resp:"+sr);
			System.out.println("errormsg"+errormsg);
			
			System.out.println("发送post请求结束");

	
		} catch (Exception e) {
			System.out.println("exception " + e);
			throw e;
		}

	}
	
	public  static boolean checkresult(){
		String sql=" SELECT status  FROM manage.t_custom_import_txn t  where ORDER_ID ='"+orderid+"'";		
		ResultQuery rq=new ResultQuery();
		String expectedresult="02";
		String result=rq.getresultwait(sql,expectedresult,10);
		if(expectedresult.equals(result)){//状态为授权成功
			return true;
		}
		else{
			System.out.println("sql查询结果为:"+result);
			return false;
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		sendrequest();
		System.out.println("海关申报申请结果为："+checkresult());
	}

}
