package com.db.tw.distribution.integration;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.db.tw.distribution.adapters.FilePublisher;
import com.db.tw.distribution.utils.BlockUtils;

public class FilePublisherTest {

	private String filepathLocation = "src/main/resources/";
	
	private static String configFile = "src/main/resources/testConfig.properties";
	
	@BeforeClass
	public static void setUp() throws Exception {
		BlockUtils.createEnvValueCONFIG_FILE_LOC(configFile);
	}

	@After
	public void tearDown() throws Exception {
		BlockUtils.deletePropertiesFile(configFile);
	}

	@Ignore
	public final void testFilePublisherString() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore
	public final void testGetFileLocation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testExecute() {
		FilePublisher filePublisher = new FilePublisher(filepathLocation);
		filePublisher.setPayLoad("great");
		try {
			filePublisher.execute();
		} catch (Exception e) {
			fail("File Publisher execute failed for " + e.getMessage());
			e.printStackTrace();
		}
		File dir = new File(filepathLocation);

		File[] xmlFiles = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File folder, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});
		Assert.assertTrue(xmlFiles.length > 0);
		deleteXMLFile(xmlFiles);
	}
	
	private void deleteXMLFile(File[] files) {
		for(int i=0; i<files.length;i++) {
			files[i].delete();
		}
	}
}
