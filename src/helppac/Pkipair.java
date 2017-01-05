package helppac;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.commons.lang.StringUtils;


public class Pkipair {
	
	
//	private Bill99Logger logger = Bill99Logger.getLogger(this.getClass());
	
	public String signMsg( String signMsg, String pemPath, String membercode , String cerpwd
			, String ceralias) {

		String base64 = "";
		InputStream ksfis = this.getClass().getResourceAsStream("/cert/env/"+pemPath + "/" + membercode + ".pfx");
		try {
			String classPath = Pkipair.class.getResource("/").toString()
					.replace("file:/", "").replaceAll("%20", " ");
			if(!StringUtils.startsWith(classPath, "/")){
				classPath = "/" + classPath;
			}
			//linux
			/*ksfis = new FileInputStream(new File("/" + classPath + "cert/env/" + 
					//System.getProperty("STAGE_ID") +
					pemPath + "/" + membercode + ".pfx"));*/
			//windows
			/*System.out.println(new File(classPath + "cert/env/" + 
					//System.getProperty("STAGE_ID") +
					pemPath + "/" + membercode + ".pfx"));*/
			
			/*ksfis = new FileInputStream(new File(classPath + "cert/env/" + 
					//System.getProperty("STAGE_ID") +
					pemPath + "/" + membercode + ".pfx"));*/
			
			KeyStore ks = KeyStore.getInstance("PKCS12");

//			FileInputStream ksfis = new FileInputStream("/Users/chris/Documents/workspace/merchant-client-demo/src/main/java/Util/tester-rsa.pfx");
			
		/*	String file = Pkipair.class.getResource("tester-rsa.pfx").getPath().replaceAll("%20", " ");
			System.out.println(file);*/
//			
//			FileInputStream ksfis = new FileInputStream(file);
			
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);

			//证书密码
			char[] keyPwd = cerpwd.toCharArray();
			//char[] keyPwd = "YaoJiaNiLOVE999Year".toCharArray();
			ks.load(ksbufin, keyPwd);

			PrivateKey priK = (PrivateKey) ks.getKey(ceralias , keyPwd);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(signMsg.getBytes("utf-8"));
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			base64 = encoder.encode(signature.sign());
			
		} catch(FileNotFoundException e){
//			logger.error("证书文件不存在或路径错误");
			System.out.println("证书文件不存在或路径错误");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
//		logger.debug("signMsg="+base64);
		return base64;
	}
	
	public boolean enCodeByCerA(String val, String msg) {
		boolean flag = false;
		try {
			//����ļ�(����·��)
			InputStream inStream = new FileInputStream("/Users/chris/Downloads/atimes.rsa (9).cer");
			
			//����ļ�(���·��)
		//	String file = Pkipair.class.getResource("99bill[1].cert.rsa.20140803.cer").toURI().getPath();
			//System.out.println(file);
			//FileInputStream inStream = new FileInputStream(file);
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			//��ù�Կ
			PublicKey pk = cert.getPublicKey();
			//ǩ��
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes());
			//����
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			System.out.println(new String(decoder.decodeBuffer(msg)));
			flag = signature.verify(decoder.decodeBuffer(msg));
			System.out.println(	flag);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("no");
		} 
		return flag;
	}
	
	public boolean enCodeByCer( String val, String msg, String envType) {
		boolean flag = false;
		try {
			//����ļ�(����·��)
			FileInputStream inStream = null;
			String classPath = Pkipair.class.getResource("/").toString()
					.replace("file:/", "");
			//linux下最前面需要加上"/"
			inStream = new FileInputStream(new File("/" + classPath + "cert/env/" + 
					envType + "/" +"atimes.rsa.cer"));
			
			//windows下目录结构
			/*inStream = new FileInputStream(new File(classPath + "cert/env/" + 
					envType + "/" +"atimes.rsa.cer"));*/
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			//��ù�Կ
			PublicKey pk = cert.getPublicKey();
			//ǩ��
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes());
			//����
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			//System.out.println(new String(decoder.decodeBuffer(msg)));
			flag = signature.verify(decoder.decodeBuffer(msg));
			System.out.println("enCode " + flag);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("enCode fail");
		} 
		return flag;
	}
	
	/*public static void main(String[] args) {
		Pkipair pair = new Pkipair();
		String sourceMsg = "inputCharset=1&pageUrl=http://192.168.1.111:8081/QAMOCK/notifyReceiverPg.do&bgUrl=http://192.168.1.111:8081/QAMOCK/notifyReceiverBg.do&version=hw1.0&language=1&signType=4&merchantAcctId=1001215986501&payerName=李财水&payerContactType=2&payerContact=15800000000&payerIdentityCard=320125198805232313&"
				+ "orderId=201506011548413&orderCurrency=USD&settlementCurrency=USD&orderAmount=600&orderTime=600&productName=IPHONE6&productNum=1&productId=YN-00-01&productDesc=IPHONE6港版&ext1=ext1&ext2=ext2&payType=00&redoFlag=1";
		String msg ="be5nRiIOtD9Ch0GKAI0i7utq7ZHIdYwCyH9USC9zZwmtmc7MFRXXffEli21lDMqOReWFaH9E6fwv\nXrzTREkrcOHxZNFmqlzS1DRFH8pH1jK5XMxMyjhiES2+8J6FPXEjO61wPlDZVPFC31pp8VHZgilz\nxPR4OZRafMSKS4tYij8=";
		boolean flag = pair.enCodeByCer(sourceMsg, msg , "stage2");
		System.err.println("flag==="+flag);
		//pair.enCodeByCerA(val, msg);
	}*/
	
	public static void main(String[] args) {
		Pkipair pair = new Pkipair();
		String sourceMsg = "inputCharset=1&pageUrl=http://192.168.1.111:8081/QAMOCK/notifyReceiverPg.do&bgUrl=http://192.168.1.111:8081/QAMOCK/notifyReceiverBg.do&version=hw1.0&language=1&signType=4&merchantAcctId=1001215986501&payerName=李财水&payerContactType=2&payerContact=15800000000&payerIdentityCard=320125198805232313&"
				+ "orderId=201506011642415&orderCurrency=USD&settlementCurrency=USD&orderAmount=600&orderTime=20150601151622&productName=IPHONE6&productNum=1&productId=YN-00-01&productDesc=IPHONE6港版&ext1=ext1&ext2=ext2&payType=00&redoFlag=1";
		String msg = pair.signMsg(sourceMsg, "stage2", "10012159865", "99bill", "test-alias");
		System.out.print(msg);
	}
}
