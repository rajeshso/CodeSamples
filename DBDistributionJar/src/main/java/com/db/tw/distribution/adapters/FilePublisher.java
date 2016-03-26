package com.db.tw.distribution.adapters;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.tw.distribution.Router;
import com.db.tw.distribution.publisher.Publisher;

public class FilePublisher implements Publisher {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilePublisher.class);
	private String fileLocation;
	private String payLoad;

	public FilePublisher() {
	}

	/**
	 * @param fileLocation
	 */
	public FilePublisher(String fileLocation) {
		super();
		this.fileLocation = fileLocation;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public void prepare(Map<String, Object> context) {
		// TODO Auto-generated method stub
	}

	public void execute() throws Exception {
		LOGGER.debug("FilePublisher execute called");
		if (payLoad == null) {
			LOGGER.error("FilePublisher payLoad is not set");
			throw new Exception("payLoad for the publisher is not set");
		}
		String fileName = getFileName();
		LOGGER.info("FileName is {}", fileName);
		Path newFilePath = Paths.get(fileLocation, fileName);
		RandomAccessFile inFile = null;
		FileChannel inChannel = null;
		if (!Files.exists(newFilePath)) {
			try {
				Files.createFile(newFilePath);
				inFile = new RandomAccessFile(fileLocation + fileName, "rw");
				inChannel = inFile.getChannel();
				// TODO: The following logic written for String.
				// Find a better logic to handle fileSizes that are larger than
				// 8192 bytes
				ByteBuffer buf = ByteBuffer.allocate(8192);
				buf.clear();
				buf.put(payLoad.getBytes(StandardCharsets.UTF_8));
				buf.flip();
				while (buf.hasRemaining()) {
					inChannel.write(buf);
				}
				inChannel.force(true);
			} catch (IOException e) {
				System.err.println(e);
				throw e;
			} finally {
				if (inChannel != null)
					inChannel.close();
				if (inFile != null)
					inFile.close();
			}
		}
	}

	private String getFileName() {
		StringBuilder fileNameBuilder = new StringBuilder();
		fileNameBuilder.append(System.currentTimeMillis());
		fileNameBuilder.append("-");
		fileNameBuilder.append(Math.random() * 100);
		fileNameBuilder.append(".xml");
		String fileName = fileNameBuilder.toString();
		return fileName;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (Exception e) {
			// Do not retry. Write to the audit error log.
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	@Override
	public boolean testConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}
}
