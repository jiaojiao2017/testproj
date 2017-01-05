/**
 * 
 */
package dbtest;

import httptest.HttpRequest;

/**
 * @author jiaojiao.ma
 *
 */
public class HttpReq {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sr = HttpRequest.sendGet(
				"http://192.168.10.170:8080/tomtest/hello.html",null);
		System.out.println("resp= "+sr);
		}

	

}
