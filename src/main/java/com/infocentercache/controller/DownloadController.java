package com.infocentercache.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DownloadController {
	
	@Autowired
	DataSource dataSource;
	
	private static final int BUFFER_SIZE = 4096;
	
	@RequestMapping(value="/downloadinfofile/{fileid}",method= RequestMethod.GET)
	public void downloadFile(@PathVariable("fileid") int fileId,ModelMap model,HttpServletResponse response){
		
		String fileName="download";
		try{
        String fullPath = System.getProperty("filepath")+fileId;      
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
        Connection conn=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			conn=dataSource.getConnection();
			String sql="SELECT path FROM fileid_path WHERE file_id = ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fileId);
			rs=ps.executeQuery();
			rs.next();
			fileName=rs.getString(1);
		}catch(Exception ex){System.out.println(ex);}
		finally{
			try {
				conn.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        /*ServletContext context = request.getServletContext();
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        */
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                fileName);
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
		}catch (Exception ex){System.out.println(ex);}
	}

}
