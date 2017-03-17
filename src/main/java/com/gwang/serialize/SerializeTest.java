package com.gwang.serialize;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class SerializeTest {
	private static final Logger log = LoggerFactory.getLogger(SerializeTest.class);
	
	public static void json() {
		try {
			SerializeDemo demo = new SerializeDemo(1, "haha");
			String jsonString = JSON.toJSONString(demo);
			log.info(jsonString);
			FileWriter writer = new FileWriter("1.txt", false);  
			writer.write(jsonString);  
			writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static void serialize() {
		try {
			SerializeDemo demo = new SerializeDemo(1, "haha");
			FileOutputStream fos = new FileOutputStream("1.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(demo);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}

	public static void main(String[] args) {
//		SerializeTest.json();
		SerializeTest.serialize();
	}
}
