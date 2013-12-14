package com.armenia.news.bean;

public class NewsListBean {

	private String imageURL = "";
	private String text = "";
	private String header = "";
	private String id = "";
	private String date = "";
	
	
	public NewsListBean(){
		
	}
	
	public NewsListBean(String imageURL, String header, String text, String id, String date){
		this.imageURL = imageURL;
		this.header = header;
		this.text = text;
		this.id = id;
		this.date = date;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
