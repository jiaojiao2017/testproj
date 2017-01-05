package httptest;

import helppac.HttpClientUtil;
import helppac.Pkipair;

import java.util.LinkedHashMap;
import java.util.Map;

//对接口进行测试  
public class TestMain {
//	private String url = "https://192.168.1.132/";
	private String url = "http://192.168.21.230:8082/";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;

	public TestMain() {
		httpClientUtil = new HttpClientUtil();
	}

	public void test() {
		String httpOrgCreateTest = url + "pay/singleTrxQuery.htm";
		Map<String, String> createMap = new LinkedHashMap<String, String>();

		// String requ =
		// "inputCharset=1&signType=4&merchantAcctId=1001201611101&terminalId=test001&orderId=20161118164255137";
		String requ = "";
		String env = "stage2";
		String mer = "10012016111";
		String ter = "test001";
		String pwd = "99bill";
		String AliasName = "test-alias";

		createMap.put("inputCharset", "1");
		createMap.put("signType", "4");
		createMap.put("merchantAcctId", mer + "01");
		createMap.put("terminalId", ter);
		createMap.put("orderId", "20161118164255137");

		for (Map.Entry<String, String> entry : createMap.entrySet()) {
			requ = requ + entry.getKey() + "=" + entry.getValue() + "&";
			System.out.println("key= " + entry.getKey() + " and value= "
					+ entry.getValue());
		}
		//去除最后的&
		requ=requ.substring(0,requ.length()-1);
		System.out.println("requ："+requ);

		Pkipair pkisign = new Pkipair();
		String signMsg = pkisign.signMsg(requ, env, mer, pwd, AliasName);
		createMap.put("signMsg", signMsg);
		// System.out.println("signMsg"+signMsg);
		// requ=requ+"&signMsg="+signMsg;

		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,
				createMap, charset);
		System.out.println("result:" + httpOrgCreateTestRtn);
	}

	public static void main(String[] args) {
		TestMain main = new TestMain();
		main.test();
	}
}
