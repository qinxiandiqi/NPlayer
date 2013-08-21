package com.qinxiandiqi.utils;

import java.util.HashMap;

public class MediaUtils {

	// Audio file types
	public static final int FILE_TYPE_MP3 = 1;
	public static final int FILE_TYPE_M4A = 2;
	public static final int FILE_TYPE_WAV = 3;
	public static final int FILE_TYPE_AMR = 4;
	public static final int FILE_TYPE_AWB = 5;
	public static final int FILE_TYPE_WMA = 6;
	public static final int FILE_TYPE_OGG = 7;
	public static final int FILE_TYPE_AAC = 8;
	public static final int FILE_TYPE_MKA = 9;
	public static final int FILE_TYPE_FLAC = 10;
	private static final int FIRST_AUDIO_FILE_TYPE = FILE_TYPE_MP3;
	private static final int LAST_AUDIO_FILE_TYPE = FILE_TYPE_FLAC;

	// MIDI file types
	public static final int FILE_TYPE_MID = 11;
	public static final int FILE_TYPE_SMF = 12;
	public static final int FILE_TYPE_IMY = 13;
	private static final int FIRST_MIDI_FILE_TYPE = FILE_TYPE_MID;
	private static final int LAST_MIDI_FILE_TYPE = FILE_TYPE_IMY;

	// Video file types
	public static final int FILE_TYPE_MP4 = 21;
	public static final int FILE_TYPE_M4V = 22;
	public static final int FILE_TYPE_3GPP = 23;
	public static final int FILE_TYPE_3GPP2 = 24;
	public static final int FILE_TYPE_WMV = 25;
	public static final int FILE_TYPE_ASF = 26;
	public static final int FILE_TYPE_MKV = 27;
	public static final int FILE_TYPE_MP2TS = 28;
	public static final int FILE_TYPE_AVI = 29;
	public static final int FILE_TYPE_WEBM = 30;
	private static final int FIRST_VIDEO_FILE_TYPE = FILE_TYPE_MP4;
	private static final int LAST_VIDEO_FILE_TYPE = FILE_TYPE_WEBM;

	// Image file types
	public static final int FILE_TYPE_JPEG = 31;
	public static final int FILE_TYPE_GIF = 32;
	public static final int FILE_TYPE_PNG = 33;
	public static final int FILE_TYPE_BMP = 34;
	public static final int FILE_TYPE_WBMP = 35;
	public static final int FILE_TYPE_WEBP = 36;
	private static final int FIRST_IMAGE_FILE_TYPE = FILE_TYPE_JPEG;
	private static final int LAST_IMAGE_FILE_TYPE = FILE_TYPE_WEBP;

	// Playlist file types
	public static final int FILE_TYPE_M3U = 41;
	public static final int FILE_TYPE_PLS = 42;
	public static final int FILE_TYPE_WPL = 43;
	public static final int FILE_TYPE_HTTPLIVE = 44;

	private static final int FIRST_PLAYLIST_FILE_TYPE = FILE_TYPE_M3U;
	private static final int LAST_PLAYLIST_FILE_TYPE = FILE_TYPE_HTTPLIVE;

	// Drm file types
	public static final int FILE_TYPE_FL = 51;
	private static final int FIRST_DRM_FILE_TYPE = FILE_TYPE_FL;
	private static final int LAST_DRM_FILE_TYPE = FILE_TYPE_FL;

	// Other popular file types
	public static final int FILE_TYPE_TEXT = 100;
	public static final int FILE_TYPE_HTML = 101;
	public static final int FILE_TYPE_PDF = 102;
	public static final int FILE_TYPE_XML = 103;
	public static final int FILE_TYPE_MS_WORD = 104;
	public static final int FILE_TYPE_MS_EXCEL = 105;
	public static final int FILE_TYPE_MS_POWERPOINT = 106;
	public static final int FILE_TYPE_ZIP = 107;

	public static class MediaFileType {
		public final int fileType;
		public final String mimeType;

		MediaFileType(int fileType, String mimeType) {
			this.fileType = fileType;
			this.mimeType = mimeType;
		}
	}

