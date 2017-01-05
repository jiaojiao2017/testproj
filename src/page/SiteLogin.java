package page;

import helppac.OCRHelper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SiteLogin {

	private static String name;
	private static String passwd;
	private static String directory = "C:\\Temp\\input\\sitepic.jpg";

	public boolean login(String name, String passwd, WebDriver driver) {
		this.name = name;
		this.passwd = passwd;
		boolean flg = false;

		do {
			sendkeys(driver);
			try {
				Alert confirm = driver.switchTo().alert();
				confirm.accept();
				flg = true;
			} catch (NoAlertPresentException Ex) {
				flg = false;
			}
		} while (flg);
		
		//如果弹出load data error要点掉

		WebDriverWait wait = new WebDriverWait(driver, 3);
        try {
            Alert alert = wait.until(new ExpectedCondition<Alert>() {
                @Override
                public Alert apply(WebDriver driver) {
                    try {
                        return driver.switchTo().alert();
                    } catch (NoAlertPresentException e) {
                        return null;
                    }
                }
            });
            alert.accept();
        } catch (TimeoutException e) {
            /* Ignore */
            System.out.println("没有弹出错误");
        }

		// 如果出现了错误弹窗，点掉
//		int time = 3;// 等待弹出弹框时间
//		boolean alertflg = true;// 出现弹框则不再等待
//
//		while (time > 0 && alertflg) {
//			try {
//				Thread.sleep(1000);
//				System.out.println("等待1s");
//				Alert error = driver.switchTo().alert();
//				error.accept();
//				alertflg = false;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (NoAlertPresentException Ex) {
//				time--;
//			}
//		}

		// 转换为中文
		if (driver.getPageSource().contains("简体中文")) {
			WebElement language = driver.findElement(By.id("languageBtn"));
			language.click();

			//如果弹出load data error要点掉

			WebDriverWait waiterror = new WebDriverWait(driver, 3);
	        try {
	            Alert alert = waiterror.until(new ExpectedCondition<Alert>() {
	                @Override
	                public Alert apply(WebDriver driver) {
	                    try {
	                        return driver.switchTo().alert();
	                    } catch (NoAlertPresentException e) {
	                        return null;
	                    }
	                }
	            });
	            alert.accept();
	        } catch (TimeoutException e) {
	            /* Ignore */
	            System.out.println("没有弹出错误");
	        }

		}

		// 等待页面加载完成
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.linkText("      创建支付订单"));
			}
		});

		// 判断是否登陆成功
		if (driver.getCurrentUrl().contains("index")) {
			System.out.println("登录成功");
			return true;
		}

		return false;

	}

	public static void sendkeys(WebDriver driver) {
		WebElement usr = driver.findElement(By
				.xpath("//input[@name='account']"));
		usr.sendKeys(name);
		WebElement pwd = driver.findElement(By
				.xpath("//input[@name='password']"));

		pwd.sendKeys(passwd);
		WebElement cappic = driver.findElement(By
				.xpath("//img[@id='captchaImg']"));
		// 保存验证码文件
		try {
			createElementImage(cappic, driver);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OCRHelper oh = new OCRHelper();
		File testData = new File(directory);
		try {
			String capt = oh.recognizeText(testData);

			System.out.println("验证码：" + capt);
			WebElement captxt = driver.findElement(By
					.xpath("//input[@name='captcha']"));
			captxt.sendKeys(capt);

			WebElement login = driver.findElement(By
					.xpath("//a[@class='submit btn login-btn']"));
			login.click();

			System.out.println("点击登录");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BufferedImage createElementImage(WebElement webElement,
			WebDriver driver) throws IOException {
		// 获得webElement的位置和大小。
		Point location = webElement.getLocation();
		Dimension size = webElement.getSize();

		// 创建全屏截图。
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(
				takeScreenshot(driver)));
		// 截取webElement所在位置的子图。*3/2为实际截图后获取的结果，即扩大1.5倍时可正确保存图片
		BufferedImage croppedImage = originalImage.getSubimage(
				location.getX() * 3 / 2, location.getY() * 3 / 2,
				size.getWidth() * 3 / 2, size.getHeight() * 3 / 2);
		File file = new File(directory);
		ImageIO.write(croppedImage, "jpg", file);
		return croppedImage;
	}

	public static byte[] takeScreenshot(WebDriver driver) throws IOException {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		return takesScreenshot.getScreenshotAs(OutputType.BYTES);
	}

	public static void main(String[] args) {

		String name = "10012016111";
		String passwd = "1qaz";
		String host = "https://192.168.1.132/site";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(host + "/login.htm");

		SiteLogin sl = new SiteLogin();
		sl.login(name, passwd, driver);

		driver.quit();

	}

}
