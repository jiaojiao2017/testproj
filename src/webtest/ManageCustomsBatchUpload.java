package webtest;

import helppac.ExcelMain;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import page.CustomBatchImportPage;
import page.ManageLogin;

public class ManageCustomsBatchUpload {

	public static void main(String[] args) {

		String name = "admin1";
		String passwd = "123456";
//		String host = "https://192.168.1.132/manage";
		String host = "http://192.168.21.230:8083/manage";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(host + "/index");

		Map<String, String> map = new HashMap<String, String>();
		map.put("host", host);
		map.put("memberCode", "10012016111");
		map.put("custom", "QDHG");
		map.put("checksource", "1");//1是check，其他不check
		String filename = "C:/Temp/custom.xls";// 生成海关申报文件的地址
		int linenum = 10;// 申报条数

		ExcelMain excel = new ExcelMain();
		boolean flag = excel.readWriter(map, linenum, filename);

		ManageLogin lin = new ManageLogin();
		boolean flg = lin.login(name, passwd, driver);
		if (flag) {
			if (flg) {
				CustomBatchImportPage cusba = new CustomBatchImportPage();
				boolean uploadflg = cusba.upload(driver, map, filename);
				if (uploadflg) {
					System.out.println("导入文件完成");
				}
				else{
					System.out.println("导入文件失败");
				}
			}
			else{
				System.out.println("登陆失败");
			}
		}else{
			System.out.println("生成文件失败");
		}
			

		driver.quit();

	}

}
