package net.infocentre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public abstract class PageReader {
	
	  protected List<String> cookies;
	  protected HttpURLConnection conn;
	  
	private final String USER_AGENT = "Mozilla/5.0";
	
	protected abstract List<String[]> parseContents(String htmlTableContent) throws IOException;
	protected abstract String getLink(int pageID);
	
	  public String GetPageContent(String url) throws IOException {
		  
			URL obj = null;
			try {
				obj = new URL(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				conn = (HttpURLConnection) obj.openConnection();
			
			// default is GET
			try {
				conn.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			conn.setUseCaches(false);
		 
			// act like a browser
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			if (cookies != null) {
				for (String cookie : this.cookies) {
					conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
				}
			}
			/*int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);*/
			BufferedReader in = null;
			
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
		 
			try {
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			// Get the response cookies
			setCookies(conn.getHeaderFields().get("Set-Cookie"));
		 
			return response.toString();
		 
		  }
		 
		  public List<String> getCookies() {
			return cookies;
		  }
		 
		  public void setCookies(List<String> cookies) {
			this.cookies = cookies;
		  }

}
