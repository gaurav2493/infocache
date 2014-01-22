package com.infocentercache.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class XMLConverter {

	private DataSource dataSource;

	public XMLConverter()
	{
		dataSource=(DataSource)ListnerClass.springContext.getBean("dataSource");
	}
	public List<Notice> getNoticesXML(int page)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement ps=null;

		try {
			conn=dataSource.getConnection();
			String sql="SELECT date,subject,posted_by,notice_id " +
					"FROM notices " +
					"ORDER BY notice_id DESC LIMIT ?,15";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, (page-1)*15);
			rs=ps.executeQuery();
			List<Notice> noticesList=new ArrayList<Notice>();
			Notice notice;
			
			while(rs.next())
			{
				notice=new Notice();
				notice.setDate(rs.getDate("date"));
				notice.setSubject(rs.getString("subject"));
				notice.setAuthor(rs.getString("posted_by"));
				notice.setNoticeId(rs.getInt("notice_id"));
				noticesList.add(notice);
			}
			return noticesList;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
				//dataSource.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
	public Notice getNoticeXML(int id)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			conn=dataSource.getConnection();
			String sql="SELECT p.notice,q.date,q.subject,q.posted_by " +
					"FROM noticeid_notice p,notices q " +
					"WHERE p.notice_id=? and q.notice_id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.setInt(2,id);
			rs=ps.executeQuery();
			Notice notice=new Notice();			
			rs.next();
			notice.setAuthor(rs.getString("posted_by").replaceAll("&","AND"));
			notice.setContent(rs.getString("notice").replaceAll("&","AND").replaceAll("(?i)<br([^>]+)>", "&#10;"));
			notice.setDate(rs.getDate("date"));
			notice.setSubject(rs.getString("subject").replaceAll("&","AND").replaceAll("(?i)<br([^>]+)>", "&#10;"));
			notice.setNoticeId(id);			
			return notice;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
				//dataSource.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
	public List<InfoFile> getFilesXML(int page)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			conn=dataSource.getConnection();
			String sql="SELECT date,description,subject,posted_by,file_id " +
					"FROM files " +
					"ORDER BY file_id DESC LIMIT ?,10";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, (page-1)*10);
			rs=ps.executeQuery();
			List<InfoFile> filesList=new ArrayList<InfoFile>();
			InfoFile file=null;
			
			while(rs.next())
			{
				file=new InfoFile();
				file.setDate(rs.getDate("date"));
				file.setDescription(rs.getString("description").replaceAll("&","AND"));
				file.setSubject(rs.getString("subject").replaceAll("&","AND"));
				file.setAuthor(rs.getString("posted_by").replaceAll("&","AND"));
				file.setFileId(rs.getInt("file_id"));
				filesList.add(file);
			}
			return filesList;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
				//dataSource.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}

}
