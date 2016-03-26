/**
 * 
 */
package com.db.tw.distribution.bolts;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.db.tw.distribution.topology.MyTopologyMain;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

/**
 * @author my pc
 *
 */
public class FileDispatcherBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 5269368704360889421L;
	final static Logger logger = Logger.getLogger(FileDispatcherBolt.class);
	private int id;
	private String name;

	/*
	 * Process the input tuple and optionally emit new tuples based on the input
	 * tuple. All acking is managed for you. Throw a FailedException if you want
	 * to fail the tuple.
	 * 
	 * @see
	 * backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple,
	 * backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		logger.info("FileDispatcherBolt "+ this.name + " with id "+ this.id +" execute method is invoked %n");
		Fields fieldsRecieved = input.getFields();
		Iterator<String> fieldsRecievedIterator = fieldsRecieved.iterator();
		while(fieldsRecievedIterator.hasNext()) {
			String fieldString = (String) fieldsRecievedIterator.next();
			logger.info("FieldString is "+fieldString);
		}
		File file = new File("Hello");
		List<Object> payLoad = new ArrayList<Object>();
		payLoad.add(file);
		collector.emit(payLoad);
	}

	/*
	 * Declare the output schema for all the streams of this topology.
	 * 
	 * @see
	 * backtype.storm.topology.IComponent#declareOutputFields(backtype.storm
	 * .topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("FileDispatcherField"));
	}

	/**
	 * IComponent - Declare configuration specific to this component. Only a
	 * subset of the "topology.*" configs can be overridden. The component
	 * configuration can be further overridden when constructing the topology
	 * using TopologyBuilder
	 */

//	@Override
//	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * IBasicBolt - 
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
		logger.info("FileDispathcerBolt prepare is invoked. The name is "+ this.name + " and the id is "+ this.id);
	}

	/**
	 * IBasicBolt
	 */
	@Override
	public void cleanup() {
		logger.info("FileDispathcerBolt cleanup is invoked");
	}

}
