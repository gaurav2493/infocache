package net.infocentre;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class ViewNoticesPageReader extends PageReader {
 
	protected static String noticeLink = "http://210.212.85.155/notice/notices.php";
	
	public ViewNoticesPageReader()
	{		
	}
 
  @Override
public List<String[]> parseContents(String htmlTableContent) throws IOException
  {

	  Document doc = Jsoup.parse(htmlTableContent);
	  int i=0;
	  List<String[]> fileList=new ArrayList<String[]>();
	  String[] fileArray=null;
		Element loginform = doc.getElementsByTag("table").get(4);
		Elements inputElements = loginform.getElementsByTag("td");
		for (Element inputElement : inputElements) {
			
			String data = inputElement.html();
			if(i==0)
			{
				fileArray = new String[4];
				fileList.add(fileArray);
			}
			if(++i==4)
			{
				String link="";
				try{link= inputElement.getElementsByTag("a").first().attr("href");
				}
				catch(NullPointerException ex)
				{
					try {
						//InfoAuthenticator.authenticate(System.getProperty("infouser"),System.getProperty("infopass"));
						InfoAuthenticator.authenticate("11it056","9013669956");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				try{
				fileArray[i-1]=link.substring(19,link.length());
				i=0;
				}catch(Exception ex)
				{
					return null;
				}
			}
			else
			{
				fileArray[i-1]=data;
			}
			
		}
		return fileList;
  }

@Override
public String getLink(int pageID) {
	return noticeLink+"?start="+(pageID-1)*15;
} 


 
}
