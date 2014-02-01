package com.infocentercache.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class PushNotifierAndroid implements Runnable {
	
	private static String subject;
	private DataSource dataSource;
	private String deviceId;

	public PushNotifierAndroid(String subject,String author) {
		
		PushNotifierAndroid.subject=subject;
		pushToAllAndroidDevices();
	}

	public PushNotifierAndroid(String deviceId) {

		this.deviceId=deviceId;
	}

	private void pushToAllAndroidDevices() {
		
		WebApplicationContext context=ListnerClass.springContext;
		dataSource = (DataSource)context.getBean("dataSource");
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT deviceid FROM androidpushid";
		try{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				new Thread(new PushNotifierAndroid(rs.getString("deviceid"))).start();
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

	@Override
	public void run() {
		
		String deviceToken = deviceId;
		try {

			Sender sender = new Sender("AIzaSyBZDdrun7EihVzb8yq4mHJiG2FKHgbLces");
			Message message = new Message.Builder().addData(
					"price", subject).build();
			System.out.println("deviceToken " + deviceToken);
			Result result = sender.send(message, deviceToken, 1);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
