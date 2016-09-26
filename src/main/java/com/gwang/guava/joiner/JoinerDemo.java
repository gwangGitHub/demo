package com.gwang.guava.joiner;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

public class JoinerDemo {

	public static void main(String[] args) {
		
		String [] ids = new String [] {"1001", "1002", null, "1003"};
		
		//ugly case
		StringBuilder builder = new StringBuilder();
		for (String id : ids) {
			builder.append(id).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		System.out.println(builder.toString());
		
		//bad case
		System.out.println(StringUtils.join(ids, ","));
		
		//perfect
		System.out.println(Joiner.on(",").skipNulls().join(ids));
		
		//map map joiner
		System.out.println(Joiner.on("&").skipNulls().withKeyValueSeparator("=")
				.join(ImmutableMap.of("name", "zhangsan", "sex", "man")));
	}
}
