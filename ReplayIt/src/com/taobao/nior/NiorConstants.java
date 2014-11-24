package com.taobao.nior;

public class NiorConstants {
	public static final String REMOTE_BASE_DIR="/data/local/nior/";
	public static final String LOCAL_BASE_DIR="/data/data/com.taobao.athrun.ri/files/";
	
	public static final String RECORDER_PATH = REMOTE_BASE_DIR+"rr/recorder";
	public static final String REPLAYER_PATH = REMOTE_BASE_DIR+"rr/replayer";
	
	public static final String LOCAL_RECORDER_PATH = LOCAL_BASE_DIR+"recorder";
	public static final String LOCAL_REPLAYER_PATH = LOCAL_BASE_DIR+"replayer";
	
	
	public static final String NATIVE_EVENT_RECORD = "/native/libs/armeabi/event_record";
	public static final String NATIVE_EVENT_REPLAY = "/native/libs/armeabi/event_replay";
	public static String REMOTE_VIDEO_DIR = "/sdcard/screencast/";
	public static String REMOTE_TCPDUMP_DIR = "/sdcard/AndroidNoir/tcpdump/";
	public static String DIR_VIDEO = "Videos";
	public static String DIR_IMAGE = "Result";
	public static String HELP_LINK="http://code.google.com/p/nior/";
}