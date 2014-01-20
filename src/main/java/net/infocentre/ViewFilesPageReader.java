package net.infocentre;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ViewFilesPageReader extends PageReader {
	
protected static String filesLink = "http://210.212.85.155/file/files.php";
	
	public ViewFilesPageReader()
	{		
	}

	@Override
	public List<String[]> parseContents(String htmlTableContent) {
		 Document doc = Jsoup.parse(htmlTableContent);
		 List<String[]> fileList=new ArrayList<String[]>();
		  int i=0;
		  String[] fileArray=null;
			Element loginform = doc.getElementsByTag("table").get(4);
			Elements inputElements = loginform.getElementsByTag("td");
			for (Element inputElement : inputElements) {
				
				String data = inputElement.html();
				if(i==0)
				{
					fileArray = new String[5];
					fileList.add(fileArray);
				}
				if(++i==5)
				{
					String link= inputElement.getElementsByTag("a").first().attr("href");
					fileArray[i-1]=link.substring(16,link.length());
					i=0;
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
		return filesLink+"?start="+(pageID-1)*15;
	}

}
