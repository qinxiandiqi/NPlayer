package com.qinxiandiqi.nplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qinxiandiqi.nplayer.utils.CodeUtils;
import com.qinxiandiqi.nplayer.utils.FileUtils;

public class SearchActivity extends Activity {

	private ListView listView;
	private Button searchButton;
	private List<String> searchPathList;
	private String searchParentPath;
	private List<File> searchFileList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		listView = (ListView) findViewById(R.id.searchlistview);
		searchButton = (Button) findViewById(R.id.searchbutton);
		searchPathList = new ArrayList<String>();
		searchFileList = new ArrayList<File>();
		searchParentPath = getIntent().getExtras().getString(
				CodeUtils.SEARCHPATHFOREXTRA);
		searchFileList = FileUtils.getSearchFileByPath(searchParentPath,
				searchFileList);
		SearchAdapter searchAdapter = new SearchAdapter(this);
		listView.setAdapter(searchAdapter);
	}

	public void searchOnClick(View v) {
		Intent intent = getIntent();
		intent.putStringArrayListExtra(CodeUtils.SEARCHPATHLIST,
				(ArrayList<String>) searchPathList);
		SearchActivity.this.setResult(CodeUtils.SEARCHRESULTCODE, intent);
		SearchActivity.this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CodeUtils.SEARCHREQUESTCODE
				&& resultCode == CodeUtils.SEARCHRESULTCODE) {
			SearchActivity.this.setResult(CodeUtils.SEARCHRESULTCODE, data);
			SearchActivity.this.finish();
		}
	}

//	搜索列表适配器
	public class SearchAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private Boolean[] state = new Boolean[searchFileList.size()];

		public SearchAdapter(Context context) {
			super();
			this.inflater = LayoutInflater.from(context);
			for (int i = 0; i < state.length; i++) {
				state[i] = false;
			}
		}

		@Override
		public int getCount() {
			return searchFileList.size();
		}

		@Override
		public Object getItem(int position) {
			return searchFileList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.searchitem, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.searchitemimage);
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.searchitemtext);
				viewHolder.checkBox = (CheckBox) convertView
						.findViewById(R.id.searchitemcheckbox);
				convertView.setTag(viewHolder);
			} else {
//				Log.i(CodeUtils.SEARCHTAG, "view is reuse");
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView.setText(searchFileList.get(position).getName());
			if (position == 0) {
				viewHolder.imageView.setImageResource(R.drawable.upback);
				viewHolder.textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SearchActivity.this.finish();
					}
				});
			} else {
				if (searchFileList.get(position).isDirectory()) {
					viewHolder.imageView.setImageResource(R.drawable.folder);
					viewHolder.textView
							.setOnClickListener(new OnClickFolderListener(
									position));
				} else {
					viewHolder.imageView.setImageResource(R.drawable.file);
					viewHolder.textView
							.setOnClickListener(new OnClickFileListener(
									position, state, viewHolder.checkBox));
				}
			}
			viewHolder.checkBox
					.setOnCheckedChangeListener(new SearchItemOnCheckedChangeListener(
							position, state));
			viewHolder.checkBox.setChecked(state[position]);
//			Log.i(CodeUtils.SEARCHTAG, "request" + position + " and state="
//					+ state[position] + " and length=" + state.length);
			return convertView;
		}

//		单击文件夹选项监听器
		public class OnClickFolderListener implements OnClickListener {
			private int id;

			public OnClickFolderListener(int id) {
				this.id = id;
			}

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SearchActivity.this,
						SearchActivity.class);
				intent.putExtra(CodeUtils.SEARCHPATHFOREXTRA, searchFileList
						.get(id).getAbsolutePath());
				SearchActivity.this.startActivityForResult(intent,
						CodeUtils.SEARCHREQUESTCODE);
			}

		}

//		单击文件选项监听器
		public class OnClickFileListener implements OnClickListener {
			private CheckBox checkBox;
			private int id;
			private Boolean[] state;

			public OnClickFileListener(int id, Boolean[] state,
					CheckBox checkBox) {
				this.checkBox = checkBox;
				this.id = id;
				this.state = state;
			}

			@Override
			public void onClick(View v) {
				state[id] = !state[id];
				checkBox.setChecked(state[id]);
			}
		}

//		多选框按钮状态改变监听器
		public class SearchItemOnCheckedChangeListener implements
				OnCheckedChangeListener {

			private int id;
			private Boolean[] state;

			public SearchItemOnCheckedChangeListener(int id, Boolean[] state) {
				this.id = id;
				this.state = state;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				state[id] = isChecked;
				if (isChecked) {
					searchPathList
							.add(searchFileList.get(id).getAbsolutePath());
				} else {
					searchPathList.remove(searchFileList.get(id)
							.getAbsolutePath());
				}
				if (searchPathList.isEmpty() || searchPathList == null) {
					searchButton.setVisibility(Button.INVISIBLE);
				} else {
					searchButton.setVisibility(Button.VISIBLE);
				}
			}
		}
	}

	static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
		public CheckBox checkBox;
	}
}
