package com.infocentercache.manager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;

public class PushNotifier implements Runnable {
	
	private DataSource dataSource;
	private String subtitle;
	private String body;
	private String uri;
	private String page;
	
	public PushNotifier(){}
	
	public PushNotifier(String uri,String subtitle,String body,String page)
	{
		this.subtitle=subtitle;
		this.body=body;
		this.uri=uri;
		this.page=page;
	}	
	
	public void pushToAllWindowsPhone(String subtitle,String body,String page)
	{
		WebApplicationContext context=ListnerClass.springContext;
		dataSource=(DataSource)context.getBean("dataSource");
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT uri FROM pushuri";
		try{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				new Thread(new PushNotifier(rs.getString("uri"),subtitle,body,page)).start();
			}
		}
		catch(Exception ex){}
		finally
		{
			try{
				conn.close();
				ps.close();
				rs.close();
			}catch(Exception ex){}
		}
	}
	
	private void pushNoticeNotification()
	{
		String title;
		if(page.contains("Notice"))
			title="New Notice";
		else 
			title="New File";
		String url=this.uri;
 
        String message="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<wp:Notification xmlns:wp=\"WPNotification\">" +
                "<wp:Toast>" +
                     "<wp:Text1>" + title + "</wp:Text1>" +
                     "<wp:Text2>" + subtitle + "</wp:Text2>" +
                     "<wp:Param>/"+page+"?NavigatedFrom="+body+"</wp:Param>" +
                "</wp:Toast> " +
             "</wp:Notification>";
        URLConnection connection=null;
        OutputStream output =null;
        try {
        connection = new URL(url).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("ContentType", "text/xml");
        connection.setRequestProperty("ContentLength",message.getBytes().length+"");
        connection.setRequestProperty("X-WindowsPhone-Target", "toast");
        connection.setRequestProperty("X-NotificationClass", "2");
        output= connection.getOutputStream();
       
             output.write(message.getBytes(),0,message.getBytes().length);
        }
        catch(IOException ex){}
        finally {
             try { output.close(); } catch (IOException logOrIgnore) {}
        }
       /* System.out.println("Notification Status="+connection.getHeaderFields().get("X-NotificationStatus"));
        System.out.println("Subscription Status="+connection.getHeaderFields().get("X-SubscriptionStatus"));
        System.out.println("Device Connection Status="+connection.getHeaderFields().get("X-DeviceConnectionStatus"));*/
	}

	@Override
	public void run() {
		this.pushNoticeNotification();
	}

}
