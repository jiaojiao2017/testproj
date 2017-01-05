package page;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SendCB {
	
	
	public boolean sendkey(WebDriver dr,Map<String,String> map){
				
				
		WebElement merchant = dr.findElement(By.name("merchantAcctId"));
		merchant.clear();
		merchant.sendKeys(map.get("merchantAcctId"));
		
		WebElement terminalId = dr.findElement(By.name("terminalId"));
		terminalId.clear();
		terminalId.sendKeys(map.get("terminalId"));
		
		if("600"!=map.get("amt")){
			WebElement amt = dr.findElement(By.name("orderAmount"));
			amt.clear();
			amt.sendKeys(map.get("amt"));
		}
		
		Select select = new Select(dr.findElement(By.id("submiturl")));
		if(map.get("host").contains("1.111")){
			select.selectByVisibleText("st3");
		}
		else if(map.get("host").contains("21.229")){
			select.selectByVisibleText("huifu_dev");
		}
		
		WebElement submit = dr.findElement(By.name("submit"));
		submit.click();
		
		return true;
		
	}
	
	public static void main(String[] args) {
		String host = "http://192.168.1.111:8081/QAMOCK-Test/";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(host + "sendCB.do");
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("host", host);
		map.put("merchantAcctId", "1001201611101");
		map.put("terminalId", "test002");
		map.put("amt", "600");
		
		SendCB sc=new SendCB();
		sc.sendkey(driver, map);
		
		BankNotify bn=new BankNotify();
		bn.success(driver);
		
		
	}

}
