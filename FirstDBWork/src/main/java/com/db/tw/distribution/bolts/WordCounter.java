package com.db.tw.distribution.bolts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCounter extends BaseBasicBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2795932943749903797L;
	Integer id;
	String name;
	Map<String, Integer> counters;

	/**
	 * At the end of the spout (when the cluster is shutdown
	 * We will show the word counters
	 */
	@Override
	public void cleanup() {
		System.out.println("-- Word Counter ["+name+"-"+id+"] --");
		for(Map.Entry<String, Integer> entry : counters.entrySet()){
			System.out.println(entry.getKey()+": "+entry.getValue());
		}
	}

	/**
	 * On create 
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		this.counters = new HashMap<String, Integer>();
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	/**
	 * The tuple is the main data structure in Storm. A tuple is a named list of
	 * values, where each value can be any type. Tuples are dynamically typed --
	 * the types of the fields do not need to be declared. Tuples have helper
	 * methods like getInteger and getString to get field values without having
	 * to cast the result. Storm needs to know how to serialise all the values
	 * in a tuple. By default, Storm knows how to serialise the primitive types,
	 * strings, and byte arrays.
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String str = input.getString(0);
		/**
		 * If the word dosn't exist in the map we will create this, if not We
		 * will add 1
		 */
		if (!counters.containsKey(str)) {
			counters.put(str, 1);
		} else {
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
		collector.emit(new Values(str));
		System.out.println("Emitted "+ str);
	}
}
