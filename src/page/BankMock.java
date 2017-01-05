package page;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankMock {
	
	public Map<String,String> parser(String sr){
		int index = sr.indexOf("OrdId");
		String dealidString = sr.substring(index + 14);
		
		Pattern p0 = Pattern.compile("^[A-Za-z0-9]+");
		Matcher m0 = p0.matcher(dealidString);
		String dealid = null;
		if (m0.find()) {
			dealid = m0.group();
//			System.out.println("checkval:"+checkval);
		}
		System.out.println("dealid:" + dealid);

		int checkvalue = sr.indexOf("ChkValue");
		String checkString = sr.substring(checkvalue + 17);
		Pattern p = Pattern.compile("^[A-Za-z0-9]+");
		Matcher m = p.matcher(checkString);
		String checkval = null;
		if (m.find()) {
			checkval = m.group();
//			System.out.println("checkval:"+checkval);
		}
		
		int amtindex = sr.indexOf("OrdAmt");
		String amtString = sr.substring(amtindex + 15);
		
//		System.out.println("amtString:"+amtString);
		Pattern p1 = Pattern.compile("^[0-9]\\d*\\.\\d*");
		Matcher m1 = p1.matcher(amtString);
		String amt = null;
		if (m1.find()) {
			amt = m1.group();
			System.out.println("amt:" + amt);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("dealid", dealid);
		map.put("checkvalue", checkval);
		map.put("amt", amt);
		
		
		return map;
		
	}

}
