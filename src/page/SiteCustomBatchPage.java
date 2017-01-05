package page;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class SiteCustomBatchPage {

	public boolean upload(WebDriver dr, Map<String, String> map, String filename) {

		WebElement menu = dr.findElement(By.linkText("      申报批次管理"));
		menu.click();

		WebElement upload = dr.findElement(By.xpath("//input[@value='导入']"));
		upload.click();

		WebElement file = dr.findElement(By.id("_batchFile"));
		file.sendKeys(filename);

		// 是否检验交易来源
		if ("1" != map.get("checksource")) {
			WebElement checkid = dr.findElement(By.name("checkFlag"));
			checkid.click();
		}

		WebElement button = dr.findElement(By.id("btn_search"));
		button.click();
		// 确认提交
		Alert confirm = dr.switchTo().alert();
		confirm.accept();

		if (dr.getPageSource().contains("批次导入成功")) {
			return true;
		} else {
			return false;
		}

	}

}
