package proj;

public class IDVerify {
	
	final int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
	final int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2}; 
	private int[] ai = new int[18];

	public static void main(String[] args) {
		String eightcardid=new String("511526198106104173");
		String remaining =new IDVerify().getVerify(eightcardid);
		
		System.out.println("remaining is "+remaining);
		// TODO Auto-generated method stub

	}
	
	public String getVerify(String eightcardid) {  
        int remaining = 0;  

        if (eightcardid.length() == 18) {  
              eightcardid = eightcardid.substring(0, 17);  
        }  

        if (eightcardid.length() == 17) {  
              int sum = 0;  
              for (int i = 0; i < 17; i++) {  
                    String k = eightcardid.substring(i, i + 1);  
                    ai[i] = Integer.parseInt(k);  
              }  

              for (int i = 0; i < 17; i++) {  
                    sum = sum + wi[i] * ai[i];  
              }  
              remaining = sum % 11;  
        }  

        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);  
  }

}
