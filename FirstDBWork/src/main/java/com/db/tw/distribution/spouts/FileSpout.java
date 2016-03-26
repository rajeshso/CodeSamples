package com.db.tw.distribution.spouts;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.db.tw.distribution.topology.MyTopologyMain;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class FileSpout extends BaseRichSpout {

	private static final long serialVersionUID = -6099374832821772653L;
	final static Logger logger = Logger.getLogger(FileSpout.class);
	private SpoutOutputCollector collector;
	boolean completed = false;

	/**
	 * Called when a task for this component is initialized within a worker on
	 * the cluster. It provides the spout with the environment in which the
	 * spout executes. This includes the: Parameters:conf - The Storm
	 * configuration for this spout. This is the configuration provided to the
	 * topology merged in with cluster configuration on this machine.context -
	 * This object can be used to get information about this task's place within
	 * the topology, including the task id and component id of this task, input
	 * and output information, etc.collector - The collector is used to emit
	 * tuples from this spout. Tuples can be emitted at any time, including the
	 * open and close methods. The collector is thread-safe and should be saved
	 * as an instance variable of this spout object.
	 */
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		logger.info("FileSpout open is invoked");
		this.collector = collector;
	}

	/**
	 * Called when an ISpout is going to be shutdown. There is no guarantee that
	 * close will be called, because the supervisor kill -9's worker processes
	 * on the cluster. The one context where close is guaranteed to be called is
	 * a topology is killed when running Storm in local mode.
	 */
	@Override
	public void close() {
		logger.info("FileSpout close is invoked");
	}

	/**
	 * Called when a spout has been activated out of a deactivated mode.
	 * nextTuple will be called on this spout soon. A spout can become activated
	 * after having been deactivated when the topology is manipulated using the
	 * `storm` client.
	 */
	@Override
	public void activate() {
		logger.info("FileSpout activate is invoked");
	}

	/**
	 * Called when a spout has been deactivated. nextTuple will not be called
	 * while a spout is deactivated. The spout may or may not be reactivated in
	 * the future.
	 */
	@Override
	public void deactivate() {
		logger.info("FileSpout decativate is invoked");
	}

	/**
	 * When this method is called, Storm is requesting that the Spout emit
	 * tuples to the output collector. This method should be non-blocking, so if
	 * the Spout has no tuples to emit, this method should return. nextTuple,
	 * ack, and fail are all called in a tight loop in a single thread in the
	 * spout task. When there are no tuples to emit, it is courteous to have
	 * nextTuple sleep for a short amount of time (like a single millisecond) so
	 * as not to waste too much CPU.
	 */
	@Override
	public void nextTuple() {
		/**
		 * The nextuple it is called forever, so if we have read the file
		 * we will wait and then return
		 */
		if(completed){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Do nothing
			}
			return;
		}
		Values tuplePayload = new Values("payLoadField");
		this.collector.emit(tuplePayload);
		this.completed = true;
	}

	/**
	 * Storm has determined that the tuple emitted by this spout with the msgId
	 * identifier has been fully processed. Typically, an implementation of this
	 * method will take that message off the queue and prevent it from being
	 * replayed.
	 */
	@Override
	public void ack(Object msgId) {
		logger.info("Thank you acknowledgement from FileSpout for msgID "+msgId);
	}

	/**
	 * The tuple emitted by this spout with the msgId identifier has failed to
	 * be fully processed. Typically, an implementation of this method will put
	 * that message back on the queue to be replayed at a later time.
	 */
	@Override
	public void fail(Object msgId) {
		logger.info("FileSpout fail method is called");
	}

	/**
	 * IComponent - Declare the output schema for all the streams of this
	 * topology.
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		logger.info("FileSpout declareOutputFields method is called");
		declarer.declare(new Fields("TheFileSpoutField"));
	}

	/**
	 * IComponent - Declare configuration specific to this component. Only a
	 * subset of the "topology.*" configs can be overridden. The component
	 * configuration can be further overridden when constructing the topology
	 * using TopologyBuilder
	 */
//	@Override
//	public Map<String, Object> getComponentConfiguration() {
//		return null;
//	}

}
