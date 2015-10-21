package com.qinxiandiqi.nplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinxiandiqi.nplayer.R;
import com.qinxiandiqi.nplayer.utils.AdapterUtils;
import com.qinxiandiqi.nplayer.utils.CodeUtils;

public class MusicAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private int defaultimage;

	public MusicAdapter(Context context, int defaultimage) {
		this.inflater = LayoutInflater.from(context);
		this.defaultimage = defaultimage;
	}

	@Override
	public int getCount() {
		return CodeUtils.musicFileList.size();
	}

	@Override
	public Object getItem(int position) {
		return CodeUtils.musicFileList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.mediaitem, null);
			holder = new Holder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.mediaitemimage);
			holder.textView = (TextView) convertView
					.findViewById(R.id.mediaitemtext);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if(position == CodeUtils.playingMusic){
			convertView.setBackgroundResource(R.color.clickitem);
		}else{
			convertView.setBackgroundResource(R.color.listitem);
		}
		
		holder.imageView = AdapterUtils.setImageView(position,
				CodeUtils.playingMusic, CodeUtils.playingMusicState,
				holder.imageView, defaultimage);

		holder.textView.setText((position + 1) + "."
				+ CodeUtils.musicFileList.get(position).getMediaName());
		
		return convertView;
	}

	static class Holder {
		public ImageView imageView;
		public TextView textView;
	}
}
