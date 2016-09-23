package com.gwang.reflex;

public interface Voice {
	
	public static int voiceType = 1; //发音类型（1：说话 2：叫唤）

	public void getVoiceType(int voiceType);
	
	public void sound();
}
