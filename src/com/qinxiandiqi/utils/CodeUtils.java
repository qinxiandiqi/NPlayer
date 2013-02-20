package com.qinxiandiqi.utils;

import java.util.List;

import com.qinxiandiqi.adapter.MusicAdapter;
import com.qinxiandiqi.adapter.VideoAdapter;
import com.qinxiandiqi.bean.MusicFile;
import com.qinxiandiqi.bean.VideoFile;

public class CodeUtils {
	
	public static final String SEARCHTAG = "SearchTag";
	public static final String MAINTAG = "MainTag";
	
//	首选项参数
	public static final String PREFERENCES = "qinxiandiqi";
	public static final String LASTIDOFMUSIC = "lastidofmusic";
	public static final String LASTIDOFVIDEO = "lastidofvideo";
	public static final String LASTMUSICMODEL = "lastmodelofmusic";
	public static final String LASTVIDEOMODEL = "lastmodelofvideo";
	
//	标签页id
	public static final String MUSICTAB = "musicTab";
	public static final String VIDEOTAB = "videoTab";
	
//	搜索功能使用常量
	public static final int SEARCHREQUESTCODE = 10;
	public static final int SEARCHRESULTCODE = 10;
	public static final String SEARCHPATHFOREXTRA = "searchpath";
	public static final String SEARCHPATHLIST = "searchpathlist";

//	播放状态常量
	public static final int START = 0;            //开始
	public static final int STOP = 1;			  //停止
	public static final int PAUSE = 2;			  //暂停
	public static final int PLAYING = 3;		  //播放中
	public static final int FASTFORWARD = 4;	  //快进
	public static final int REWIND = 5;			  //快退
	public static final int NEXTONE = 6;		  //下一曲
	public static final int FORWARDONE = 7;		  //上一曲
	public static final int NOTFOUNDFILE = 8;	  //未找到播放文件
	public static final int CUTETONEXT = 9;	   	  //直接切入下一首
	public static final int NOTNEXTONE = -1;	  //没有下一首了
	
//	播放模式
	public static final int LISTLOOP = 20;		   //列表循环
	public static final int SINGLECYCLE = 21;	   //单曲循环
	public static final int RANDOM = 22;		   //随机播放
	public static final int ORDERPLAY = 23;		   //顺序播放
	public static final int SINGLEPLAY = 24;       //单曲播放
	
//	广播响应action
	public static final String MUSICACTION = "com.qinxiandiqi.nplay.MusicActivity";
	public static final String COREACTION = "com.qinxiandiqi.nplay.MainActivity";
	public static final String MUSICSERVICEACTION = "com.qinxiandiqi.service.MusicService";
	
//	intent标识
	public static final String MEDIAOPCODE = "MediaOpcode";
	public static final String NEXTID = "nextID";
	
//	快进快退步伐
	public static final int STEPSIZE = 5000;
	
	public static String mainTab = MUSICTAB;
	
//	判断mediaPlayer是否已经加载音乐文件标识
	public static boolean hadLoadMusic = false;
	
//	播放列表信息
	public static List<MusicFile> musicFileList;
	public static List<VideoFile> videoFileList;
	public static MusicAdapter musicAdapter;
	public static VideoAdapter videoAdapter;
	
//	正在播放音频视频信息
	public static int playingMusic = NOTNEXTONE;
	public static int playingVideo = NOTNEXTONE;
	public static int playingMusicState = PAUSE;
	public static int playingVideoState = PAUSE;
	public static int playingMusicModel = LISTLOOP;
	public static int playingVideoModel = LISTLOOP;
	
//	上一首音乐
	public static int forwardMusic = NOTNEXTONE;
	
//	当前音频和视频列表长度
	public static int musicListLength;
	public static int videoListLength;
}
