package com.qinxiandiqi.nplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.qinxiandiqi.nplayer.utils.CodeUtils;
import com.qinxiandiqi.nplayer.utils.PlayingUtils;

public class VideoActivity extends Activity {

	private VideoView videoView;
	private MediaController mediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		videoView = (VideoView) findViewById(R.id.videoview);

		mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);
		videoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				CodeUtils.videoFileList.get(CodeUtils.playingVideo)
						.setLastTime(0);
				int next = PlayingUtils.nextOneId(CodeUtils.playingVideoModel,
						CodeUtils.videoListLength, CodeUtils.playingVideo);
				if (next == CodeUtils.NOTNEXTONE) {
					Toast.makeText(VideoActivity.this, R.string.playdone,
							Toast.LENGTH_SHORT).show();
				} else {
					CodeUtils.playingVideo = next;
					PlayingUtils.startVideoPlaying(VideoActivity.this,
							videoView);
				}
			}
		});

		PlayingUtils.startVideoPlaying(this, videoView);

	}

	@Override
	protected void onPause() {
		if (CodeUtils.playingVideo != CodeUtils.NOTNEXTONE) {
			CodeUtils.playingVideoState = CodeUtils.PAUSE;
			CodeUtils.videoFileList.get(CodeUtils.playingVideo).setLastTime(
					videoView.getCurrentPosition());
			CodeUtils.videoAdapter.notifyDataSetChanged();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
