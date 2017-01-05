package studycase;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class LifecycleJMeterSample extends AbstractJavaSamplerClient {
	@Override
	public Arguments getDefaultParameters() {
		System.out.println("Get Parameter name! [getDefaultParameters]");
		return super.getDefaultParameters();
	}

	@Override
	public void setupTest(JavaSamplerContext context) {
		System.out.println("[setupTest]");
		super.setupTest(context);
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		System.out.println("[teardownTest]");
		super.teardownTest(context);
	}

	@Override
	public SampleResult runTest(JavaSamplerContext ctx) {
		SampleResult result = new SampleResult();
		result.sampleStart();
		System.out.println("[runTest]");
		result.setSuccessful(true);
		result.sampleEnd();
		return result;
	}
}
