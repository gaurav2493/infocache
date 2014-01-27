package com.infocentercache.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PushUriController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/pushurisubmit", method=RequestMethod.GET)
	public @ResponseBody String byParameter(@RequestParam("uri") String uri,@RequestParam("id") String id, HttpServletResponse response) {
		
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="REPLACE INTO pushuri(id,uri) VALUES(?,?)";
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, uri);
			ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			ps.executeUpdate();
		}catch(Exception ex){}
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
	public @ResponseBody String byParameter(HttpServletResponse response) {
		response.setContentType("text/plain");
		
	    response.setCharacterEncoding("UTF-8");
		return "Not implemented yet";
	}
}
