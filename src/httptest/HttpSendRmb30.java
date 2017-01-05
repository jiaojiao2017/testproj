package httptest;

import helppac.HttpClientUtil;
import helppac.Pkipair;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import page.BankMock;
import base.SysPara;
import common.util.DateUtil;
import dbtest.ResultQuery;

/**
 * 人民币网关3.0测试，HTTPS提交
 * @author jiaojiao.ma
 *
 */

public class HttpSendRmb30 {
//	private String url = "https://192.168.1.132/";
//	private String url = "http://192.168.21.230:8082/";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;
	private static ResourceBundle resource = SysPara.getResource();
	private String url = resource.getString("posthostnew");
	private static Date d = new Date();
	private static String orderid = DateUtil.formatDateTime(
			"yyyyMMddHHmmssSSS", d);

	public HttpSendRmb30() {
		httpClientUtil = new HttpClientUtil();
	}

	public String  sendrequest() {
		String httpOrgCreateTest = url + "pay/receivermb.htm";
		Map<String, String> createMap = new LinkedHashMap<String, String>();

		String requ = "";
		String env = "stage2";
		String mer = "10012016111";
		String ter = "test002";
		String pwd = "99bill";
		String AliasName = "test-alias";
		String ordertime = DateUtil.formatDateTime("yyyyMMddHHmmss", d);
		String amt = "6660";

		createMap.put("inputCharset", "1");
		createMap.put("pageUrl", "http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverPg.do");
		createMap.put("bgUrl", "http://192.168.1.111:8081/QAMOCK-Test/notifyReceiverBg.do");
		createMap.put("version", "3.0");
		createMap.put("language", "1");
		createMap.put("signType", "4");
		createMap.put("merchantAcctId", mer + "01");
		createMap.put("terminalId", ter);
		createMap.put("payerName", "李财水");
		createMap.put("payerContactType", "2");
		createMap.put("payerContact", "15800000000");
		createMap.put("payerIdentityCard", "320125198805232313");
		createMap.put("orderId", orderid);
		createMap.put("orderCurrency", "CNY");
		createMap.put("orderAmount", amt);
		createMap.put("orderTime", ordertime);
		createMap.put("productName", "test_IPHONE6");
		createMap.put("productNum","1");
		createMap.put("productId", "YN0001");
		createMap.put("productDesc", "IPHONE6test");
		createMap.put("ext1", "ext1");
		createMap.put("ext2", "ext2");
		createMap.put("payType", "10");
		createMap.put("bankId","cmb");
		createMap.put("redoFlag", "1");

		for (Map.Entry<String, String> entry : createMap.entrySet()) {
			requ = requ + entry.getKey() + "=" + entry.getValue() + "&";
		}
		//去除最后的&
		requ=requ.substring(0,requ.length()-1);
		System.out.println("requ："+requ);

		Pkipair pkisign = new Pkipair();
		String signMsg = pkisign.signMsg(requ, env, mer, pwd, AliasName);
		createMap.put("signMsg", signMsg);
//		System.out.println("signMsg"+signMsg);

		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,
				createMap, charset);
		System.out.println("result:" + httpOrgCreateTestRtn);
		
		//Redirect页面
		String httpOrgCreateTestRtn1 = httpClientUtil.doPost(httpOrgCreateTestRtn);
//		System.out.println("result1:" + httpOrgCreateTestRtn1);
		
		return httpOrgCreateTestRtn1;
		
		
	}
	
	public void notify(String result){
		//发送结果通知
				Map<String, String> map = new HashMap<String, String>();
				BankMock bm = new BankMock();
				map = bm.parser(result);
				String checkval = map.get("checkvalue");
				String dealid = map.get("dealid");
				String amt = map.get("amt");

				String sre = HttpRequest
						.sendPost(
//								"http://192.168.1.111:8081/QAMOCK-Test/payResult.do?bgurl=https://192.168.1.132/pay/bgChinaPnrReceive.htm&pgurl=https://192.168.1.132/pay/pgChinaPnrReceive.htm&params=OrdAmt="
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
		
	}
	
	/**
	 * 在数据库中查询交易状态
	 * @return true为与预期结果相符
	 */
	
	public boolean checkresult() {
		String sql = " SELECT status  FROM gateway.t_transaction  t where t.ORDER_ID='"
				+ orderid + "'";
		ResultQuery rq = new ResultQuery();
		String expectedresult = "51";
		String result = rq.getresultwait(sql, expectedresult, 5);
		if (expectedresult.equals(result)) {// 状态为成功
			return true;
		} else {
			System.out.println("sql查询结果为:" + result);
			return false;
		}

	}
	
	public void trade(){
		HttpSendRmb30 main = new HttpSendRmb30();
		String result=main.sendrequest();
		main.notify(result);
		boolean flag=main.checkresult();
		if(flag){
			System.out.println("人民币网关交易成功");
		}
		else{
			System.out.println("人民币网关交易失败");
		}
		
		
	}

	public static void main(String[] args) {
		HttpSendRmb30 main = new HttpSendRmb30();
		main.trade();
	}
}
