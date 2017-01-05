package webtest;

import helppac.ExcelMain;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import page.SiteCustomBatchPage;
import page.SiteLogin;
import base.SysPara;

public class SiteCustomBatch {
	
	public static void main(String[] args) {
		
		ResourceBundle resource = SysPara.getResource(); 
		String name = "10012016111";
		String passwd = "1qaz";
		String host = resource.getString("sitehost");
//		String host = "https://192.168.1.132/site";
		

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(host+"/login.htm");		
		SiteLogin sl=new SiteLogin();
		boolean logflag=sl.login(name, passwd, driver);
		
		if(logflag){
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("memberCode", "10012016111");
			map.put("custom", "GZHG");
			map.put("checksource", "0");//1是check，其他不check
			String filename = "C:/Temp/custom.xls";// 生成海关申报文件的地址
			int linenum = 1;// 申报条数
			
			ExcelMain excel = new ExcelMain();
			boolean flag = excel.readWriter(map, linenum, filename);
			if(flag){
				SiteCustomBatchPage sitepage=new SiteCustomBatchPage();
				boolean upflag=sitepage.upload(driver, map, filename);
				if(upflag){
					System.out.println("导入文件成功");
				}
				else{
					System.out.println("导入文件失败");
				}
			}
			else{
				System.out.println("生成文件失败");
			}
			
		}
		else{
			System.out.println("登陆失败");
		}

		driver.quit();
	}

}
