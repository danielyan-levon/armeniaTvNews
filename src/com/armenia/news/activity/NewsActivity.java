package com.armenia.news.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.armenia.news.adapter.NewsAdapter;
import com.armenia.news.adapter.NewsTabsAdapter;
import com.armenia.news.bean.NewsListBean;

public class NewsActivity extends Activity{

	private NewsAdapter adapter;
	private NewsTabsAdapter newsTabsAdapter;
	private HashMap<String, Integer> tabMap = new HashMap<String, Integer>();
	private ViewFlipper flipper = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.news_layout);
		
		ListView listOfNews = (ListView)findViewById(R.id.news_list);
		adapter = new NewsAdapter(this, 0);
		
		flipper = (ViewFlipper)findViewById(R.id.news_list_flipper);
		
//		for(int i = 0; i<10;i++){
//			NewsListBean bean = new NewsListBean("","HEADER HEADER ... ","TEXT TEXT TEXT ...");
//			adapter.add(bean);
//		}
		
		newsTabsAdapter = new NewsTabsAdapter(this, 0);
		Gallery newsTabsGallery = (Gallery)findViewById(R.id.news_tabs);
		newsTabsGallery.setAdapter(newsTabsAdapter);
		newsTabsAdapter.add("Աշխարհում");
		newsTabsAdapter.add("Քաղաքականություն");
		newsTabsAdapter.add("Տարածաշրջան");
		newsTabsAdapter.add("Հասարակություն");
		newsTabsAdapter.add("Տնտեսություն");
		newsTabsAdapter.add("Արտակարգ");
		newsTabsAdapter.add("Քրեական");
		newsTabsAdapter.add("Մամուլի տեսություն");
		newsTabsAdapter.add("Շոու բիզնես");
		
		tabMap.put("Աշխարհում", 5);
		tabMap.put("Քաղաքականություն", 8);
		tabMap.put("Տարածաշրջան", 26);
		tabMap.put("Հասարակություն", 23);
		tabMap.put("Տնտեսություն", 11);
		tabMap.put("Արտակարգ", 14);
		tabMap.put("Քրեական", 21);
		tabMap.put("Մամուլի տեսություն", 41);
		tabMap.put("Շոու բիզնես", 38);
		
		newsTabsAdapter.notifyDataSetChanged();
		newsTabsGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				final int selectedId = tabMap.get(newsTabsAdapter.getItem(position));
				adapter.clear();
				flipper.setDisplayedChild(0);
				Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 try {
								getNewsList(selectedId,25,1);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				});
				
				tr.start();
				
			}
		});
		
		listOfNews.setAdapter(adapter);
		
		Thread tr = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 try {
						getNewsList(5,25,1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		
		tr.start();
		
		
		listOfNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos,
					long posLong) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(NewsActivity.this, NewsOpenedActivity.class);
				i.putExtra("id", ((NewsListBean)adapter.getItemAtPosition(pos)).getId());
				i.putExtra("header", ((NewsListBean)adapter.getItemAtPosition(pos)).getHeader());
				i.putExtra("date", ((NewsListBean)adapter.getItemAtPosition(pos)).getDate());
				startActivity(i);
			}
		});