	private static HashMap<String, MediaFileType> sFileTypeMap = new HashMap<String, MediaFileType>();
	private static HashMap<String, Integer> sMimeTypeMap = new HashMap<String, Integer>();

	static void addFileType(String extension, int fileType, String mimeType) {
		sFileTypeMap.put(extension, new MediaFileType(fileType, mimeType));
		sMimeTypeMap.put(mimeType, Integer.valueOf(fileType));
	}

	static {
		addFileType("MP3", FILE_TYPE_MP3, "audio/mpeg");
		addFileType("M4A", FILE_TYPE_M4A, "audio/mp4");
		addFileType("WAV", FILE_TYPE_WAV, "audio/x-wav");
		addFileType("AMR", FILE_TYPE_AMR, "audio/amr");
		addFileType("AWB", FILE_TYPE_AWB, "audio/amr-wb");
		addFileType("WMA", FILE_TYPE_WMA, "audio/x-ms-wma");

		addFileType("OGG", FILE_TYPE_OGG, "application/ogg");
		addFileType("OGA", FILE_TYPE_OGG, "application/ogg");
		addFileType("AAC", FILE_TYPE_AAC, "audio/aac");
		addFileType("AAC", FILE_TYPE_AAC, "audio/aac-adts");
		addFileType("MKA", FILE_TYPE_MKA, "audio/x-matroska");

		addFileType("MID", FILE_TYPE_MID, "audio/midi");
		addFileType("MIDI", FILE_TYPE_MID, "audio/midi");
		addFileType("XMF", FILE_TYPE_MID, "audio/midi");
		addFileType("RTTTL", FILE_TYPE_MID, "audio/midi");
		addFileType("SMF", FILE_TYPE_SMF, "audio/sp-midi");
		addFileType("IMY", FILE_TYPE_IMY, "audio/imelody");
		addFileType("RTX", FILE_TYPE_MID, "audio/midi");
		addFileType("OTA", FILE_TYPE_MID, "audio/midi");
		addFileType("MXMF", FILE_TYPE_MID, "audio/midi");

		addFileType("MPEG", FILE_TYPE_MP4, "video/mpeg");
		addFileType("MPG", FILE_TYPE_MP4, "video/mpeg");
		addFileType("MP4", FILE_TYPE_MP4, "video/mp4");
		addFileType("M4V", FILE_TYPE_M4V, "video/mp4");
		addFileType("3GP", FILE_TYPE_3GPP, "video/3gpp");
		addFileType("3GPP", FILE_TYPE_3GPP, "video/3gpp");
		addFileType("3G2", FILE_TYPE_3GPP2, "video/3gpp2");
		addFileType("3GPP2", FILE_TYPE_3GPP2, "video/3gpp2");
		addFileType("MKV", FILE_TYPE_MKV, "video/x-matroska");
		addFileType("WEBM", FILE_TYPE_WEBM, "video/webm");
		addFileType("TS", FILE_TYPE_MP2TS, "video/mp2ts");
		addFileType("AVI", FILE_TYPE_AVI, "video/avi");

		addFileType("WMV", FILE_TYPE_WMV, "video/x-ms-wmv");
		addFileType("ASF", FILE_TYPE_ASF, "video/x-ms-asf");

		addFileType("JPG", FILE_TYPE_JPEG, "image/jpeg");
		addFileType("JPEG", FILE_TYPE_JPEG, "image/jpeg");
		addFileType("GIF", FILE_TYPE_GIF, "image/gif");
		addFileType("PNG", FILE_TYPE_PNG, "image/png");
		addFileType("BMP", FILE_TYPE_BMP, "image/x-ms-bmp");
		addFileType("WBMP", FILE_TYPE_WBMP, "image/vnd.wap.wbmp");
		addFileType("WEBP", FILE_TYPE_WEBP, "image/webp");

		addFileType("M3U", FILE_TYPE_M3U, "audio/x-mpegurl");
		addFileType("M3U", FILE_TYPE_M3U, "application/x-mpegurl");
		addFileType("PLS", FILE_TYPE_PLS, "audio/x-scpls");
		addFileType("WPL", FILE_TYPE_WPL, "application/vnd.ms-wpl");
		addFileType("M3U8", FILE_TYPE_HTTPLIVE, "application/vnd.apple.mpegurl");
		addFileType("M3U8", FILE_TYPE_HTTPLIVE, "audio/mpegurl");
		addFileType("M3U8", FILE_TYPE_HTTPLIVE, "audio/x-mpegurl");

		addFileType("FL", FILE_TYPE_FL, "application/x-android-drm-fl");

		addFileType("TXT", FILE_TYPE_TEXT, "text/plain");
		addFileType("HTM", FILE_TYPE_HTML, "text/html");
		addFileType("HTML", FILE_TYPE_HTML, "text/html");
		addFileType("PDF", FILE_TYPE_PDF, "application/pdf");
		addFileType("DOC", FILE_TYPE_MS_WORD, "application/msword");
		addFileType("XLS", FILE_TYPE_MS_EXCEL, "application/vnd.ms-excel");
		addFileType("PPT", FILE_TYPE_MS_POWERPOINT, "application/mspowerpoint");
		addFileType("FLAC", FILE_TYPE_FLAC, "audio/flac");
		addFileType("ZIP", FILE_TYPE_ZIP, "application/zip");
	}

