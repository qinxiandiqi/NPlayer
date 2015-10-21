package com.qinxiandiqi.nplayer.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;
import android.widget.VideoView;

import com.qinxiandiqi.nplayer.R;
import com.qinxiandiqi.nplayer.VideoActivity;

public class PlayingUtils {

//	删除音频列表项
	public static final void delMusicItem(Context context) {
		if(CodeUtils.playingMusic == CodeUtils.NOTNEXTONE){
			Toast.makeText(context, R.string.delfail, Toast.LENGTH_SHORT).show();
			return;
		}
		CodeUtils.musicFileList.remove(CodeUtils.playingMusic);
		CodeUtils.musicListLength = CodeUtils.musicFileList.size();
		CodeUtils.hadLoadMusic = false;
		if(CodeUtils.musicListLength == 0){
			CodeUtils.playingMusic = CodeUtils.NOTNEXTONE;
			CodeUtils.forwardMusic = CodeUtils.NOTNEXTONE;
		}else if(CodeUtils.musicListLength == CodeUtils.playingMusic){
			CodeUtils.playingMusic--;
			CodeUtils.forwardMusic--;
		}else if(CodeUtils.playingMusic < CodeUtils.forwardMusic){
			CodeUtils.forwardMusic--;
		}
		CodeUtils.musicAdapter.notifyDataSetChanged();
		Toast.makeText(context, R.string.delsuccess, Toast.LENGTH_SHORT).show();
	}

//	删除视频列表项
	public static final void delVideoItem(Context context) {
		if(CodeUtils.playingVideo == CodeUtils.NOTNEXTONE){
			Toast.makeText(context, R.string.delfail, Toast.LENGTH_SHORT).show();
			return;
		}
		CodeUtils.videoFileList.remove(CodeUtils.playingVideo);
		CodeUtils.videoListLength = CodeUtils.videoFileList.size();
		if(CodeUtils.videoListLength == 0){
			CodeUtils.playingVideo = CodeUtils.NOTNEXTONE;
			CodeUtils.hadLoadMusic = false;
		}else if(CodeUtils.videoListLength == CodeUtils.playingVideo){
			CodeUtils.playingVideo--;
		}
		CodeUtils.videoAdapter.notifyDataSetChanged();
		Toast.makeText(context, R.string.delsuccess, Toast.LENGTH_SHORT).show();
	}

	public static final void startVideoActivity(Context context, Intent intent) {
		intent.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.PAUSE);
		context.sendBroadcast(intent);
		context.startActivity(new Intent(context, VideoActivity.class));
	}

//	播放视频前判别工作和播放
	public static final void startVideoPlaying(Context context,
			VideoView videoView) {
		if (CodeUtils.playingVideo != CodeUtils.NOTNEXTONE) {
			String path = CodeUtils.videoFileList.get(CodeUtils.playingVideo)
					.getMediaPath();
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				videoView.setVideoPath(path);
				videoView.requestFocus();
				CodeUtils.videoFileList.get(CodeUtils.playingVideo)
						.setMediaLength(videoView.getDuration());
				videoView.seekTo(CodeUtils.videoFileList.get(
						CodeUtils.playingVideo).getLastTime());
				videoView.start();
			} else {
				CodeUtils.videoFileList.remove(CodeUtils.playingVideo);
				CodeUtils.videoListLength = CodeUtils.videoFileList.size();
				Toast.makeText(context, R.string.palyfail, Toast.LENGTH_SHORT)
						.show();
				if(CodeUtils.videoListLength == 0){
					CodeUtils.playingVideo = CodeUtils.NOTNEXTONE;
					return;
				}
				if(CodeUtils.videoListLength == CodeUtils.playingVideo){
					CodeUtils.playingVideo--;
				}
				PlayingUtils.startVideoPlaying(context, videoView);
			}
		}
	}

	public static final void startMusicPlaying(Context context, MediaPlayer mediaPlayer, Intent intent){
		if(CodeUtils.playingMusic != CodeUtils.NOTNEXTONE){
			File file = new File(CodeUtils.musicFileList.get(CodeUtils.playingMusic).getMediaPath());
			if(file.exists()&&file.isFile()){
				mediaPlayer.reset();
				try {
					mediaPlayer.setDataSource(CodeUtils.musicFileList.get(CodeUtils.playingMusic).getMediaPath());
					mediaPlayer.prepare();
					CodeUtils.hadLoadMusic = true;
					CodeUtils.musicFileList.get(CodeUtils.playingMusic).setMediaLength(mediaPlayer.getDuration());
					mediaPlayer.seekTo(CodeUtils.musicFileList.get(CodeUtils.playingMusic).getLastTime());
					mediaPlayer.start();
					CodeUtils.playingMusicState = CodeUtils.PLAYING;
					intent.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.PLAYING);
					context.sendBroadcast(intent);
					CodeUtils.musicAdapter.notifyDataSetChanged();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e){
					Toast.makeText(context, R.string.setdatafail, Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}else{
				CodeUtils.musicFileList.remove(CodeUtils.playingMusic);
				CodeUtils.musicListLength = CodeUtils.musicFileList.size();
				Toast.makeText(context, R.string.palyfail, Toast.LENGTH_SHORT)
						.show();
				if(CodeUtils.musicListLength == 0){
					CodeUtils.playingMusic = CodeUtils.NOTNEXTONE;
					CodeUtils.hadLoadMusic = false;
					return;
				}
				if(CodeUtils.musicListLength == CodeUtils.playingVideo){
					CodeUtils.playingMusic--;
				}
				PlayingUtils.startMusicPlaying(context, mediaPlayer, intent);
			}
		}
	}
	
//	停止播放音乐
	public static final void stopMusic(Context context, MediaPlayer mediaPlayer, Intent intent){
		if (CodeUtils.hadLoadMusic) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
			mediaPlayer.seekTo(0);
			CodeUtils.musicFileList.get(CodeUtils.playingMusic).setLastTime(mediaPlayer.getCurrentPosition());
			CodeUtils.playingMusicState = CodeUtils.STOP;
			intent.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.STOP);
			context.sendBroadcast(intent);
			CodeUtils.musicAdapter.notifyDataSetChanged();
		}
	}
	
