package page;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomBatchImportPage {

	private String url = "/customImportBat/import?showPages=showPages";

	public boolean upload(WebDriver dr, Map<String, String> map, String filename) {

		dr.get(map.get("host") + url);

		WebElement member = dr.findElement(By.id("memberCode"));
		member.sendKeys(map.get("memberCode"));

		WebElement file = dr.findElement(By.id("_batchFile"));
		file.sendKeys(filename);

		//是否检验交易来源
		if ("1" != map.get("checksource")) {
			WebElement checkid = dr.findElement(By.name("checkFlag"));
			checkid.click();
		}

		WebElement button = dr.findElement(By.id("btn_search"));
		button.click();
		// 确认提交
		Alert confirm = dr.switchTo().alert();
		confirm.accept();

		if (dr.getPageSource().contains("申报文件导入成功")) {
			return true;
		} else {
			return false;
		}
	}

}
