package com.gwang.guava.splitter;

import com.google.common.base.Splitter;

public class SplitterDemo {
	public static void main(String[] args) {
		String idStr = "1001 ,,101,11,1,,,10";
		String [] ids = idStr.split(",");
		for(String id : ids) {
			System.out.println(id);
		}
		Iterable<String> iterator = Splitter.on(",").trimResults().omitEmptyStrings().split(idStr);
		for (String id : iterator) {
			System.out.println(id);
		}
	}
}
