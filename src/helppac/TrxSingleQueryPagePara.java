package helppac;


public class TrxSingleQueryPagePara {
	
	/**
	 * 请求地址
	 */
	private String requestUrl;
	
	/**
	 * 
	 */
	private String inputCharset;
	
	/**
	 * 
	 */
	private String signType;

	/**
	 * 会员账号
	 */
	private String merchantAcctId;
	
	/**
	 * 商户终端号
	 */
	private String terminalId;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 收单系统交易号
	 */
	private String dealId;
	
	private String envType;
	
	private String password;
	
	private String aliasName;

	public String getTerminalId() {
		return terminalId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getEnvType() {
		return envType;
	}

	public String getPassword() {
		return password;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public String getSignType() {
		return signType;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}
}
