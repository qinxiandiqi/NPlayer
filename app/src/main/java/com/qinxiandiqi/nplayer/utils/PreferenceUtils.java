package com.qinxiandiqi.nplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {

	private static SharedPreferences preferences;
	
	public static final void writePreferences(Context context){
		preferences = context.getSharedPreferences(CodeUtils.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(CodeUtils.LASTIDOFMUSIC, CodeUtils.playingMusic);
		editor.putInt(CodeUtils.LASTIDOFVIDEO, CodeUtils.playingVideo);
		editor.putInt(CodeUtils.LASTMUSICMODEL, CodeUtils.playingMusicModel);
		editor.putInt(CodeUtils.LASTVIDEOMODEL, CodeUtils.playingVideoModel);
		editor.commit();
	}
	
	public static final void readPreferences(Context context){
		preferences = context.getSharedPreferences(CodeUtils.PREFERENCES, Context.MODE_PRIVATE);
		CodeUtils.playingMusic = preferences.getInt(CodeUtils.LASTIDOFMUSIC, CodeUtils.NOTNEXTONE);
		CodeUtils.playingVideo = preferences.getInt(CodeUtils.LASTIDOFVIDEO, CodeUtils.NOTNEXTONE);
		CodeUtils.playingMusicModel = preferences.getInt(CodeUtils.LASTMUSICMODEL, CodeUtils.LISTLOOP);
		CodeUtils.playingVideoModel	= preferences.getInt(CodeUtils.LASTVIDEOMODEL, CodeUtils.LISTLOOP);
		CodeUtils.forwardMusic = CodeUtils.playingMusic;
	}
}
