package testNGstudy;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNGstudy {

	@DataProvider(name = "user")
	public Object[][] Users() {
		return new Object[][] { { "root", "passowrd" },
				{ "cnblogs", "tankxiao" }, { "tank", "xiao" } };
	}

	@Test(dataProvider = "user")
	public void verifyUser(String userName, String password) {
		System.out.print("Username: "  + userName  + "\t" + " Password: "+ "\t"  + password);
		System.out.println();
	}

}