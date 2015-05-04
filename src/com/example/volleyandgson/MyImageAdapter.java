package com.example.volleyandgson;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class MyImageAdapter extends BaseAdapter {
	ArrayList<Data> urlSet=new ArrayList<Data>();
	LayoutInflater inflater;
	ImageLoader imageLoader;
	public MyImageAdapter(Context context,ArrayList<Data> set, ImageLoader imageLoader){
		inflater=LayoutInflater.from(context);
		this.urlSet=set;
		this.imageLoader=imageLoader;
	}
	public MyImageAdapter(Context context,ArrayList<Data> set, RequestQueue queue){
		inflater=LayoutInflater.from(context);
		this.urlSet=set;

		this.imageLoader=new ImageLoader(queue, new BitmapCache());
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urlSet.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return urlSet.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_image, null);
			holder.iv_cotnent=(ImageView) convertView.findViewById(R.id.iv_content);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		String url=urlSet.get(position).getObjURL();
		if(url!=null){
			ImageListener listener=ImageLoader.getImageListener(holder.iv_cotnent, R.drawable.ic_launcher, R.drawable.ic_launcher);
			imageLoader.get(url, listener);
		}
		
		return convertView;
	}
	class ViewHolder{
		private ImageView iv_cotnent;
	}

}
