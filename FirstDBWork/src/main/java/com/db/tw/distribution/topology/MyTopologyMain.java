package com.db.tw.distribution.topology;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.db.tw.distribution.bolts.FileDispatcherBolt;
import com.db.tw.distribution.spouts.FileSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;


public class MyTopologyMain {
	final static Logger logger = Logger.getLogger(MyTopologyMain.class);
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
		PropertyConfigurator.configure("log4j.properties");
		logger.debug("Hello");
		
		//Topology definition
		TopologyBuilder topologyBuilder = new TopologyBuilder(); 
		topologyBuilder.setSpout("spoutID1", new FileSpout());
		topologyBuilder.setBolt("boltID1", new FileDispatcherBolt()).shuffleGrouping("spoutID1");
		StormTopology topology = topologyBuilder.createTopology();

		//Configuration object to pass to spout and bolt
		Config config = new Config();
		//config.setDebug(false);
		config.setMaxSpoutPending(1);
		
		//Initiate and Submit the Topology
		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("RajTopology", config, topology);
		Thread.sleep(5000);
		//Shutdown the Topology
		localCluster.shutdown();
	}

}
