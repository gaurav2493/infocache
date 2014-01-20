package net.infocentre;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.lang.StringEscapeUtils;

public class NoticeExtractor extends PageReader {
	

	@Override
	public List<String[]> parseContents(String htmlTableContent) {
		Document doc = Jsoup.parse(htmlTableContent);
		//  int i=0;
			Element loginform = doc.getElementsByTag("table").get(3);
			
			String notice=loginform.getElementsByTag("p").get(2).html();
			ArrayList<String[]> arr=new ArrayList<String[]>();
			String[] strArr={StringEscapeUtils.unescapeHtml(notice)};
			arr.add(strArr);
			return arr;
	}
	@Override
	public String getLink(int noticeId) {
		return "http://210.212.85.155/notice/view_notice.php?id="+noticeId;
	} 
	

}
