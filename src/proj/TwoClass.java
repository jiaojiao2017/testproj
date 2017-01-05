/**
 * 
 */
package proj;

/**
 * @author jiaojiao.ma
 *
 */
public class TwoClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TwoClass tc= new TwoClass();
		tc.output();
		tc.output2();
		// TODO Auto-generated method stub

	}
	
	public void output(){
		System.out.println("hello");
	}
	
	public void output2(){
		this.output();
		System.out.println("hello1");
	}

}
