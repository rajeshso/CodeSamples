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

import com.db.tw.distribution.adapters.JMSTopicPublisher;
import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.config.JMSBlock;
import com.db.tw.distribution.publisher.Publisher;
import com.db.tw.distribution.publisher.PublisherFactory;
import com.db.tw.distribution.publisher.PublisherRegistry;
import com.db.tw.distribution.utils.BlockUtils;

public class PublisherFactoryTest {
	
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
	public final void testPublisherFactory() {

	}

	@Test
	public final void testGetPublisher() {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			JMSTopicPublisher jmsTopicPublisher = (JMSTopicPublisher) publisherFactory.getPublisher(iBlock);
			Assert.assertNotNull(jmsTopicPublisher);
		} catch (Exception e) {
			fail("Problem with getPublisherFactory of the PublisherRegistry: Did not Expecte an exception");
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Test(expected=Exception.class)
	public void testGetPublisherWithNullIBlock() throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			JMSTopicPublisher jmsTopicPublisher = (JMSTopicPublisher) publisherFactory.getPublisher(null);
			fail("Exception for null iBlock is not thrown");
		} catch (Exception e) {
			assertEquals("iblock is invalid", e.getMessage());
			throw e;
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	@Test(expected=Exception.class)
	public void testGetPublisherWithNullIBlockName() throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			iBlock.setBlockNameValue(null);
			JMSTopicPublisher jmsTopicPublisher = (JMSTopicPublisher) publisherFactory.getPublisher(iBlock);
			fail("Exception for null iBlock is not thrown");
		} catch (Exception e) {
			assertEquals("iblock is invalid", e.getMessage());
			throw e;
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}		
	@Test(expected=Exception.class)
	public void testGetPublisherWithEmptyIBlockName() throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			iBlock.setBlockNameValue(null);
			JMSTopicPublisher jmsTopicPublisher = (JMSTopicPublisher) publisherFactory.getPublisher(iBlock);
			fail("Exception for null iBlock is not thrown");
		} catch (Exception e) {
			assertEquals("iblock is invalid", e.getMessage());
			throw e;
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	@Test(expected=Exception.class)
	public void testGetPublisherWithNullIBlockConfigProperty() throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			iBlock.setConfigProperty(null);
			JMSTopicPublisher jmsTopicPublisher = (JMSTopicPublisher) publisherFactory.getPublisher(iBlock);
			fail("Exception for null iBlock is not thrown");
		} catch (Exception e) {
			assertEquals("iblock's config property is missing", e.getMessage());
			throw e;
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}		
	@Ignore
	public final void testCreatePublisher() {
		fail("Not yet implemented"); // TODO
	}
}
