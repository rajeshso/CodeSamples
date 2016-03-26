package com.db.tw.distribution.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;

import com.db.tw.distribution.config.BlockManager;
import com.db.tw.distribution.config.IBlock;

public class BlockManagerTest {

	 //@BeforeClass
	public static void setUp() throws Exception {
		BlockManager.init();
		Set<IBlock> iBlocks = BlockManager.getConfigList();
		System.out.println("iBlocks = " + iBlocks);
		Set<String> configTypes = BlockManager.getConfigTypes();
		System.out.println("iBlock Types = " + configTypes);
		Set<IBlock> iBlocksForJMS = BlockManager.getIBlocks("JMS");
		System.out.println("IBlockForJMS = " + iBlocksForJMS);
	}

	@Ignore
	public final void testInit() {
		//System.out.println("---------------------------------------");
		assertNotNull(BlockManager.getConfigList());
	}

	@Ignore
	public final void testGetMatchingConfigsForBKeys() {
		//System.out.println("---------------------------------------");
		Map<String, List<String>> matchingCriteria = getMatchingCriteria();
		Set<IBlock> mathingBlocks = BlockManager
				.getMatchingConfigsForBKeys(matchingCriteria);
		//System.out.println("mathingBlocks = "+ mathingBlocks);
		Set<String> matchingBlockIDs = getIBlockIDs(mathingBlocks);
		assertTrue(matchingBlockIDs.contains("MYACTIVEMQ"));
		assertTrue(matchingBlockIDs.contains("MYJMS1"));
		assertTrue(matchingBlockIDs.contains("MYJMS2"));
	}
	
	@Ignore
	public final void testGetJMSServerIDs() {
		//System.out.println("---------------------------------------");
		Map<String, List<String>> matchingCriteria = getMatchingCriteria();
		Set<IBlock> mathingBlocks = BlockManager
				.getMatchingConfigsForBKeys(matchingCriteria);
		//System.out.println("mathingBlocks = "+ mathingBlocks);
		for (Iterator<IBlock> i = mathingBlocks.iterator();i.hasNext(); ) {
			IBlock iBlock = i.next();
			String serverIDValue = getJMSBlockServerID(iBlock);
			assertTrue(serverIDValue.equals("A") || serverIDValue.equals("B"));
		}
	}	

	/**
	 * A JMS Block contains a value called SERVERID. The server ID is useful to provide the ConnectionFactory
	 *  
	 * @param iBlock
	 * @return SERVERID
	 */
	private String getJMSBlockServerID(IBlock iBlock) {
		String iBlockName = iBlock.getBlockNameValue();
		String serverIDString = "SERVERID";
		String serverIDKey = iBlockName + "." + serverIDString;
		String serverIDValue = iBlock.getConfigProperty().getProperty(serverIDKey);
/*		System.out.println("serverIDKey  = " +serverIDKey);
		System.out.println("serverIDValue = " + serverIDValue );
		System.out.println("iBlock Config Property = "  + iBlock.getConfigProperty());*/
		return serverIDValue;
	}

	/**
	 * Given a set of matchingBlocks, this method provides a list of BlockIDs
	 * @param mathingBlocks
	 * @return
	 */
	private Set<String> getIBlockIDs(Set<IBlock> mathingBlocks) {
		Set<String> matchingBlockIDs = new HashSet<String>();
		for (Iterator<IBlock> i = mathingBlocks.iterator(); i.hasNext();) {
			IBlock iBlock = (IBlock) i.next();
			matchingBlockIDs.add(iBlock.getBlockNameValue());
		}
		return matchingBlockIDs;
	}
	
	private Map<String, List<String>> getMatchingCriteria() {
		Map<String, List<String>> matchingCriteria = new HashMap<String, List<String>>();
		String businessKey1 = "ASSET";
		String businessKey2 = "JUR";
		List<String> businessValues1 = new ArrayList<String>();
		List<String> businessValues2 = new ArrayList<String>();
		businessValues1.add("FX");
		matchingCriteria.put(businessKey1, businessValues1);
		System.out.printf("Business Key =  %s | Business Values = %s  %n", businessKey1,  businessValues1);
		businessValues2.add("EMIR");
		businessValues2.add("ASIC");
		matchingCriteria.put(businessKey2, businessValues2);
		System.out.printf("Business Key =  %s | Business Values = %s  %n", businessKey2,  businessValues2);
		return matchingCriteria;
	}
}
