package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class JhApprove {
	private String host="https://192.168.1.132/manage";
	private String url="/sellRemitReq/review/pre?remitReqid=";
	public boolean approve(WebDriver dr){
		
		
//		WebElement df_apply = dr.findElement(By.xpath("//span[@id='leftmenu_65_span']"));
//		df_apply.click();
		
		dr.get(host+url);
		
		WebElement approve = dr.findElement(By.linkText("审核"));
		approve.click();
		
		
		
		return false;
		
	}

}
