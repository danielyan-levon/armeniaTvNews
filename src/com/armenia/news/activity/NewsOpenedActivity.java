package com.armenia.news.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class NewsOpenedActivity extends Activity {

	private String title = "Title";
	private String date = "01.01.11";
	private String text = "";
	private String id = "";
	private String imageInnerHTML = "";
	private ViewFlipper flipper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras(); 

		if (extras != null) {
		    id = extras.getString("id");
		    title = extras.getString("header");
		    date = extras.getString("date");
		}
		
		setContentView(R.layout.news_opened_layout);
		
		flipper = (ViewFlipper)findViewById(R.id.news_opened_flipper);
		
		Typeface font= Typeface.createFromAsset(getAssets(), "arlamub.ttf");
		((TextView)findViewById(R.id.news_opened_title)).setTypeface(font);
		((TextView)findViewById(R.id.news_opened_title)).setText(title);
		((TextView)findViewById(R.id.news_opened_date)).setText(date);
		((TextView)findViewById(R.id.news_opened_date)).setTypeface(font);
		((TextView)findViewById(R.id.news_opened_text)).setText(text);
		
		
		((TextView)findViewById(R.id.news_opened_button_back_text)).setTypeface(font);
		((TextView)findViewById(R.id.news_opened_button_back_text)).setText("<Հետ");
		findViewById(R.id.news_opened_button_back).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Thread tr = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					getNewsContent(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		tr.start();
		
	}
	
	
	private void getNewsContent(String id) throws Exception{
		System.out.println("Sending request");
		URL url = new URL("http://news.armeniatv.com/android-api-single/?id="+id);
		URLConnection connection = url.openConnection();
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		String jsonString = builder.toString();
		final JSONObject newsJsonObject = new JSONObject(jsonString);
		imageInnerHTML = "<html><head></head><body style = \"padding:0px\" marginwidth=\"0\" marginheight=\"0\" leftmargin=\"0\" topmargin=\"0\">";
		imageInnerHTML+="<center><img src=\""+newsJsonObject.getString("image_path")+"\" .com\"  width=\"200\" /></center></body></html>";
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
					try {
						Typeface font= Typeface.createFromAsset(NewsOpenedActivity.this.getAssets(), "arlamu.ttf");
						((TextView)findViewById(R.id.news_opened_text)).setTypeface(font);
						((TextView)findViewById(R.id.news_opened_text)).setText(newsJsonObject.getString("text"));
						((WebView)findViewById(R.id.news_opened_image)).loadData(imageInnerHTML, "text/html", "utf-8");
						flipper.setDisplayedChild(1);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		});
		
	}
}
