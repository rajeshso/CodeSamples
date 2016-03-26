package com.raj.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.RandomAccess;

public class FileCreator {

	public FileCreator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		StringBuilder fileNameBuilder = new StringBuilder();
		fileNameBuilder.append(System.currentTimeMillis());
		fileNameBuilder.append("-");
		fileNameBuilder.append(new Random().nextInt());
		fileNameBuilder.append(".xml");
		String fileName = fileNameBuilder.toString();
		System.out.printf("FileName is %s %n", fileName);
		String fileLocation = "C:\\temp\\outbound\\";
		Path newFilePath = Paths.get(fileLocation, fileName);
		if (!Files.exists(newFilePath)) {
		    try {
		        Files.createFile(newFilePath);
		        RandomAccessFile inFile = new RandomAccessFile(fileLocation+fileName, "rw");
		        FileChannel inChannel = inFile.getChannel();
		        String newData = "New String to write to file1233..." + System.currentTimeMillis();

		        ByteBuffer buf = ByteBuffer.allocate(8192);
		        buf.clear();
		        buf.put(newData.getBytes());
		        buf.flip();
		        while(buf.hasRemaining()) {
		        	inChannel.write(buf);
		        }
		        inChannel.force(true);
		    	inChannel.close();
		    	inFile.close();
		    } catch (IOException e) {
		        System.err.println(e);
		    }finally {

		    }
		}
	}

}
