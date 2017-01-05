package page;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BatchIdCheckPage {

	private String url = "/batchExReq/preChkAdd";

	public boolean upload(WebDriver dr, Map<String, String> map,String csvfile) {


		dr.get(map.get("host") + url);

		WebElement member = dr.findElement(By.id("memberCode"));
		member.sendKeys(map.get("memberCode"));

		WebElement terminalId = dr.findElement(By.id("terminalId"));
		terminalId.sendKeys(map.get("terminalId"));

		String cur=map.get("targetCur");
		Select targetCur = new Select(dr.findElement(By.id("targetCur")));
		targetCur.selectByValue(cur);

		Select batchExType = new Select(dr.findElement(By.id("batchExType")));
		if ("CNY".equals(cur)) {
			batchExType.selectByValue("3");//跨境人民币
		}
		else{
			batchExType.selectByValue("1");
		}
		
		WebElement file = dr.findElement(By.id("_batchFile"));
		file.sendKeys(csvfile);
		
		WebElement button = dr.findElement(By.id("btn_search"));
		button.click();
		//确认提交
		Alert confirm = dr.switchTo().alert();
		confirm.accept();
		//显示处理中
		Alert confirm1 = dr.switchTo().alert();
		confirm1.accept();
		
		if(dr.getPageSource().contains("预校验处理中")){
			return true;
		}
		else{
			return false;
		}

	}

}
