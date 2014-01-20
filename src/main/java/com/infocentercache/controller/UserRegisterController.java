package com.infocentercache.controller;

import javax.servlet.http.HttpServletResponse;

import net.infocentre.InfoAuthenticator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserRegisterController {
	
	@RequestMapping(value="/newuser",method= RequestMethod.POST)
	public @ResponseBody String byParameter(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletResponse response) {
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    try {
			InfoAuthenticator.validate(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Username= "+username+"\nPassword= "+password;
	}

}
