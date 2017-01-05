package page;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BatchCBPage {
	
	
	private String url = "/batchCrossReq/add?showPages=showPages";

	public boolean upload(WebDriver dr, Map<String, String> map, String csvfile) {

		dr.get(map.get("host") + url);

		WebElement member = dr.findElement(By.id("memberCode"));
		member.sendKeys(map.get("memberCode"));

		WebElement terminalId = dr.findElement(By.id("terminalId"));
		terminalId.sendKeys(map.get("terminalId"));


		WebElement file = dr.findElement(By.id("_batchFile"));
		file.sendKeys(csvfile);

		//去掉身份校验
		WebElement checkid = dr.findElement(By.name("idCheck"));
		checkid.click();
		
		WebElement button = dr.findElement(By.id("btn_search"));
		button.click();

		// 确认提交
		Alert confirm = dr.switchTo().alert();
		confirm.accept();

		return true;

	}

}
