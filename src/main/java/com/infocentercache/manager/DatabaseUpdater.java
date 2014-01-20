package com.infocentercache.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;

public class DatabaseUpdater {

	private static DataSource dataSource;

	protected static void addNotice(Notice notice) {

		dataSource = ListnerClass.dataSource;

		Connection conn=null;
		PreparedStatement ps=null ;
		try {
			conn = dataSource.getConnection();
			String sql = "REPLACE INTO"
					+ " notices( date,subject,posted_by,notice_id) "
					+ "VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setDate(1, notice.getDate());
			ps.setString(2, notice.getSubject());
			ps.setString(3, notice.getAuthor());
			ps.setInt(4, notice.getNoticeId());

			if (ps.executeUpdate() != 0) {
				conn = dataSource.getConnection();
				sql = "REPLACE INTO" + " noticeid_notice( notice, notice_id) "
						+ "VALUES(?,?)";
				ps = conn.prepareStatement(sql);

				ps.setString(1, notice.getContent());
				ps.setInt(2, notice.getNoticeId());

				ps.executeUpdate();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
			conn.close();
			ps.close();
			}catch(SQLException ex){}
			
		}

	}

	public static void addFile(InfoFile infoFile) {

		dataSource = ListnerClass.dataSource;

		Connection conn=null;
		PreparedStatement ps=null ;
		try {
			conn = dataSource.getConnection();
			String sql = "REPLACE INTO"
					+ " files(date, description, subject, posted_by, file_id) "
					+ "VALUES(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setDate(1, infoFile.getDate());
			ps.setString(2, infoFile.getDescription());
			ps.setString(3, infoFile.getSubject());
			ps.setString(4, infoFile.getAuthor());
			ps.setInt(5, infoFile.getFileId());

			if (ps.executeUpdate() != 0) {
				conn = dataSource.getConnection();
				sql = "REPLACE INTO" + " fileid_path(path,file_id) "
						+ "VALUES(?,?)";
				ps = conn.prepareStatement(sql);

				ps.setString(1, infoFile.getPath());
				ps.setInt(2, infoFile.getFileId());

				ps.executeUpdate();
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
			conn.close();
			ps.close();
			}catch(SQLException ex){}
			
		}

	}
	public boolean addNewUser(String username,String password)
	{
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=dataSource.getConnection();
			String sql="REPLACE INTO " +
					"user(username,password) " +
					"VALUES(?,?)";
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			if(ps.executeUpdate()==1)
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
			con.close();
			ps.close();
			}catch(SQLException ex){}
			
		}

		return false;
	}

}