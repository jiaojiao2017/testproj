package page;

import helppac.OCRHelper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManageLogin {

	private static String directory = "C:\\Temp\\input\\pic.jpg";

	public boolean login(String name, String passwd, WebDriver driver) {

		ManageLogin mlin = new ManageLogin();
		try {

			do {

				WebElement usr = driver.findElement(By
						.xpath("//input[@id='account']"));
				usr.sendKeys(name);
				WebElement pwd = driver.findElement(By
						.xpath("//input[@id='password']"));
				pwd.sendKeys(passwd);
				WebElement cappic = driver.findElement(By
						.xpath("//img[@id='captchaImg']"));
				// 保存验证码文件
				mlin.createElementImage(cappic, driver);
				OCRHelper oh = new OCRHelper();
				File testData = new File(directory);
				try {
					String capt = oh.recognizeText(testData);

					System.out.println("验证码：" + capt);
					WebElement captxt = driver.findElement(By
							.xpath("//input[@id='captcha']"));
					captxt.sendKeys(capt);

					WebElement login = driver.findElement(By
							.xpath("//input[@value='Login']"));
					login.click();

					System.out.println("点击登录");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (driver.getCurrentUrl().contains("login"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (driver.getCurrentUrl().contains("index")) {
			System.out.println("登录成功");
		}

		while(!driver.getPageSource().contains("产品运营")){

		// 等待页面加载完成
		try {
			Thread.sleep(1000);
			System.out.println("等待1s");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		return true;

	}

	public BufferedImage createElementImage(WebElement webElement,
			WebDriver driver) throws IOException {
		// 获得webElement的位置和大小。
		Point location = webElement.getLocation();
		Dimension size = webElement.getSize();
		// 创建全屏截图。
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(
				takeScreenshot(driver)));
		// 截取webElement所在位置的子图。
		BufferedImage croppedImage = originalImage.getSubimage(location.getX()*3/2
				, location.getY()*3/2, size.getWidth()*3/2,
				size.getHeight()*3/2);
		File file = new File(directory);
		ImageIO.write(croppedImage, "jpg", file);
		return croppedImage;
	}

	public byte[] takeScreenshot(WebDriver driver) throws IOException {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		return takesScreenshot.getScreenshotAs(OutputType.BYTES);
	}

}