	public static boolean isAudioFileType(int fileType) {
		return ((fileType >= FIRST_AUDIO_FILE_TYPE && fileType <= LAST_AUDIO_FILE_TYPE) || (fileType >= FIRST_MIDI_FILE_TYPE && fileType <= LAST_MIDI_FILE_TYPE));
	}

	public static boolean isVideoFileType(int fileType) {
		return (fileType >= FIRST_VIDEO_FILE_TYPE && fileType <= LAST_VIDEO_FILE_TYPE);
	}

	public static boolean isImageFileType(int fileType) {
		return (fileType >= FIRST_IMAGE_FILE_TYPE && fileType <= LAST_IMAGE_FILE_TYPE);
	}

	public static boolean isPlayListFileType(int fileType) {
		return (fileType >= FIRST_PLAYLIST_FILE_TYPE && fileType <= LAST_PLAYLIST_FILE_TYPE);
	}

	public static boolean isDrmFileType(int fileType) {
		return (fileType >= FIRST_DRM_FILE_TYPE && fileType <= LAST_DRM_FILE_TYPE);
	}

	public static MediaFileType getFileType(String path) {
		int lastDot = path.lastIndexOf(".");
		if (lastDot < 0)
			return null;
		return sFileTypeMap.get(path.substring(lastDot + 1).toUpperCase());
	}

//	根据文件路径判断文件是否为音频
	public static boolean isAudioFile(String path){
		if(getFileType(path)==null){
			return false;
		}
		return isAudioFileType(getFileType(path).fileType);
	}
	
//	根据文件路径判断文件是否为视频
	public static boolean isVideoFile(String path){
		if(getFileType(path)==null){
			return false;
		}
		return isVideoFileType(getFileType(path).fileType);
	}
	
//	判断媒体类型是否为多媒体
	public static boolean isMimeTypeMedia(String mimeType) {
		int fileType = getFileTypeForMimeType(mimeType);
		return isAudioFileType(fileType) || isVideoFileType(fileType)
				|| isImageFileType(fileType) || isPlayListFileType(fileType);
	}

//	根据文件路径获取文件名（除去扩展名）
	public static String getFileTitle(String path) {
		int lastSlash = path.lastIndexOf('/');
		if (lastSlash >= 0) {
			lastSlash++;
			if (lastSlash < path.length()) {
				path = path.substring(lastSlash);
			}
		}
		int lastDot = path.lastIndexOf('.');
		if (lastDot > 0) {
			path = path.substring(0, lastDot);
		}
		return path;
	}

//	将媒体类型转换为对应的整型值
	public static int getFileTypeForMimeType(String mimeType) {
		Integer value = sMimeTypeMap.get(mimeType);
		return (value == null ? 0 : value.intValue());
	}

//	根据文件路径获取文件的媒体类型
	public static String getMimeTypeForFile(String path) {
		MediaFileType mediaFileType = getFileType(path);
		return (mediaFileType == null ? null : mediaFileType.mimeType);
	}

}