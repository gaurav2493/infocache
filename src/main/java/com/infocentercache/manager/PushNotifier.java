package com.infocentercache.manager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PushNotifier {
	
	public static void pushNoticeNotification(String subtitle,String body)
	{
		String title="New Notice";
		String url=System.getProperty("wpuri");
 
        String message="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<wp:Notification xmlns:wp=\"WPNotification\">" +
                "<wp:Toast>" +
                     "<wp:Text1>" + title + "</wp:Text1>" +
                     "<wp:Text2>" + subtitle + "</wp:Text2>" +
                     "<wp:Param>/Page2.xaml?NavigatedFrom="+body+"</wp:Param>" +
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
        System.out.println("Notification Status="+connection.getHeaderFields().get("X-NotificationStatus"));
        System.out.println("Subscription Status="+connection.getHeaderFields().get("X-SubscriptionStatus"));
        System.out.println("Device Connection Status="+connection.getHeaderFields().get("X-DeviceConnectionStatus"));
	}

}
