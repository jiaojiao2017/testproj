package page;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BatchHWPage {

	private String url = "/batchExReq/add?showPages=showPages";

	public boolean upload(WebDriver dr, Map<String, String> map, String csvfile) {

		dr.get(map.get("host") + url);

		WebElement member = dr.findElement(By.id("memberCode"));
		member.sendKeys(map.get("memberCode"));

		WebElement terminalId = dr.findElement(By.id("terminalId"));
		terminalId.sendKeys(map.get("terminalId"));

		// 指定外币为1，指定人民币为2
		Select batchExType = new Select(dr.findElement(By.id("batchExType")));
		batchExType.selectByValue("1");// 跨境人民币

		String cur = map.get("targetCur");
		Select targetCur = new Select(dr.findElement(By.id("targetCur")));
		targetCur.selectByValue(cur);

		WebElement file = dr.findElement(By.id("_batchFile"));
		file.sendKeys(csvfile);

		//去掉身份校验
//		WebElement checkid = dr.findElement(By.name("idCheck"));
//		checkid.click();
		
		WebElement button = dr.findElement(By.id("btn_search"));
		button.click();

		// 确认提交
		Alert confirm = dr.switchTo().alert();
		confirm.accept();

		return true;

	}

}
