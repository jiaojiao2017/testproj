package httptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;



//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import common.util.httpclient.HttpClientService;
import common.util.httpclient.HttpRequestContext;
import common.util.httpclient.HttpSendResult;
import common.util.httpclient.impl.HttpClientServiceImpl;
import helppac.Pkipair;
import helppac.TrxSingleQueryPagePara;

public class HttpRequest {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String requ = "inputCharset=1&signType=4&merchantAcctId=1001201611101&terminalId=test001&orderId=20150525210025473";
		String env = "stage2";
		String mer = "10012016111";
		String pwd = "99bill";
		String AliasName = "test-alias";
		
		TrxSingleQueryPagePara sendpara=new TrxSingleQueryPagePara();
		sendpara.setAliasName(AliasName);
		sendpara.setEnvType(env);
		sendpara.setInputCharset("1");
		sendpara.setMerchantAcctId(mer);
		sendpara.setOrderId("111111111");
		sendpara.setPassword(pwd);
		sendpara.setRequestUrl("http://192.168.21.230:8082/pay/singleTrxQuery.htm");
		sendpara.setSignType("4");
		sendpara.setTerminalId("test001");

		try{
		Pkipair pkisign = new Pkipair();
		String signMsg = pkisign.signMsg(requ, env, mer, pwd, AliasName);
		requ=requ+"&signMsg="+signMsg;
		
		System.out.println("request is "+requ);
		
		HttpRequestContext context = new HttpRequestContext();
		context.setUrl(sendpara.getRequestUrl());
		context.setHttpMethod(HttpRequestContext.POST_METHOD);
		context.setResponseCharset("utf-8");
		
		Map<String, String> sendParams = new HashMap<String, String>();
		sendParams.put("inputCharset", sendpara.getInputCharset());
		sendParams.put("signType", sendpara.getSignType());
		sendParams.put("merchantAcctId", sendpara.getMerchantAcctId());
		sendParams.put("terminalId", sendpara.getTerminalId());
		sendParams.put("orderId", sendpara.getOrderId());
		sendParams.put("dealId", sendpara.getDealId());
		sendParams.put("signMsg", signMsg);
		context.setSendParams(sendParams);
		System.out.println("sendParams:" + sendParams);
		System.out.println("context:" + context);
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");//读取bean.xml中的内容
		HttpClientService tp = ctx.getBean("obutil.httpClientService",HttpClientService.class);//创建bean的引用对象
		HttpSendResult result = tp.sendRequest(context);
		System.out.println("result:" + result.getResponseBody());
		
		//代码不可用，未完成

		// 发送 POST 请求
//		String sr = HttpRequest.sendPost(
//				"http://192.168.21.230:8082/pay/singleTrxQuery.htm", requ);
//		System.out.println("resp= "+sr);
		}
		catch  (Exception e ){
			System.out.println("exception "+e );
			throw e;
		}

	}
}