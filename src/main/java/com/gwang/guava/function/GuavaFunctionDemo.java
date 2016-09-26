package com.gwang.guava.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class GuavaFunctionDemo {

	public static void main(String[] args) {
		Function<Date, String> function = new Function<Date, String>() {

			public String apply(Date input) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		        return dateFormat.format(input);
			}
		};
		System.out.println(function.apply(new Date()));
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("aaa", "aaa");
		hashMap.put("bbb", "bbb");
		hashMap.put("ccc", "ccc");
		Function<String, String> lookup = Functions.forMap(hashMap);
		System.out.println(lookup.apply("aaa"));
	}
}
