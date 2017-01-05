package httptest;


import helppac.Pkipair;


public class HttpQuery {
	

	public static void main(String[] args) throws Exception {
		String requ = "inputCharset=1&signType=4&merchantAcctId=1001201611101&terminalId=test001&orderId=20161118164255137";
		String env = "stage2";
		String mer = "10012016111";
		String pwd = "99bill";
		String AliasName = "test-alias";

		try{
			//加密
		Pkipair pkisign = new Pkipair();
		String signMsg = pkisign.signMsg(requ, env, mer, pwd, AliasName);
		//将+转换为%2B，避免传输时被转换为空格
		String signMsg2= signMsg.replaceAll("\\+", "%2B");
		requ=requ+"&signMsg="+signMsg2;
		
		System.out.println("request is:"+requ);
		

		 //发送 POST 请求
		String sr = HttpRequest.sendPost(
				"http://192.168.1.139:8081/pay/singleTrxQuery.htm", requ);
		System.out.println("resp= "+sr);
		}
		catch  (Exception e ){
			System.out.println("exception "+e );
			throw e;
		}

	}
}
