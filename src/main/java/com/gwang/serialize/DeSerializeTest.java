package com.gwang.serialize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class DeSerializeTest {
	private static final Logger log = LoggerFactory.getLogger(SerializeTest.class);
	
	public static void parsJson() {
		try {
			File file = new File("1.txt");  
			BufferedReader reader = new BufferedReader(new FileReader(file)); 
			String jsonString = reader.readLine();
			log.info(jsonString);
			reader.close();
			SerializeDemo demo = JSON.parseObject(jsonString, SerializeDemo.class);
			log.info("demo:{}", demo.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static void deSerialize() {
		try {
			FileInputStream fis = new FileInputStream("1.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			SerializeDemo demo = (SerializeDemo) ois.readObject();
			ois.close();
			log.info("demo:{}", demo.toString());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}

	public static void main(String[] args) {
//		DeSerializeTest.parsJson();
		DeSerializeTest.deSerialize();
	}
}
