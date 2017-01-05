package studycase;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class printpic extends AbstractJavaSamplerClient {

	@Override
	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();
		params.addArgument("line", "5"); // 定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
		System.out.println("Get Parameter name! [getDefaultParameters]");
		return params;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {

		String num = arg0.getParameter("line");
		int linenum = Integer.parseInt(num);
		for (int i = 0; i < linenum; i++) {
			int t = 0; // t是用来存放距离边界需要多少空格
			if (i >= linenum / 2) {// >是m为奇数的情况下的判断，=是m为偶数的情况下的判断，这里将过半的那些i值转换为与它对称的i值
				t = linenum - 1 - i;
			} else {
				t = i;
			}
			for (int j = 1; j <= linenum; j++) {
				if (j >= (linenum + 1) / 2 - t && j <= (linenum + 1) / 2 + t) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
		return null;
	}

}
