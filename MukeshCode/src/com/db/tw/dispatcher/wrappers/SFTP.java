package com.db.tw.dispatcher.wrappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;
/*import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpProgressMonitor;
*/
public class SFTP {

	// This Data Will come from the Configuration Object we have been hard
	// coding to see if this works

	private static String serverAddress = "127.0.0.10";
	private static String userId = "Test";
	private static String password = "test";
	private static String remoteDirectory = "\\";
	private static String localDirectory = "C:\\TestSFTPClient\\";
	private int port = 22;

	private static String fileToSend = "OSM 7.2.4.pdf";

	static Properties props;

	public static void main(String[] args) {

		SFTP sendMyFiles = new SFTP();
		// if (args.length < 1) {
		// System.err.println("Usage: java "
		// + sendMyFiles.getClass().getName()
		// + " Properties_file File_To_FTP ")
		// System.exit(1);
		// }

		// String propertiesFile = args[0].trim();
		// String fileToFTP = args[1].trim();

		// BlockManager.init();

		System.out.println("Matching:");
		Map<String, List<String>> bkeyMap = new HashMap<String, List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("FX");
		
		bkeyMap.put("ASSET", values);
		System.out.println("BKey BUSKEY.ASSET " + values);
		
		List<String> values1 = new ArrayList<String>();
		values1.add("EMIR");
		values1.add("ASIC");
		
		bkeyMap.put("JUR", values1);
		System.out.println("BKey BUSKEY.JUR " + values1);
		
		BlockManager.init();
		 Set<IBlock> iBlocks = BlockManager.getConfigList();
		 System.out.println("iBlocks = "+ iBlocks);
		 Set<String> configTypes = BlockManager.getConfigTypes();
		 System.out.println("iBlock Types = "+ configTypes);
		 Set<IBlock> iBlocksForJMS = BlockManager.getIBlocks("JMS");
		 System.out.println("IBlockForJMS = "+ iBlocksForJMS);
/*		Set<IBlock> mathingConfig = BlockManager
				.getMatchingConfigsForBKeys(bkeyMap);
		
		Iterator<IBlock> matchingConfigIterator = mathingConfig.iterator();
		int numberOfConfigBlocks=0;
		System.out.println("-------------------------------------------------------");
		while(matchingConfigIterator.hasNext()) {
			numberOfConfigBlocks++;
			IBlock iBlock = (IBlock) matchingConfigIterator.next();
			System.out.println("IBlock is "+ iBlock);
			System.out.println("-------------------------------------------------------");
			System.out.println("iBlock.getBlockNameValue() = "+ iBlock.getBlockNameValue() );
			System.out.println("iBlock.getConfigType() = " + iBlock.getConfigType());
			System.out.println("iBlock.getBusinessKeySet().toString() = " + iBlock.getBusinessKeySet().toString());
			System.out.println("iBlock.getConfigProperty().toString() = "+iBlock.getConfigProperty().toString());
			System.out.println("iBlock.getTypePropKeySet().toString() = "+ iBlock.getTypePropKeySet().toString());
			System.out.println("-------------------------------------------------------");
		}
		System.out.println("Number of matchingConfigurators is "+ numberOfConfigBlocks);*/
		
		//System.out.println("Matching Configuration " + mathingConfig);

		// Check what is loaded
		////BlockManager.printConfigMap();
		////BlockManager.printBKeytoConfigMap();
		// ConfigLoader.loadConfig();
		// ConfigLoader.printConfigType();
		// ConfigLoader.printConfigMapByType();

		// sendMyFiles.startFTP(fileToSend);

	}
/*
	public void startFTP(String fileToFTP) {
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			// jsch.addIdentity(prvkey);
			session = jsch.getSession(userId, serverAddress, port);
			session.setPassword(password);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;

			channelSftp.lcd(localDirectory);

			File folder = new File(localDirectory);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile()) {
					FileInputStream fs = new FileInputStream(file);
					System.out.println("Channel is connected "
							+ channelSftp.isConnected());
					SftpProgressMonitor monitor = new SftpProgressMonitor() {
						long bytesToTransfer = 0;

						@Override
						public void init(int arg0, String arg1, String arg2,
								long data) {
							bytesToTransfer = data;
							// System.out.println("file Transfer has started");

						}

						@Override
						public void end() {
							// TODO Auto-generated method stub

						}

						@Override
						public boolean count(long arg0) {
							// System.out.println("Remaining : " +
							// (bytesToTransfer-arg0));
							return true;
						}
					};
					channelSftp.put(fs, file.getName(), monitor);
					System.out.println(file.getName());
					fs.close();
				}
			}

			java.util.Vector vv = channelSftp.ls(remoteDirectory);
			for (Object obj : vv) {
				if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
					System.out
							.println(((com.jcraft.jsch.ChannelSftp.LsEntry) obj)
									.getLongname());
				}
			}
			// File f = new File(FILETOTRANSFER);
			// channelSftp.put(new FileInputStream(f), f.getName());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}*/
}
