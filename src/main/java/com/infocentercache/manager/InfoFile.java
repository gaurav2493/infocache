package com.infocentercache.manager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class InfoFile {

	private Date date;
	private String subject;
	private String author;
	private String description;
	private int fileId;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFormatedDate() {

		try{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		  return dateFormat.format(date);
		}catch(Exception ex){}
		return null;
	}

}
