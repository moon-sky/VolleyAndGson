package com.example.volleyandgson;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	ListView lv_image;
	RequestQueue queue;
	ImageLoader imageLoader;
	JsonArrayRequest jsonArrayRequest;
	JsonObjectRequest jsonObjectRequest;
	public static  String imgAPIURL = "http://image.baidu.com/i?tn=baiduimagejson&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1349413075627_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&rn=10&pn=1";
	MyImageAdapter adapter;
	EditText et_content;
	Button btn_confirm;
	String content;
//	ImageView iv_test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv_image = (ListView) findViewById(R.id.lv_image);
		et_content=(EditText)findViewById(R.id.et_content);
		btn_confirm=(Button)findViewById(R.id.btn_commit);
		btn_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initJsonObjectRequest();
			}
		});
//		iv_test=(ImageView)findViewById(R.id.iv_content);
		initResponseQueue();
		imageLoader = new ImageLoader(queue, new BitmapCache());
		// initJsonArrayRequest();
//		initJsonObjectRequest();
//		initImageRequest();
//		initImageLoader();
	}

	public void initResponseQueue() {
		queue = Volley.newRequestQueue(this);

	}

	public void initJsonArrayRequest() {
		jsonArrayRequest = new JsonArrayRequest(imgAPIURL,
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray jsonArray) {
						Log.d("TAG", jsonArray.toString());

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("TAG", error.getMessage());

					}
				});
		queue.add(jsonArrayRequest);
	}
	public void initImageRequest() {
		ImageRequest imageRequest = new ImageRequest(
				"http://e.hiphotos.baidu.com/image/pic/item/b151f8198618367a6e191c612d738bd4b21ce5fe.jpg",
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
//						iv_test.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
//						iv_test.setImageResource(R.drawable.ic_launcher);
					}
				});
		queue.add(imageRequest);
	}
	public void initImageLoader() {
		ImageLoader imageLoader = new ImageLoader(queue,new BitmapCache());
//		ImageListener listener = ImageLoader.getImageListener(iv_test,
//				R.drawable.ic_launcher, R.drawable.ic_launcher);
//		imageLoader
//				.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
//						listener);
	}
	public void initJsonObjectRequest() {
		imgAPIURL+="&word="+(content=(et_content.getText().toString()==null||et_content.getText().toString().length()<=0)?"girl":et_content.getText().toString());
		jsonObjectRequest = new JsonObjectRequest(imgAPIURL, null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObj) {

						Gson gson = new Gson();
						ImageEntity entity = gson.fromJson(jsonObj.toString(),
								ImageEntity.class);
						if (entity != null && entity.data != null) {
							
//							ImageListener listener=ImageLoader.getImageListener(iv_test, R.drawable.ic_launcher, R.drawable.ic_launcher);
//							imageLoader.get(entity.data.get(0).getObjURL(), listener);
							
							adapter = new MyImageAdapter(
									getApplicationContext(), entity.data,queue);
							lv_image.setAdapter(adapter);
						}

						Log.d("TAG", "size:" + entity.getData().size());

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("TAG", error.getMessage());

					}
				});
		queue.add(jsonObjectRequest);
	}

}
