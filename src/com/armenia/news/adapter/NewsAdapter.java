package com.armenia.news.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.armenia.news.activity.R;
import com.armenia.news.bean.NewsListBean;

public class NewsAdapter extends ArrayAdapter<NewsListBean> {
	
	private Context mContext = null;

	public NewsAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		mContext = context;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = null;
		final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		itemView = inflater.inflate(R.layout.news_list_item, null);
		Typeface font= Typeface.createFromAsset(getContext().getAssets(), "arlamu.ttf");
		((TextView)itemView.findViewById(R.id.news_header_text)).setText(getItem(position).getHeader());
		((TextView)itemView.findViewById(R.id.news_header_text)).setTypeface(font);
		((TextView)itemView.findViewById(R.id.news_text)).setTypeface(font);
		((TextView)itemView.findViewById(R.id.news_text)).setText(getItem(position).getText());
		((TextView)itemView.findViewById(R.id.news_date)).setText(getItem(position).getDate());
		((TextView)itemView.findViewById(R.id.news_date)).setTypeface(font);
		String imageInnerHTML = "<html><head></head><body style = \"padding:0px\" marginwidth=\"0\" marginheight=\"0\" leftmargin=\"0\" topmargin=\"0\">";
//		imageInnerHTML+="<div style=\"width:100px;height:100px;float:left;background-color: #dce0eb;background-repeat:no-repeat;background-position:center center;background-image:url('"
//					+ getItem(position).getImageURL()+"');');\" ></div>";
		imageInnerHTML+="<center><img src=\""+getItem(position).getImageURL()+"\" .com\"  width=\"100\" /></center></body></html>";
		((WebView)itemView.findViewById(R.id.news_image)).setFocusable(false);
		((WebView)itemView.findViewById(R.id.news_image)).setClickable(false);
		((WebView)itemView.findViewById(R.id.news_image)).loadData(imageInnerHTML, "text/html", "utf-8");
		/*Bitmap bmp = null;
		try {
			bmp = BitmapFactory.decodeStream(new java.net.URL(getItem(position).getImageURL()).openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((ImageView)itemView.findViewById(R.id.news_image)).setImageBitmap(bmp);
		*/
		return itemView;
	}

}
