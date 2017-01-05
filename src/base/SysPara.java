package base;

import java.util.ResourceBundle;

public class SysPara {
	/**
	 * prop.env.test.value为汇付测试环境
	 * prop.env.dev.value为研发st3环境
	 */
	

	static ResourceBundle resource = ResourceBundle.getBundle("prop.env.test.value");

	public static ResourceBundle getResource() {
		return resource;
	}

	public static void setResource(ResourceBundle resource) {
		SysPara.resource = resource;
	}

}
