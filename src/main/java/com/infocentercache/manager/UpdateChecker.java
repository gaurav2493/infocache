package com.infocentercache.manager;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.infocentre.FileDownloader;
import net.infocentre.NoticeExtractor;
import net.infocentre.ViewFilesPageReader;
import net.infocentre.ViewNoticesPageReader;

public class UpdateChecker extends Thread {
	
	DatabaseUpdater databaseUpdater;
	
	@Override
	public void run() {
		
		databaseUpdater=new DatabaseUpdater();
		while (true) {
			try{
			updateNotices();
			updateFiles();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			try {
				sleep(1800000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	public synchronized int updateNotices() throws Exception {

		ViewNoticesPageReader http = new ViewNoticesPageReader();
		List<String[]> list;
		try {
			list = http.parseContents(http.GetPageContent(http
					.getLink(1)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		int count = 0;
		int maxNoticeId=Integer.parseInt(System.getProperty("localNoticeId"));
		for (int i = 0; i < list.size()
				&& Integer.parseInt(list.get(i)[3]) > Integer.parseInt((System
						.getProperty("localNoticeId"))); i++) {
			
			String[] myarr = list.get(i);
			Notice notice = new Notice();
			notice.setDate(convertDate(myarr[0]));
			notice.setSubject(myarr[1]);
			notice.setAuthor(myarr[2]);
			notice.setNoticeId(Integer.parseInt(myarr[3]));
			try {
				notice.setContent(getNotice(Integer.parseInt(myarr[3])));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			
			databaseUpdater.addNotice(notice);
			try{
				new PushNotifier(notice.getSubject().replaceAll("&amp;","AND"),notice.getAuthor().replaceAll("&amp;","AND"),"ViewNotices.xaml");
				//new PushNotifierAndroid(notice.getSubject().replaceAll("&amp;","AND"),notice.getAuthor().replaceAll("&amp;","AND"));
				}catch(Exception ex){}
			
			
			if(notice.getNoticeId()>maxNoticeId)
			{
				maxNoticeId=notice.getNoticeId();
			}
			count++;
		}
		System.setProperty("localNoticeId", maxNoticeId+"");
		return count;
	}

	public synchronized int updateFiles() throws NumberFormatException, IOException {
		ViewFilesPageReader pr = new ViewFilesPageReader();
		List<String[]> list = pr.parseContents(pr.GetPageContent(pr.getLink(1)));
		int count = 0;
		int maxFileId=Integer.parseInt(System.getProperty("localFileId"));
		for (int i = 0; i < list.size() && Integer.parseInt(list.get(i)[4])>Integer.parseInt(System.getProperty("localFileId")); i++) {
			String[] myarr = list.get(i);
			
			InfoFile infoFile = new InfoFile();
			infoFile.setDate(convertDate(myarr[0]));
			infoFile.setDescription(myarr[1]);
			infoFile.setSubject(myarr[2]);
			infoFile.setAuthor(myarr[3]);
			infoFile.setFileId(Integer.parseInt(myarr[4]));
			infoFile.setPath(DownloadFile(Integer.parseInt(myarr[4])));
			
			databaseUpdater.addFile(infoFile);
			try{
				new PushNotifier().pushToAllWindowsPhone(infoFile.getSubject().replaceAll("&amp;","AND"), infoFile.getAuthor().replaceAll("&amp;","AND"),"ViewFiles.xaml");
					}catch(Exception ex){}
			if(infoFile.getFileId()>maxFileId)
			{
				maxFileId=infoFile.getFileId();
			}
			
			count++;
		
		
	}
	System.setProperty("localFileId", maxFileId+"");
		return count;
	}

	private static String getNotice(int noticeId) throws IOException {
		NoticeExtractor http = new NoticeExtractor();
		String result = http.GetPageContent(http.getLink(noticeId));
		return http.parseContents(result).get(0)[0];

	}

	private static String DownloadFile(int fileId) throws IOException {
		FileDownloader fd = new FileDownloader(fileId);
		return fd.saveDownloadTo(System.getProperty("filepath"));
	}

	private static Date convertDate(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		java.util.Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return new Date(date.getTime());
	}

}
