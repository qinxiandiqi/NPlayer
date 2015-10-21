package com.qinxiandiqi.nplayer.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.qinxiandiqi.nplayer.bean.MusicFile;
import com.qinxiandiqi.nplayer.bean.VideoFile;


public class XMLUtils {

	public static List<MusicFile> readMusicXML(InputStream xml) throws Exception {
		MusicFile musicFile = null;
		XmlPullParser PullParser = Xml.newPullParser();
		PullParser.setInput(xml, "utf-8");
		int event = PullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if("music".equals(PullParser.getName())){
					musicFile = new MusicFile();
				}
				if("mediaName".equals(PullParser.getName())){
					String mediaName = PullParser.nextText();
					musicFile.setMediaName(mediaName);
				}
				if("mediaPath".equals(PullParser.getName())){
					String mediaPath = PullParser.nextText();
					musicFile.setMediaPath(mediaPath);
				}
				if("mediaLength".equals(PullParser.getName())){
					int mediaLength = Integer.parseInt(PullParser.nextText());
					musicFile.setMediaLength(mediaLength);
				}
				if("lastTime".equals(PullParser.getName())){
					int lastTime = Integer.parseInt(PullParser.nextText());
					musicFile.setLastTime(lastTime);
				}
				if("musicSinger".equals(PullParser.getName())){
					String musicSinger = PullParser.nextText();
					musicFile.setMusicSinger(musicSinger);
				}
				break;
			case XmlPullParser.END_TAG:
				if("music".equals(PullParser.getName())){
					CodeUtils.musicFileList.add(musicFile);
				}
				break;
			}
			event = PullParser.next();
		}
		return CodeUtils.musicFileList;
	}
	
	public static void writeMusicXML(OutputStream out)throws Exception{
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(out, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "musics");
		for(MusicFile musicItem:CodeUtils.musicFileList){
			serializer.startTag(null, "music");
			
			serializer.startTag(null, "mediaName");
			serializer.text(musicItem.getMediaName());
			serializer.endTag(null, "mediaName");
			
			serializer.startTag(null, "mediaPath");
			serializer.text(musicItem.getMediaPath());
			serializer.endTag(null, "mediaPath");
			
			serializer.startTag(null, "mediaLength");
			serializer.text(String.valueOf(musicItem.getMediaLength()));
			serializer.endTag(null, "mediaLength");
			
			serializer.startTag(null, "lastTime");
			serializer.text(String.valueOf(musicItem.getLastTime()));
			serializer.endTag(null, "lastTime");
			
			serializer.startTag(null, "musicSinger");
			serializer.text(musicItem.getMusicSinger());
			serializer.endTag(null, "musicSinger");
			
			serializer.endTag(null, "music");
		}
		serializer.endTag(null, "musics");
		serializer.endDocument();
		out.flush();
		out.close();
	}
	
	
	public static List<VideoFile> readVideoXML(InputStream xml) throws Exception {
		VideoFile videoFile = null;
		XmlPullParser PullParser = Xml.newPullParser();
		PullParser.setInput(xml, "utf-8");
		int event = PullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if("video".equals(PullParser.getName())){
					videoFile = new VideoFile();
				}
				if("mediaName".equals(PullParser.getName())){
					String mediaName = PullParser.nextText();
					videoFile.setMediaName(mediaName);
				}
				if("mediaPath".equals(PullParser.getName())){
					String mediaPath = PullParser.nextText();
					videoFile.setMediaPath(mediaPath);
				}
				if("mediaLength".equals(PullParser.getName())){
					int mediaLength = Integer.parseInt(PullParser.nextText());
					videoFile.setMediaLength(mediaLength);
				}
				if("lastTime".equals(PullParser.getName())){
					int lastTime = Integer.parseInt(PullParser.nextText());
					videoFile.setLastTime(lastTime);
				}
				break;
			case XmlPullParser.END_TAG:
				if("video".equals(PullParser.getName())){
					CodeUtils.videoFileList.add(videoFile);
				}
				break;
			}
			event = PullParser.next();
		}
		return CodeUtils.videoFileList;
	}
	
	public static void writeVideoXML(OutputStream out)throws Exception{
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(out, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "videos");
		for(VideoFile videoItem:CodeUtils.videoFileList){
			serializer.startTag(null, "video");
			
			serializer.startTag(null, "mediaName");
			serializer.text(videoItem.getMediaName());
			serializer.endTag(null, "mediaName");
			
			serializer.startTag(null, "mediaPath");
			serializer.text(videoItem.getMediaPath());
			serializer.endTag(null, "mediaPath");
			
			serializer.startTag(null, "mediaLength");
			serializer.text(String.valueOf(videoItem.getMediaLength()));
			serializer.endTag(null, "mediaLength");
			
			serializer.startTag(null, "lastTime");
			serializer.text(String.valueOf(videoItem.getLastTime()));
			serializer.endTag(null, "lastTime");
			
			serializer.endTag(null, "video");
		}
		serializer.endTag(null, "videos");
		serializer.endDocument();
		out.flush();
		out.close();
	}
}
