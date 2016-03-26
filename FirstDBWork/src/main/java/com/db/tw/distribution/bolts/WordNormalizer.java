package com.db.tw.distribution.bolts;

import java.util.Iterator;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
/**
 * It will split the line into words, convert all words to lowercase, and trim them.
 * @author Rajesh
 *
 */
public class WordNormalizer extends BaseBasicBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 751105854423231360L;


	@Override
	public void cleanup() {}

	/**
	 * The bolt will receive the line from the
	 * words file and process it to Normalise this line
	 * 
	 * The normalise will put the words in lower case
	 * and split the line to get all words in this tuple 
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for(String word : words){
            word = word.trim();
            if(!word.isEmpty()){
                word = word.toLowerCase();
                collector.emit(new Values(word));
            }
        }
	}
	

	/**
	 * The bolt will only emit the field "word". The values emission will be from the execute method.
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}
