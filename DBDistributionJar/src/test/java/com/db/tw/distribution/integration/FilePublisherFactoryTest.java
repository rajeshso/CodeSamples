package com.db.tw.distribution.integration;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.db.tw.distribution.adapters.FilePublisher;
import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.publisher.Publisher;
import com.db.tw.distribution.publisher.PublisherFactory;
import com.db.tw.distribution.publisher.PublisherRegistry;
import com.db.tw.distribution.utils.BlockUtils;

public class FilePublisherFactoryTest {

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

	@Test
	public final void testFilePublisherFactory() {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties fileProp = BlockUtils.getFileBlock(1, filepathLocation);
			BlockUtils.createPropertiesFile(fileProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			FilePublisher filePublisher = (FilePublisher) publisherFactory.getPublisher(iBlock);
			Assert.assertNotNull(filePublisher);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Problem with getPublisherFactory of the PublisherRegistry: Did not Expecte an exception");
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Ignore
	public final void testCreatePublisherIBlock() {
		fail("Not yet implemented"); // TODO
	}

}
