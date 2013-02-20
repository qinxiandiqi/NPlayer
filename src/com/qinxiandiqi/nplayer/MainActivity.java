package com.qinxiandiqi.nplayer;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.qinxiandiqi.adapter.MusicAdapter;
import com.qinxiandiqi.adapter.VideoAdapter;
import com.qinxiandiqi.bean.MusicFile;
import com.qinxiandiqi.bean.VideoFile;
import com.qinxiandiqi.service.MusicService;
import com.qinxiandiqi.utils.CodeUtils;
import com.qinxiandiqi.utils.FileUtils;
import com.qinxiandiqi.utils.PlayingUtils;
import com.qinxiandiqi.utils.PreferenceUtils;

public class MainActivity extends TabActivity {

	/**
	 * @author 林佳楠  
	 * sina Weibo：@琴弦第七
	 */
	
	private ListView musicListView;
	private ListView videoListView;
	private Intent musicIntent;
	private Builder modelBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.activity_main,
				tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec(CodeUtils.MUSICTAB)
				.setIndicator("", getResources().getDrawable(R.drawable.music))
				.setContent(R.id.musiclistview));
		tabHost.addTab(tabHost.newTabSpec(CodeUtils.VIDEOTAB)
				.setIndicator("", getResources().getDrawable(R.drawable.video))
				.setContent(R.id.videolistview));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				CodeUtils.mainTab = tabId;
			}
		});

		musicIntent = new Intent();
		musicIntent.setAction(CodeUtils.MUSICSERVICEACTION);
		
		musicListView = (ListView) findViewById(R.id.musiclistview);
		videoListView = (ListView) findViewById(R.id.videolistview);
		
		CodeUtils.musicFileList = new ArrayList<MusicFile>();
		CodeUtils.videoFileList = new ArrayList<VideoFile>();
		FileUtils.readMusicListFromXML(this);
		FileUtils.readVideoListFromXML(this);
		CodeUtils.musicListLength = CodeUtils.musicFileList.size();
		CodeUtils.videoListLength = CodeUtils.videoFileList.size();
		CodeUtils.musicAdapter = new MusicAdapter(this, R.drawable.music);
		CodeUtils.videoAdapter = new VideoAdapter(this, R.drawable.video);
		PreferenceUtils.readPreferences(this);
		
		musicListView.setAdapter(CodeUtils.musicAdapter);
		videoListView.setAdapter(CodeUtils.videoAdapter);

		musicListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (CodeUtils.playingMusic != position) {
					CodeUtils.forwardMusic = CodeUtils.playingMusic;
					CodeUtils.playingMusic = position;
					musicIntent.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.CUTETONEXT);
				} else {
					if (CodeUtils.playingMusicState == CodeUtils.PLAYING) {
						musicIntent.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.PAUSE);
					} else {
						musicIntent.putExtra(CodeUtils.MEDIAOPCODE, CodeUtils.START);
					}
				}
				MainActivity.this.sendBroadcast(musicIntent);
			}
		});
		videoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (CodeUtils.playingVideo != position) {
					CodeUtils.playingVideoState = CodeUtils.PLAYING;
					CodeUtils.playingVideo = position;
					PlayingUtils.startVideoActivity(MainActivity.this,musicIntent);
				} else {
					if (CodeUtils.playingVideoState == CodeUtils.PLAYING) {
						CodeUtils.playingVideoState = CodeUtils.PAUSE;
					} else {
						CodeUtils.playingVideoState = CodeUtils.PLAYING;
						PlayingUtils.startVideoActivity(MainActivity.this, musicIntent);
					}
				}
				CodeUtils.videoAdapter.notifyDataSetChanged();
			}
		});

		startService(new Intent(this, MusicService.class));
		
		modelBuilder = new Builder(this);
		modelBuilder.setIcon(R.drawable.model);
		modelBuilder.setTitle(R.string.choosemodel);
		modelBuilder.setItems(
				new String[] { getResources().getString(R.string.listloop),
						getResources().getString(R.string.singlecycle),
						getResources().getString(R.string.ramdom),
						getResources().getString(R.string.orderplay),
						getResources().getString(R.string.singleplay) },
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(CodeUtils.mainTab.equals(CodeUtils.MUSICTAB)){
							CodeUtils.playingMusicModel = CodeUtils.LISTLOOP + which;
						}else{
							CodeUtils.playingVideoModel = CodeUtils.LISTLOOP + which;
						}
					}
				});


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CodeUtils.SEARCHREQUESTCODE
				&& resultCode == CodeUtils.SEARCHREQUESTCODE) {
			ArrayList<CharSequence> searchPaths = data
					.getCharSequenceArrayListExtra(CodeUtils.SEARCHPATHLIST);
			for (int i = 0; i < searchPaths.size(); i++) {
				FileUtils.scanMedia(searchPaths.get(i).toString());
			}
		}
		CodeUtils.musicListLength = CodeUtils.musicFileList.size();
		CodeUtils.videoListLength = CodeUtils.videoFileList.size();
		CodeUtils.musicAdapter.notifyDataSetChanged();
		CodeUtils.videoAdapter.notifyDataSetChanged();
	}


	@Override
	protected void onDestroy() {
//		stopService(new Intent(this,MusicService.class));
		FileUtils.writeMusicListToXML(this);
		FileUtils.writeVideoListToXML(this);
		PreferenceUtils.writePreferences(this);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, 1, R.string.musicmenu).setIcon(R.drawable.music);
		menu.add(Menu.NONE, 2, 2, R.string.videomenu).setIcon(R.drawable.video);
		menu.add(Menu.NONE, 3, 3, R.string.modelmenu).setIcon(R.drawable.model);
		menu.add(Menu.NONE, 4, 4, R.string.searchmenu).setIcon(
				R.drawable.search);
		menu.add(Menu.NONE, 5, 5, R.string.delmenu).setIcon(R.drawable.del);
		menu.add(Menu.NONE, 6, 6, R.string.quitmenu).setIcon(R.drawable.quit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (CodeUtils.mainTab.equals(CodeUtils.MUSICTAB)) {
			menu.getItem(2)
					.setIcon(
							PlayingUtils
									.getModelIconID(CodeUtils.playingMusicModel))
					.setTitle(
							PlayingUtils
									.getModelStringID(CodeUtils.playingMusicModel));

		} else {
			menu.getItem(2)
					.setIcon(
							PlayingUtils
									.getModelIconID(CodeUtils.playingVideoModel))
					.setTitle(PlayingUtils.getModelStringID(CodeUtils.playingVideoModel));
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			startActivity(new Intent(this,MusicActivity.class));
			break;
		case 2:
			PlayingUtils.startVideoActivity(this,musicIntent);
			break;
		case 3:
			modelBuilder.create().show();
			break;
		case 4:
			Intent searchIntent = new Intent(this, SearchActivity.class);
			searchIntent.putExtra(CodeUtils.SEARCHPATHFOREXTRA, Environment
					.getExternalStorageDirectory().getAbsolutePath());
			startActivityForResult(searchIntent, CodeUtils.SEARCHREQUESTCODE);
			break;
		case 5:
			if(CodeUtils.mainTab.equals(CodeUtils.MUSICTAB)){
				PlayingUtils.delMusicItem(this);
			}else{
				PlayingUtils.delVideoItem(this);
			}
			break;
		case 6:
			stopService(new Intent(this,MusicService.class));
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}
