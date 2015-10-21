package com.qinxiandiqi.nplayer.utils;

import android.widget.ImageView;

import com.qinxiandiqi.nplayer.R;

public class AdapterUtils {

	public static ImageView setImageView(int position, int palyingCode,
			int palyingState, ImageView image, int defaultimage) {
		if (position == palyingCode) {
			switch (palyingState) {
			case CodeUtils.PLAYING:
				image.setImageResource(R.drawable.playing);
				break;
			case CodeUtils.PAUSE:
				image.setImageResource(R.drawable.start);
				break;
			case CodeUtils.STOP:
				image.setImageResource(R.drawable.stop);
				break;
			default:
				break;
			}
		} else {
			image.setImageResource(defaultimage);
		}
		return image;
	}
}
