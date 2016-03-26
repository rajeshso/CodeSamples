package com.db.tw.distribution.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
import com.db.tw.distribution.config.JMSBlock;
import com.db.tw.distribution.publisher.Publisher;
import com.db.tw.distribution.publisher.PublisherFactory;
import com.db.tw.distribution.publisher.PublisherRegistry;
import com.db.tw.distribution.utils.BlockUtils;

public class PublisherRegistryTest {

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
	public final void testGetInstance() {
		PublisherRegistry publisherRegistry1 = PublisherRegistry.getInstance();
		PublisherRegistry publisherRegistry2 = PublisherRegistry.getInstance();
		int publisherRegistry1Hashcode = publisherRegistry1.hashCode();
		int publisherRegistry2Hashcode = publisherRegistry2.hashCode();
		assertTrue(publisherRegistry1Hashcode == publisherRegistry2Hashcode);
	}

	@Test
	public final void testInitPositive() {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			if (jmsProp.containsKey("MYJMS1.factory")) {
				System.out.println("NYJMS1.factory is present");
			}
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(BlockManager.getConfigList());
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			assertEquals(
					"com.db.tw.distribution.adapters.TopicPublisherFactory",
					publisherFactory.getClass().getName());
		} catch (Exception e) {
			fail("Problem with initializing the PublisherRegistry");
			e.printStackTrace();
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public final void testInitWithoutArgsPositive() {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(new HashSet());
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			assertEquals(
					"com.db.tw.distribution.adapters.TopicPublisherFactory",
					publisherFactory.getClass().getName());
		} catch (Exception e) {
			fail("Problem with initializing the PublisherRegistry");
			e.printStackTrace();
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public final void testInitWithNull() {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			BlockUtils.createPropertiesFile(jmsProp, configFile);

			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			assertEquals(
					"com.db.tw.distribution.adapters.TopicPublisherFactory",
					publisherFactory.getClass().getName());
		} catch (Exception e) {
			fail("Problem with initializing the PublisherRegistry");
			e.printStackTrace();
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test(expected = Exception.class)
	public final void testInitWithoutFactoryKey() throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(0);
			jmsProp.remove("MYJMS1.factory");
			BlockUtils.createPropertiesFile(jmsProp, configFile);

			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			assertEquals(
					"com.db.tw.distribution.adapters.TopicPublisherFactory",
					publisherFactory.getClass().getName());
			fail("Problem with initializing the PublisherRegistry: Expected an exception");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test(expected = Exception.class)
	public final void testInitWithIncorrectFactoryKey() throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(1);
			jmsProp.put("MYJMS1.factory", "ABCDE");
			BlockUtils.createPropertiesFile(jmsProp, configFile);

			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = BlockManager.getConfigList().iterator().next();
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			fail("Problem with initializing the PublisherRegistry: Expected an exception");
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Factory " + "ABCDE" + " not in class path for  "
					+ "MYJMS1", e.getMessage());
			throw e;
		} finally {
			try {
				BlockUtils.deletePropertiesFile(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public final void testgetPublisherFactoryWithIncorrectIBlock()
			throws Exception {
		PublisherRegistry publisherRegistry = PublisherRegistry.getInstance();
		try {
			Properties jmsProp = BlockUtils.getJMSBlock(0);
			BlockUtils.createPropertiesFile(jmsProp, configFile);
			BlockManager.init();
			publisherRegistry.init(null);
			IBlock iBlock = new JMSBlock();
			iBlock.setBlockNameValue("ABCDE");
			PublisherFactory<? extends Publisher> publisherFactory = publisherRegistry
					.getPublisherFactory(iBlock);
			Assert.assertNull(publisherFactory);
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

}
