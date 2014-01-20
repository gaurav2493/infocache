package com.infocentercache.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infocentercache.manager.InfoFile;
import com.infocentercache.manager.Notice;
import com.infocentercache.manager.XMLConverter;

@Controller
public class XMLTransferController {

	@RequestMapping(value="/getnotices",method= RequestMethod.GET)
	public String getNoticeXml(ModelMap model,@RequestParam("page") int page)
	{
		List<Notice> noticesList=XMLConverter.getNoticesXML(page);
		model.addAttribute("noticesList",noticesList);
		return "getnoticesxml";
	}
	@RequestMapping(value="/getnotice",method= RequestMethod.GET)
	public String getNoticeContentXml(ModelMap model,@RequestParam("id") int id)
	{
		Notice notice=XMLConverter.getNoticeXML(id);
		model.addAttribute("notice",notice);
		return "getnoticexml";
	}
	@RequestMapping(value="/getfiles",method= RequestMethod.GET)
	public String getFilesContentXml(ModelMap model,@RequestParam("page") int page)
	{
		List<InfoFile> filesList=XMLConverter.getFilesXML(page);
		model.addAttribute("filesList", filesList);
		return "getfilesxml";
	}
}
