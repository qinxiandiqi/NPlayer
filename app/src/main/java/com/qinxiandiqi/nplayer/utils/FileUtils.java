package com.qinxiandiqi.nplayer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.qinxiandiqi.nplayer.bean.MusicFile;
import com.qinxiandiqi.nplayer.bean.VideoFile;
import com.qinxiandiqi.nplayer.R;

public class FileUtils {

	// 获取path路径下文件夹和媒体文件
	public static final List<File> getSearchFileByPath(String path,
			List<File> searchFileList) {
		File file = new File(path);
		if (file.isDirectory()) {
			searchFileList.add(file);
			File[] fileArray = file.listFiles();
			for (File item : fileArray) {
				if (item.isDirectory()) {
					searchFileList.add(item);
				} else if (item.isFile()) {
					if (MediaUtils.isAudioFile(item.getAbsolutePath())
							|| MediaUtils.isVideoFile(item.getAbsolutePath())) {
						searchFileList.add(item);
					}
				}
			}
		}
		return searchFileList;
	}

	// 扫描path下的媒体文件
	public static final void scanMedia(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {
				return;
			}
			for (File item : files) {
				scanMedia(item.getAbsolutePath());
			}
		} else if (file.isFile()) {
			if (MediaUtils.isAudioFile(path)) {
				for (int i = 0; i < CodeUtils.musicListLength; i++) {
					if (path.equals(CodeUtils.musicFileList.get(i)
							.getMediaPath()))
						return;
				}
				MusicFile musicFile = new MusicFile();
				musicFile.setMediaPath(path);
				musicFile.setMediaName(MediaUtils.getFileTitle(path));
				CodeUtils.musicFileList.add(musicFile);
			}
			if (MediaUtils.isVideoFile(path)) {
				for (int i = 0; i < CodeUtils.videoListLength; i++) {
					if (path.equals(CodeUtils.videoFileList.get(i).getMediaPath()))
						return;
				}
				VideoFile videoFile = new VideoFile();
				videoFile.setMediaName(MediaUtils.getFileTitle(path));
				videoFile.setMediaPath(path);
				CodeUtils.videoFileList.add(videoFile);
			}
		}
	}

	public static final void readMusicListFromXML(Context context) {
		File file = new File(context.getFilesDir(), "MusicList.xml");
		if (file.exists() && (file.length() != 0)) {
			try {
				XMLUtils.readMusicXML(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				Toast.makeText(context, R.string.notfoundlistfile,
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} catch (Exception e) {
				Toast.makeText(context, R.string.readxmlfail,
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}

	}

	public static final void writeMusicListToXML(Context context) {

		File file = new File(context.getFilesDir(), "MusicList.xml");
		try {
			file.createNewFile();
			XMLUtils.writeMusicXML(new FileOutputStream(file));
		} catch (Exception e) {
			Toast.makeText(context, R.string.writexmlfail, Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public static final void readVideoListFromXML(Context context) {
		File file = new File(context.getFilesDir(), "VideoList.xml");
		if (file.exists() && (file.length() != 0)) {
			try {
				XMLUtils.readVideoXML(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				Toast.makeText(context, R.string.notfoundlistfile,
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} catch (Exception e) {
				Toast.makeText(context, R.string.readxmlfail,
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}

	public static final void writeVideoListToXML(Context context) {
		File file = new File(context.getFilesDir(), "VideoList.xml");
		try {
			file.createNewFile();
			XMLUtils.writeVideoXML(new FileOutputStream(file));
		} catch (Exception e) {
			Toast.makeText(context, R.string.writexmlfail, Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}
}
