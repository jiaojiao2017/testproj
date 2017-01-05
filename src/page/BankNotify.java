package page;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BankNotify {
	
	
	public boolean success(WebDriver dr){
		
		//点击成功
		WebElement button = dr.findElement(By.tagName("button"));
		button.click();
		
		return true;

				
	}

}
