package webtest;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import base.SysPara;
import page.SiteLogin;

public class SiteTest {
	
	public static void main(String[] args) {
		String name = "10012016111";
		String passwd = "1qaz";
		
		ResourceBundle resource = SysPara.getResource(); 
		String host = resource.getString("sitehost");
//		String host = "http://192.168.21.229/site";
//		String host = "https://192.168.1.132/site";
		

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(host+"/login.htm");		
		SiteLogin sl=new SiteLogin();
		sl.login(name, passwd, driver);

		driver.quit();
	}

}
