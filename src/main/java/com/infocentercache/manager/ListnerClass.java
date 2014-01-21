package com.infocentercache.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.infocentre.InfoAuthenticator;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ListnerClass implements ServletContextListener {

	private DataSource dataSource;
	public static WebApplicationContext springContext;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn =null;

		try {

			springContext = WebApplicationContextUtils
					.getWebApplicationContext(event.getServletContext());
			dataSource = (DataSource) springContext.getBean("dataSource");

			conn= dataSource.getConnection();
			String sql = "SELECT MAX(n.notice_id),MAX(f.file_id) "
					+ "FROM noticeid_notice n,fileid_path f";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			System.setProperty("localNoticeId", rs.getInt(1) + "");
			System.setProperty("localFileId", rs.getInt(2) + "");

			System.out.println("\nlocalnotice = "
					+ System.getProperty("localNoticeId"));
			System.out.println("\nlocalfile = "
					+ System.getProperty("localFileId"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException ex) {
			System.out.println("\n\n***");
			ex.printStackTrace();
			System.out.println("\n\n*****");
		}
		finally{
			try{
			conn.close();
			//dataSource.close();
			ps.close();
			rs.close();
			}catch(Exception ex){}
			
		}
		System.setProperty("infouser","11it056");
		System.setProperty("infopass","9013669956");
		
		try {
			InfoAuthenticator.authenticate(System.getProperty("infouser"),System.getProperty("infopass"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.setProperty("filepath", System.getenv("OPENSHIFT_JBOSSAS_DIR")+"files/");
		new UpdateChecker().start();

	}

}
