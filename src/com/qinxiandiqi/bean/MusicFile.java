package com.qinxiandiqi.bean;

public class MusicFile extends MediaFile{
	
	private String musicSinger;
	
	public MusicFile(){
		super();
		this.musicSinger = "";
	}
	
	public MusicFile(String musicName, String musicPath, String musicSinger) {
		super(musicName,musicPath);
		this.musicSinger = musicSinger;
	}
	
	public String getMusicSinger() {
		return musicSinger;
	}
	
	public void setMusicSinger(String musicSinger) {
		this.musicSinger = musicSinger;
	}
	
	
}
