package com.db.tw.distribution.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BlockUtils {

	public BlockUtils() {
		// TODO Auto-generated constructor stub
	}
	public static void createEnvValueCONFIG_FILE_LOC(String configFile) {
		Map<String, String> envProp = new HashMap<String, String>();
		envProp.put("CONFIG_FILE_LOC", configFile);
		setEnv(envProp);
	}

	public static Properties getJMSBlock(int blockID) {
		Properties configProp = new Properties();
		configProp.setProperty("BLOCK"+blockID, "MYJMS1");
		configProp.setProperty("MYJMS1.CONFIGTYPE", "JMS");
		configProp.setProperty("MYJMS1.BKEYLIST", "BUSKEY.ASSET, BUSKEY.JUR");
		configProp.setProperty("MYJMS1.brokerURL", "tcp://localhost:61616");
		configProp.setProperty("MYJMS1.topicName", "dtcc.us.masterbroker");
		configProp.setProperty("MYJMS1.SERVERID", "jmsConnectionFactory");
		configProp.setProperty("MYJMS1.factory",
				"com.db.tw.distribution.adapters.TopicPublisherFactory");
		configProp.setProperty("MYJMS1.retryCounter", "3");
		configProp.setProperty("MYJMS1.BUSKEY.ASSET", "FX");
		configProp.setProperty("MYJMS1.BUSKEY.JUR", "ASIC,EMIR, HKMA");
		return configProp;
	}
	public static Properties getFileBlock(int blockNumber, String filePathLocation) {
		Properties configProp = new Properties();
		configProp.setProperty("BLOCK"+blockNumber, "MYFILE1");
		configProp.setProperty("MYFILE1.CONFIGTYPE", "SFTP");
		configProp.setProperty("MYFILE1.BKEYLIST", "BUSKEY.ASSET, BUSKEY.JUR");
		configProp.setProperty("MYFILE1.filepath", filePathLocation);
		configProp.setProperty("MYFILE1.SERVERID", "jmsConnectionFactory");
		configProp.setProperty("MYFILE1.factory",
				"com.db.tw.distribution.adapters.FilePublisherFactory");
		configProp.setProperty("MYFILE1.BUSKEY.ASSET", "FX");
		configProp.setProperty("MYFILE1.BUSKEY.JUR", "ASIC,EMIR, HKMA");
		return configProp;
	}

	public static void createPropertiesFile(Properties jmsProp, String configFile2)
			throws IOException {
		// For a simple file system with windows-style paths and behaviour:
		Path dirPath = Paths.get(configFile2);
		if (Files.exists(dirPath)) {
			deletePropertiesFile(configFile2);
		}
		Path filePath = Files.createFile(dirPath);
		OutputStream out = new BufferedOutputStream(Files.newOutputStream(
				filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND));
		jmsProp.store(out, "Configuration Properties");
		out.close();
		if (Files.exists(dirPath)) {
			System.out.println(dirPath + " exists");
			print(configFile2); 
		}
	}
    private static void print(String file) throws IOException 
    {
    	RandomAccessFile aFile = new RandomAccessFile
                (file, "r");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(inChannel.read(buffer) > 0)
        {
            buffer.flip();
            for (int i = 0; i < buffer.limit(); i++)
            {
                System.out.print((char) buffer.get());
            }
            buffer.clear(); // do something with the data and clear/compact it.
        }
        inChannel.close();
        aFile.close();
    }
	
	public static void deletePropertiesFile(String configFile2) throws IOException {
		Path p = Paths.get(configFile2);
		Files.deleteIfExists(p);
	}

	// A hack to set the environment variable for CONFIG_LOC
	public static void setEnv(Map<String, String> newenv) {
		try {
			Class<?> processEnvironmentClass = Class
					.forName("java.lang.ProcessEnvironment");
			Field theEnvironmentField = processEnvironmentClass
					.getDeclaredField("theEnvironment");
			theEnvironmentField.setAccessible(true);
			Map<String, String> env = (Map<String, String>) theEnvironmentField
					.get(null);
			env.putAll(newenv);
			Field theCaseInsensitiveEnvironmentField = processEnvironmentClass
					.getDeclaredField("theCaseInsensitiveEnvironment");
			theCaseInsensitiveEnvironmentField.setAccessible(true);
			Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField
					.get(null);
			cienv.putAll(newenv);
		} catch (NoSuchFieldException e) {
			try {
				Class[] classes = Collections.class.getDeclaredClasses();
				Map<String, String> env = System.getenv();
				for (Class cl : classes) {
					if ("java.util.Collections$UnmodifiableMap".equals(cl
							.getName())) {
						Field field = cl.getDeclaredField("m");
						field.setAccessible(true);
						Object obj = field.get(env);
						Map<String, String> map = (Map<String, String>) obj;
						map.clear();
						map.putAll(newenv);
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
