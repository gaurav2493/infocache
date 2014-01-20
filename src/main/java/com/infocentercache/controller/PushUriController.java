package com.infocentercache.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PushUriController {
	
	@RequestMapping(value="/pushurisubmit", method=RequestMethod.GET)
	public @ResponseBody String byParameter(@RequestParam("uri") String foo, HttpServletResponse response) {
		response.setContentType("text/html");
		System.setProperty("wpuri",foo);
		System.setProperty("wpdate",new Date().getTime()+"");
	    response.setCharacterEncoding("UTF-8");
		return "Acknowledged";
	}
	
	@RequestMapping(value="/geturi", method=RequestMethod.GET)
	public @ResponseBody String byParameter(HttpServletResponse response) {
		response.setContentType("text/plain");
		
	    response.setCharacterEncoding("UTF-8");
		return "uri = "+System.getProperty("wpuri")+"<br/>date = <script>document.write(new Date("+System.getProperty("wpdate")+"))</script>";
	}
}
