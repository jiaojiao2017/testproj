package webtest;

import helppac.CsvWriter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import base.SysPara;
import base.SysParadev;
import page.BatchCBPage;
import page.ManageLogin;

public class ManageTest {
	public static void main(String[] args) {
		
		ResourceBundle resource = SysPara.getResource();  
		
		String name = resource.getString("name");
		String passwd =resource.getString("passwd");
		String host = resource.getString("host");
//		String host = "http://192.168.21.230:8083/manage";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(host+"/index");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("host", host);
		map.put("memberCode", "10012016111");
		map.put("targetCur", "CNY");
		if("CNY".equals(map.get("targetCur"))){
			map.put("terminalId", "test002");
		}
		else{
			map.put("terminalId", "test001");
		}
		
		String file="C:/Temp/createCSV.csv";
		CsvWriter cw=new CsvWriter();
		cw.createCSV(file, map.get("targetCur"), 20);
		
		ManageLogin lin=new ManageLogin();
		boolean flg=lin.login(name, passwd,driver);
		if(flg){
			//批量预校验
//			BatchIdCheckPage bicp=new BatchIdCheckPage();
			//批量跨境管理
			BatchCBPage bicp=new BatchCBPage();
			//批量购汇管理
//			BatchHWPage bicp=new BatchHWPage();
			boolean submitflg=bicp.upload(driver,map,file);
			if(submitflg){
				System.out.println("提交成功");
			}
			else{
				System.out.println("提交失败");
			}
			
		}
		driver.quit();
	}

}
