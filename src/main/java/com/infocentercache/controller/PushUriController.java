package com.infocentercache.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infocentercache.manager.PushNotifierAndroid;


@Controller
public class PushUriController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/pushurisubmit", method=RequestMethod.GET)
	public @ResponseBody String byParameter(@RequestParam("uri") String uri,@RequestParam("id") String id, HttpServletResponse response) {
		
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="REPLACE INTO pushuri(id,uri,date) VALUES(?,?,?)";
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, uri);
			ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex);
		}
		finally{
			try{
				conn.close();
				ps.close();
			}catch(Exception ex){}
		}
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
		return "Acknowledged";
	}
	
	@RequestMapping(value="/geturi", method=RequestMethod.GET)
	public @ResponseBody String byParameter(@RequestParam("message") String message,HttpServletResponse response) {
		response.setContentType("text/plain");
		new PushNotifierAndroid(message, "hello");
	    response.setCharacterEncoding("UTF-8");
		return "sent";
	}
	@RequestMapping(value="/androiddeviceidsubmit", method=RequestMethod.POST)
	public @ResponseBody String androidRegister(@RequestParam("regId") String uri,@RequestParam("name") String id, HttpServletResponse response) {
		
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="REPLACE INTO androidpushid(id,deviceid,date) VALUES(?,?,?)";
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, uri);
			ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println(ex);
		}
		finally{
			try{
				conn.close();
				ps.close();
			}catch(Exception ex){}
		}
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
		return "Acknowledged";
	}
	@RequestMapping(value="/androidid", method=RequestMethod.POST)
	public @ResponseBody String printAndroidDeviceId(HttpServletResponse response) {
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String result="";
		String sql="SELECT * FROM androidpushid";
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				result+=rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+"<br/>";
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		finally{
			try{
				conn.close();
				ps.close();
				rs.close();
			}catch(Exception ex){}
		}
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
		return result;
	}
}
