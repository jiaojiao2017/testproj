package page;

public class ResultCheck {
	
	public boolean check(String source){
		
		int index=source.lastIndexOf("支付结果");
		String result=source.substring(index+15,index+17);
		if("10".equals(result)){
			System.out.println("支付成功");
			return true;
		}
		else if("11".equals(result)){
			System.out.println("支付失败");
			return false;
		}
		else{
			System.out.println("未知结果："+result);
			return false;
		}
		
	}

}
