package com.qinxiandiqi.bean;

public class MediaFile {
	
	private String mediaName;
	private String mediaPath;
	private int mediaLength;
	private int lastTime;
	
	
	public MediaFile() {
		super();
		this.mediaLength = 0;
		this.lastTime = 0;
		this.mediaName = "";
		this.mediaPath = "";
	}

	public MediaFile(String mediaName, String mediaPath) {
		super();
		this.mediaName = mediaName;
		this.mediaPath = mediaPath;
		this.mediaLength = 0;
		this.lastTime = 0;
	}

	public MediaFile(String mediaName, String mediaPath, int mediaLength) {
		super();
		this.mediaName = mediaName;
		this.mediaPath = mediaPath;
		this.mediaLength = mediaLength;
		this.lastTime = 0;
	}
	
	public MediaFile(String mediaName, String mediaPath, int mediaLength,
			int lastTime) {
		super();
		this.mediaName = mediaName;
		this.mediaPath = mediaPath;
		this.mediaLength = mediaLength;
		this.lastTime = lastTime;
	}

	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaPath() {
		return mediaPath;
	}
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	public int getMediaLength() {
		return mediaLength;
	}
	public void setMediaLength(int mediaLength) {
		this.mediaLength = mediaLength;
	}

	public int getLastTime() {
		return lastTime;
	}
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	
}
