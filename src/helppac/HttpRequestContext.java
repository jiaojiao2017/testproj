package helppac;

import java.util.Map;



/**
* 
* @project  ob-util
* @description http请求上下文
* @author xiuhong.wang
* @create time 2012-3-7 下午2:31:54
* @modify time 2012-3-7 下午2:31:54
* @modify comment 
* @version
*/
public class HttpRequestContext {
	/**
	 * 默认超时时间 (30S)
	 */
	public static final int DEFAULT_TIMEOUT = 30 * 1000;
	
	/**
	 * POST
	 */
	public static final String POST_METHOD = "POST";

	/**
	 * GET
	 */
	public static final String GET_METHOD = "GET";
	
	/**
	 * xml的contentType类型
	 */
	public static final String XML_CONTENT_TYPE = "text/xml";
	
	/**
	 * 请求的数据字符集
	 */
	private String requestCharset = "utf-8";
	
	/**
	 * 返回的数据字符集
	 */
	private String responseCharset = "GBK";
	
	/**
	 * 连接超时
	 */
	private int connectionTimeout = DEFAULT_TIMEOUT;

	/**
	 * 读取超时
	 */
	private int readTimeout = DEFAULT_TIMEOUT;

	/**
	 * 请求数据
	 */
	private Map<String, String> sendParams;

	/**
	 * 请求方法
	 */
	private String httpMethod = HttpRequestContext.POST_METHOD;

	/**
	 * 请求uri
	 */
	private String url;
	
	/**
	 * http get请求字串
	 */
	private String getParamString;
	
	/**
	 * xml请求数据
	 */
	private String xml;
	
	/**
	 * Timeout
	 */
	private int timeout;
	
	/**
	 * 自动跳转
	 */
	private boolean followRedirects = true;

	/**
	 * 请求头信息
	 */
	private Map<String,String> reqHeader;
	
	public Map<String, String> getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(Map<String, String> reqHeader) {
		this.reqHeader = reqHeader;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getSendParams() {
		return sendParams;
	}

	public void setSendParams(Map<String, String> sendParams) {
		this.sendParams = sendParams;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getGetParamString() {
		return getParamString;
	}

	public void setGetParamString(String getParamString) {
		this.getParamString = getParamString;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(String requestCharset) {
		this.requestCharset = requestCharset;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}
}
