package com.sky.detector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {
	private static final String DETECTOR_CONFIG_FILE_NAME = "lms.properties";
	public static void main(String[] args) {
		Test t = new Test();
		try {
			Properties p = t.getProps(DETECTOR_CONFIG_FILE_NAME);
			System.out.println(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties getProps(String propFileName) throws IOException {
		Properties resultProps = new Properties();
		InputStream ifs = this.getClass().getClassLoader().getResourceAsStream(propFileName);
		if (ifs!=null) {
			resultProps.load(ifs);
		}else {
			throw new FileNotFoundException("Property File "+propFileName +" not found in the classpath");
		}
		return resultProps;
	}
}
