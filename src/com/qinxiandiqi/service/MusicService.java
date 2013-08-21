package com.qinxiandiqi.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

import com.qinxiandiqi.utils.CodeUtils;
import com.qinxiandiqi.utils.PlayingUtils;

public class MusicService extends Service {

	private MusicServiceReceiver serviceReceiver;
	private MediaPlayer mediaPlayer;
	private Intent intentToMusicActivity;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		serviceReceiver = new MusicServiceReceiver();
		IntentFilter filter = new IntentFilter(CodeUtils.MUSICSERVICEACTION);
		registerReceiver(serviceReceiver, filter);
		mediaPlayer = new MediaPlayer();
		intentToMusicActivity = new Intent();
		intentToMusicActivity.setAction(CodeUtils.MUSICACTION);

		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				PlayingUtils.startNextMusic(MusicService.this, mediaPlayer, intentToMusicActivity);
			}
		});

	}

	@Override
	public void onDestroy() {
		if(CodeUtils.hadLoadMusic){
			CodeUtils.musicFileList.get(CodeUtils.playingMusic).setLastTime(mediaPlayer.getCurrentPosition());
		}
		mediaPlayer.release();
		CodeUtils.hadLoadMusic = false;
		unregisterReceiver(serviceReceiver);
		super.onDestroy();
	}

	public class MusicServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getExtras().getInt(CodeUtils.MEDIAOPCODE)) {
			case CodeUtils.START:
				if (CodeUtils.hadLoadMusic) {
					mediaPlayer.start();
					CodeUtils.playingMusicState = CodeUtils.PLAYING;
					intentToMusicActivity.putExtra(CodeUtils.MEDIAOPCODE,
							CodeUtils.PLAYING);
					MusicService.this.sendBroadcast(intentToMusicActivity);
					CodeUtils.musicAdapter.notifyDataSetChanged();
				} else {
					PlayingUtils.startMusicPlaying(MusicService.this,
							mediaPlayer, intentToMusicActivity);
				}
				break;
			case CodeUtils.PAUSE:
				mediaPlayer.pause();
				CodeUtils.playingMusicState = CodeUtils.PAUSE;
				intentToMusicActivity.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.PAUSE);
				MusicService.this.sendBroadcast(intentToMusicActivity);
				CodeUtils.musicAdapter.notifyDataSetChanged();
				break;
			case CodeUtils.CUTETONEXT:
				if (CodeUtils.hadLoadMusic) {
					CodeUtils.musicFileList.get(CodeUtils.forwardMusic)
							.setLastTime(mediaPlayer.getCurrentPosition());
				}
				PlayingUtils.startMusicPlaying(MusicService.this, mediaPlayer, intentToMusicActivity);
				break;
			case CodeUtils.STOP:
				PlayingUtils.stopMusic(MusicService.this, mediaPlayer, intentToMusicActivity);
				break;
			case CodeUtils.FASTFORWARD:
				if(CodeUtils.hadLoadMusic){
					int time = mediaPlayer.getCurrentPosition() + CodeUtils.STEPSIZE;
					if(time > mediaPlayer.getDuration()){
						time = mediaPlayer.getDuration();
					}
					mediaPlayer.seekTo(time);
				}
				break;
			case CodeUtils.REWIND:
				if(CodeUtils.hadLoadMusic){
					int time = mediaPlayer.getCurrentPosition() - CodeUtils.STEPSIZE;
					if(time<0){
						time = 0;
					}
					mediaPlayer.seekTo(time);
				}
				break;
			case CodeUtils.NEXTONE:
				PlayingUtils.startNextMusic(MusicService.this, mediaPlayer, intentToMusicActivity);
				break;
			case CodeUtils.FORWARDONE:
				if(CodeUtils.hadLoadMusic){
					CodeUtils.musicFileList.get(CodeUtils.playingMusic).setLastTime(mediaPlayer.getCurrentPosition());
				}
				int id = CodeUtils.forwardMusic;
				CodeUtils.forwardMusic = CodeUtils.playingMusic;
				CodeUtils.playingMusic = id;
				PlayingUtils.startMusicPlaying(MusicService.this, mediaPlayer, intentToMusicActivity);
				break;
			default:
				break;
			}
		}
	}
}
