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
 *
 */
public class HttpSendRmb {
	//TODO
	//未完成

	private static ResourceBundle resource = SysPara.getResource();
	private static Date d = new Date();
	private static String orderid = DateUtil.formatDateTime(
			"yyyyMMddHHmmssSSS", d);

	public static String sendrequest() throws Exception {

		String env = "stage2";
		String mer = "10012016111";
		String terminal = "test002";
		String pwd = "99bill";
		String AliasName = "test-alias";
		String oriamt = "6000";

		String ordertime = DateUtil.formatDateTime("yyyyMMddHHmmss", d);

		String requ = "inputCharset=1&pageUrl=http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverPg.do&bgUrl=http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverBg.do&version=3.0&language=1&signType=4&merchantAcctId="
				+ mer
				+ "01&terminalId="
				+ terminal
				+ "&payerName=李财水&payerContactType=2&payerContact=15800000000&payerIdentityCard=320125198805232313&orderId="
				+ orderid
				+ "&orderCurrency=CNY&orderAmount="
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

			System.out.println("拼接报文结束");

			// 发送 POST 请求
			String sr = HttpRequest.sendPost(resource.getString("posthost")
					+ "pay/receivermb.htm", requ);

			// String sr = HttpRequest.sendPost(
			// "http://192.168.21.230:8082/pay/receivecb.htm", requ);

			System.out.println("发送post请求结束");
			System.out.println("resp:" + sr);
			
			

			// 发送 POST 请求
//			String sr1 = HttpRequest.sendGet(sr, null);
//			System.out.println("resp1:" + sr);

			// 发送结果通知
			Map<String, String> map = new HashMap<String, String>();
			BankMock bm = new BankMock();
			map = bm.parser(sr);
			String checkval = map.get("checkvalue");
			String dealid = map.get("dealid");
			String amt = map.get("amt");

			String sre = HttpRequest
					.sendPost(
							resource.getString("payresult")
									+ "?bgurl="
									+ resource.getString("bgurl")
									+ "&pgurl="
									+ resource.getString("pgurl")
									+ "&params=OrdAmt="
									// "http://192.168.21.229:8100/mock/payResult.do?bgurl=http://192.168.21.230:8082/pay/bgChinaPnrReceive.htm&pgurl=http://192.168.21.230:8082/pay/pgChinaPnrReceive.htm&params=OrdAmt="
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
			return dealid;

		} catch (Exception e) {
			System.out.println("exception " + e);
			throw e;
		}

	}

	public static boolean checkresult() {
		String sql = " SELECT status  FROM gateway.t_transaction  t where t.ORDER_ID='"
				+ orderid + "'";
		ResultQuery rq = new ResultQuery();
		String expectedresult = "51";
		String result = rq.getresultwait(sql, expectedresult, 5);
		if (expectedresult.equals(result)) {// 状态为购汇成功
			return true;
		} else {
			System.out.println("sql查询结果为:" + result);
			return false;
		}

	}

	public static void main(String[] args) throws Exception {
		sendrequest();
		System.out.println("人民币网关执行结果为" + checkresult());
	}

}
