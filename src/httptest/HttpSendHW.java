package httptest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.SysPara;
import page.BankMock;
import common.util.DateUtil;
import dbtest.ResultQuery;
import helppac.Pkipair;

public class HttpSendHW {
	
	private static ResourceBundle resource = SysPara.getResource(); 
	private static Date d = new Date();
	private static String orderid = DateUtil.formatDateTime("yyyyMMddHHmmssSSS", d);

	public static void sendrequest() throws Exception {		

		String env = "stage2";
		String mer = "10012016111";
		String terminal="test001";
		String pwd = "99bill";
		String AliasName = "test-alias";
		String oriamt = "2330";
		String ordertime = DateUtil.formatDateTime("yyyyMMddHHmmss", d);

		String requ = "inputCharset=1&pageUrl=http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverPg.do&bgUrl=http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverBg.do&version=2.0&language=1&signType=4&merchantAcctId="
				+ mer
				+ "01&terminalId="+terminal+"&payerName=李财水&payerContactType=2&payerContact=15800000000&payerIdentityCard=320125198805232313&mobileNumber=15811111111&cardNumber=6225881257000000&customerId=1001&orderId="
				+ orderid
				+ "&orderCurrency=USD&settlementCurrency=USD&orderAmount="
				+ oriamt
				+ "&orderTime="
				+ ordertime
				+ "&productName=test_IPHONE6&productNum=1&productId=YN0001&productDesc=IPHONE6test&ext1=ext1&ext2=ext2&payType=10&bankId=cmb&redoFlag=1";

		try {
			// 加密

			Pkipair pkisign = new Pkipair();

			String signMsg = pkisign.signMsg(requ, env, mer, pwd, AliasName);
			// 将+转换为%2B，避免传输时被转换为空格
			String signMsg2 = signMsg.replaceAll("\\+", "%2B");
			requ = requ + "&signMsg=" + signMsg2;

			System.out.println("拼接报文结束" );

			// 发送 POST 请求
//			String sr = HttpRequest.sendPost(
//					"http://192.168.1.139:8081/pay/receivehw.htm", requ);
			String sr = HttpRequest.sendPost(
					resource.getString("posthost")+"pay/receivehw.htm", requ);
			
			System.out.println("resp:"+sr);
			System.out.println("发送post请求结束");
			
			//发送结果通知
			Map<String, String> map = new HashMap<String, String>();
			BankMock bm = new BankMock();
			map = bm.parser(sr);
			String checkval = map.get("checkvalue");
			String dealid = map.get("dealid");
			String amt = map.get("amt");

			String sre = HttpRequest
					.sendPost(
//							"http://192.168.1.111:8081/QAMOCK-Test/payResult.do?bgurl=https://192.168.1.132/pay/bgChinaPnrReceive.htm&pgurl=https://192.168.1.132/pay/pgChinaPnrReceive.htm&params=OrdAmt="
							resource.getString("payresult")+"?bgurl="+resource.getString("bgurl")+"&pgurl="+resource.getString("pgurl")+"&params=OrdAmt="
									+ amt
									+ ",RetType=R,MerPriv=,TrxId="
									+ orderid
									+ ",GateId=00,RespCode=000000,DivDetails=,CurCode=,ChkValue="
									+ checkval
									+ ",MerId=510884,OrdId="
									+ dealid
									+ ",Pid=,CmdId=Buy&not=1&repeat=0&repeatTimes=1",
							null);
			System.out.println("银行通知结束");

	
		} catch (Exception e) {
			System.out.println("exception " + e);
			throw e;
		}

	}
	
	public  static boolean checkresult(){
		String sql=" SELECT status  FROM manage.t_exchange_slip t where t.ORDER_ID='"+orderid+"'";		
		ResultQuery rq=new ResultQuery();
		String expectedresult="50";
		String result=rq.getresultwait(sql,expectedresult,5);
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
		System.out.println("跨境人民币执行结果为"+checkresult());
	}

}
