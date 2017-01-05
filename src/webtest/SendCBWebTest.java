package webtest;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import page.BankNotify;
import page.ResultCheck;
import page.SendCB;
import base.SysPara;

public class SendCBWebTest {
	
	public static void main(String[] args) {
		ResourceBundle resource = SysPara.getResource(); 
		String host = resource.getString("mockhost");;

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		//跨境
//		driver.get(host + "sendCB.do");
		//海外购
		driver.get(host + "sendHW.do");

		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("host", host);
		map.put("merchantAcctId", "1001201611101");
		map.put("terminalId", "test001");
		map.put("amt", "600");
		
		SendCB sc=new SendCB();
		if(sc.sendkey(driver, map)){
			BankNotify bn=new BankNotify();
			if(bn.success(driver)){
				String resultsource=driver.getPageSource();
				ResultCheck rc=new ResultCheck();
				rc.check(resultsource);
			}
			else{
				System.out.println("点击银行通知页面失败");
			}
				
		}
		else{
			System.out.println("提交失败");
		}
		
		
		driver.quit();
		
	}

}