//	播放下一首音乐
	public static final void startNextMusic(Context context, MediaPlayer mediaPlayer, Intent intent){
		CodeUtils.forwardMusic = CodeUtils.playingMusic;
		int next = PlayingUtils.nextOneId(CodeUtils.playingMusicModel, CodeUtils.musicListLength, CodeUtils.playingMusic);
		if(next != CodeUtils.NOTNEXTONE){
			if(CodeUtils.hadLoadMusic){
				CodeUtils.musicFileList.get(CodeUtils.playingMusic).setLastTime(mediaPlayer.getCurrentPosition());
			}
			CodeUtils.playingMusic = next;
			PlayingUtils.startMusicPlaying(context, mediaPlayer, intent);
		}else{
			PlayingUtils.stopMusic(context, mediaPlayer, intent);
		}
	}
	
//	根据播放模式返回对应模式下一首id
	public static final int nextOneId(int model, int length, int nowPoint) {
		if (length == 0) {
			return CodeUtils.NOTNEXTONE;
		}
		switch (model) {
		case CodeUtils.LISTLOOP:
			nowPoint++;
			if (nowPoint == length) {
				return 0;
			} else {
				return nowPoint;
			}
		case CodeUtils.RANDOM:
			return (int) (Math.random() * (length - 1));
		case CodeUtils.ORDERPLAY:
			nowPoint++;
			if (nowPoint == length) {
				return CodeUtils.NOTNEXTONE;
			} else {
				return nowPoint;
			}
		case CodeUtils.SINGLECYCLE:
			return nowPoint;
		case CodeUtils.SINGLEPLAY:
			return CodeUtils.NOTNEXTONE;
		default:
			return CodeUtils.NOTNEXTONE;
		}
	}
	
//	获取播放模式图标资源ID
	public static int getModelIconID(int model){
		switch (model) {
		case CodeUtils.LISTLOOP:
			return R.drawable.listloop;
		case CodeUtils.RANDOM:
			return R.drawable.random;
		case CodeUtils.SINGLECYCLE:
			return R.drawable.singlecycle;
		case CodeUtils.SINGLEPLAY:
			return R.drawable.singleplay;
		case CodeUtils.ORDERPLAY:
			return R.drawable.orderplay;
		default:
			return R.drawable.listloop;
		}
	}
	
//	获取播放模式的字符串资源ID
	public static int getModelStringID(int model){
		switch (model) {
		case CodeUtils.LISTLOOP:
			return R.string.listloop;
		case CodeUtils.SINGLECYCLE:
			return R.string.singlecycle;
		case CodeUtils.SINGLEPLAY:
			return R.string.singleplay;
		case CodeUtils.ORDERPLAY:
			return R.string.orderplay;
		case CodeUtils.RANDOM:
			return R.string.ramdom;
		default:
			return R.string.listloop;
		}
	}
}
