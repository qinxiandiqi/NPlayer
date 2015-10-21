package com.qinxiandiqi.nplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qinxiandiqi.nplayer.utils.CodeUtils;

public class MusicActivity extends Activity {

	private ImageButton startButton;
	private TextView musicTitle;
	private MusicActivityReceiver musicActivityReceiver;
	private Intent intentToMusicService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		startButton = (ImageButton) findViewById(R.id.start);
		musicTitle = (TextView) findViewById(R.id.musictitle);
		musicActivityReceiver = new MusicActivityReceiver();
		IntentFilter filter = new IntentFilter(CodeUtils.MUSICACTION);
		registerReceiver(musicActivityReceiver, filter);
		intentToMusicService = new Intent();
		intentToMusicService.setAction(CodeUtils.MUSICSERVICEACTION);
		
		if(CodeUtils.playingMusic != CodeUtils.NOTNEXTONE){
			musicTitle.setText(CodeUtils.musicFileList.get(CodeUtils.playingMusic).getMediaName());
		}
		
		if(CodeUtils.playingMusicState == CodeUtils.PLAYING){
			startButton.setImageResource(R.drawable.playing);
		}
	}

	@Override
	protected void onPause() {
		CodeUtils.musicAdapter.notifyDataSetChanged();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(musicActivityReceiver);
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

//	上一首按钮监听器
	public void forwardoneOnClick(View v){
		intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.FORWARDONE);
		sendBroadcast(intentToMusicService);
	}
	
//	快退按钮监听器
	public void rewindOnClick(View v){
		intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.REWIND);
		sendBroadcast(intentToMusicService);
	}
	
//	开始暂停按钮监听器
	public void startOnClick(View v){
		if(CodeUtils.playingMusicState == CodeUtils.PLAYING){
			intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.PAUSE);
		}else{
			intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.START);
		}
		sendBroadcast(intentToMusicService);
	}
	
//	停止按钮监听器
	public void stopOnClick(View v){
		intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.STOP);
		sendBroadcast(intentToMusicService);
	}
	
//	快进按钮监听器
	public void fastforwardOnClick(View v){
		intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.FASTFORWARD);
		sendBroadcast(intentToMusicService);
	}
	
//	下一首按钮监听器
	public void nextoneOnClick(View v){
		intentToMusicService.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.NEXTONE);
		sendBroadcast(intentToMusicService);
	}
	
	public class MusicActivityReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getExtras().getInt(CodeUtils.MEDIAOPCODE)) {
			case CodeUtils.PLAYING:
				startButton.setImageResource(R.drawable.playing);
				musicTitle.setText(CodeUtils.musicFileList.get(CodeUtils.playingMusic).getMediaName());
				break;
			case CodeUtils.STOP:
				startButton.setImageResource(R.drawable.start);
				break;
			case CodeUtils.PAUSE:
				startButton.setImageResource(R.drawable.start);
				break;
			default:
				break;
			}
			
		}
		
	}
}