//		adapter.notifyDataSetChanged();
	}
	
	
	private void getNewsList(int id,int limit, int page) throws Exception{
		URL url = new URL("http://news.armeniatv.com/android_api/?perPage="+limit+"&title="+id+"&page="+page);
		URLConnection connection = url.openConnection();
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		String jsonString = builder.toString();
//		String jsonString = "[{\"id\":11847,\"header\":\"\u0532\u0561\u0566\u0574\u0561\u0569\u056b\u057e \u0570\u0561\u0576\u0565\u056c\u0578\u0582\u056f\u0576\u0565\u0580\u0578\u057e \u057d\u057a\u0561\u0576\u0578\u0582\u0569\u0575\u0578\u0582\u0576, \u0547\u056b\u0580\u0561\u056f\u056b \u0574\u0561\u0580\u0566\u0578\u0582\u0574, \u0574\u0561\u0570\u0561\u0581\u0561\u056e\u0576\u0565\u0580\u0568 \u056f\u0561\u0574 \u057d\u057a\u0561\u0576\u057e\u0561\u056e\u0576\u0565\u0580\u0568 2-\u0576 \u0565\u0576, \u057f\u0572\u0561\u0574\u0561\u0580\u0564 \u0587 \u056f\u056b\u0576:\",\"text\":\"\r\n\r\n\u0533\u0575\u0578\u0582\u0574\u0580\u056b\u0578\u0582\u0574 \u0561\u0575\u057d\u0585\u0580` \u0570\u0578\u0582\u0576\u056b\u057d\u056b 3-\u056b\u0576 \u056a\u0561\u0574\u0568\u00a0 16:30-\u056b \u057d\u0561\u0570\u0574\u0561\u0576\u0576\u0565\u0580\u0578\u0582\u0574 \u0535\u0580\u0565\u0582\u0561\u0576\u0575\u0561\u0576 \u056d\u0573\u0578\u0582\u0572\u0578\u0582 \u0574\u0565\u0580\u0571\u0561\u056f\u0561\u0575\u0584\u0578\u0582\u0574...\",\"image_path\":\"http%3A%2F%2Fnews.armeniatv.com%2Fwp-content%2Fuploads%2F2011%2F06%2Faxuryan_bnakaranashinyutyun-125x95.jpg\"},{\"id\":11648,\"header\":\"\u0540\u0565\u057f\u0561\u056d\u0578\u0582\u0566\u057e\u0578\u0572\u0576\u0565\u0580\u0568 \u0570\u0561\u0575\u057f\u0576\u0561\u0562\u0565\u0580\u057e\u0565\u0581\u056b\u0576\",\"text\":\"\u0540\u0578\u0582\u0576\u056b\u057d\u056b 3-\u056b\u0576, \u056a\u0561\u0574\u0568 02.10-\u056b\u0576, \u0544\u0578\u057d\u056f\u057e\u0561-\u0535\u0580\u0587\u0561\u0576 \u0579\u057e\u0565\u0580\u0569\u056b \u056b\u0576\u0584\u0576\u0561\u0569\u056b\u057c\u056b \u056a\u0561\u0574\u0561\u0576\u0578\u0582\u0574\u056b\u0581 \u0570\u0565\u057f\u0578 \u0578\u057d\u057f\u056b\u056f\u0561\u0576\u0578\u0582\u0569\u0575\u0561\u0576...\",\"image_path\":\"http%3A%2F%2Fnews.armeniatv.com%2Fwp-content%2Fuploads%2F2011%2F06%2Fg_image-125x95.jpg\"},{\"id\":11641,\"header\":\"\u0531\u057e\u057f\u0578\u057f\u0576\u0561\u056f\u056b\u0581 \u0570\u0561\u0575\u057f\u0576\u0561\u0562\u0565\u0580\u0565\u056c \u0565\u0576 \u0569\u0574\u0580\u0561\u0574\u056b\u057b\u0578\u0581\u0578\u057e \u0576\u0565\u0580\u0561\u0580\u056f\u056b\u0579\u0576\u0565\u0580\",\"text\":\"\u0540\u0578\u0582\u0576\u056b\u057d\u056b 2-\u056b\u0576, \u056a\u0561\u0574\u0568 17.00-\u056b\u0576, \u0578\u057d\u057f\u056b\u056f\u0561\u0576\u0578\u0582\u0569\u0575\u0561\u0576 \u0547\u0565\u0576\u0563\u0561\u057e\u056b\u0569\u056b \u0562\u0561\u056a\u0576\u056b\u00a0 \u056e\u0561\u057c\u0561\u0575\u0578\u0572\u0576\u0565\u0580\u0568, \u0561\u057a\u0585\u0580\u056b\u0576\u056b \u0569\u0574\u0580\u0561\u0574\u056b\u057b\u0578\u0581...\",\"image_path\":\"http%3A%2F%2Fnews.armeniatv.com%2Fwp-content%2Fuploads%2F2011%2F06%2Fsyringe-125x95.jpg\"},{\"id\":11634,\"header\":\"\u0544\u056c\u0561\u0564\u056b\u0579\u056b \u0563\u0578\u0580\u056e\u0578\u057e \u0564\u0561\u057f\u0561\u056f\u0561\u0576 \u0576\u056b\u057d\u057f\u0568 \u0570\u0565\u057f\u0561\u0571\u0563\u057e\u0565\u0581\",\"text\":\"\u0540\u0561\u0561\u0563\u0561\u0575\u0578\u0582\u0574 \u0570\u0578\u0582\u0576\u056b\u057d\u056b 3-\u056b\u0576 \u057d\u056f\u057d\u057e\u0565\u056c \u0567 \u0532\u0578\u057d\u0576\u056b\u0561\u0581\u056b \u057d\u0565\u0580\u0562\u0565\u0580\u056b \u0562\u0561\u0576\u0561\u056f\u0561\u0575\u056b\u0576 \u0563\u0565\u0576\u0565\u0580\u0561\u056c\u00a0\u054c\u0561\u057f\u056f\u0578 \u0544\u056c\u0561\u0564\u056b\u0579\u056b \u0561\u057c\u0561\u057b\u056b\u0576...\",\"image_path\":\"http%3A%2F%2Fnews.armeniatv.com%2Fwp-content%2Fuploads%2F2011%2F06%2F383698991-125x95.jpg\"},{\"id\":11601,\"header\":\"\u054f\u056b\u0563\u0580\u0561\u0576 \u0553\u0578\u057d\u057f\u0561\u0576\u057b\u0575\u0561\u0576\u056b\u0576 \u0561\u0566\u0561\u057f \u0565\u0576 \u0561\u0580\u0571\u0561\u056f\u0565\u056c\",\"text\":\"\u0531\u0575\u057d\u0585\u0580\u00a0\u0570\u0561\u0574\u0561\u0576\u0565\u0580\u0574\u0561\u0574\u0562 \u00a0\u0561\u0566\u0561\u057f \u0567 \u0561\u0580\u0571\u0561\u056f\u057e\u0565\u056c \u0531\u053a \u057a\u0561\u057f\u0563\u0561\u0574\u0561\u057e\u0578\u0580 \u0536\u0561\u0580\u0578\u0582\u0570\u056b \u0553\u0578\u057d\u057f\u0561\u0576\u057b\u0575\u0561\u0576\u056b \u0565\u0572\u0562\u0561\u0575\u0580\u0568` \u054f\u056b\u0563\u0580\u0561\u0576...\",\"image_path\":\"http%3A%2F%2Fnews.armeniatv.com%2Fwp-content%2Fuploads%2F2011%2F06%2Farm130709374560-125x95.jpg\"}]";
		final JSONArray newsJsonArray = new JSONArray(jsonString);
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				int count = newsJsonArray.length();
				for(int i = 0; i<count; i++){
					try {
						NewsListBean bean = new NewsListBean(newsJsonArray.getJSONObject(i).getString("image_path"),newsJsonArray.getJSONObject(i).getString("header"),newsJsonArray.getJSONObject(i).getString("text"),newsJsonArray.getJSONObject(i).getString("id"),newsJsonArray.getJSONObject(i).getString("date"));
						adapter.add(bean);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
				flipper.setDisplayedChild(1);
			}
		});
		
	}
}
