package com.infocentercache.manager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Notice {

	private Date date;
	private String subject;
	private String author;
	private int noticeId;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getFormatedDate() {

		try{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		  return dateFormat.format(date);
		}catch(Exception ex){}
		return null;
	}

}
