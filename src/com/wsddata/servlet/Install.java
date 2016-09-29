package com.wsddata.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/Install")
public class Install extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path=getServletContext().getRealPath("/");
		String installFileName=path+"install.html";
		File file = new File(installFileName);   
	    // 判断文件是否存在   
	    if (!file.exists()) {   
	        response.sendRedirect("../index.html");  
	    } else {
	        if (file.isFile()) {
	        	//执行初始化sql
	        	String driver = "com.mysql.jdbc.Driver";
	        	String dbhost="localhost"; //用户端获取
	        	//String dbname="root";//用户端获取
	        	String dbuser="root";//用户端获取
	        	String dbpassword="root";//用户端获取
	        	//String url = "jdbc:mysql://"+dbhost+":3306/"+dbname;
	        	String url = "jdbc:mysql://"+dbhost+":3306/mysql";
	        	
	        	Connection conn = null;
	        	try {
	        	       Class.forName(driver); //classLoader,加载对应驱动
	        	       conn = (Connection) DriverManager.getConnection(url, dbuser, dbpassword);
	        	       String sql="create database keyi character set 'utf8' collate 'utf8_general_ci'";
	        	       PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        	       pstmt.execute();
	        	       
	        	       //String sql2= "insert into mysql.User(Host,User,Password) values('localhost','userabc',password('abc'))";
	        	       String sql2="GRANT USAGE ON *.* TO 'userabc'@'localhost' IDENTIFIED BY 'abc' WITH GRANT OPTION";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql2);
	        	       pstmt.execute();
	        	       
	        	       String sql3="grant all privileges on keyi.* to userabc@localhost identified by 'abc'";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql3);
	        	       pstmt.execute();
	        	       
	        	       String sql4="flush privileges";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql4);
	        	       pstmt.execute();
	        	       
	        	       
	        	       String sql5="use keyi";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql5);
	        	       pstmt.execute();
	        	       
	        	       String sql6="CREATE TABLE USER (uid int NOT NULL AUTO_INCREMENT,username varchar(50) NOT NULL,password varchar(50),isAdmin int DEFAULT 0,PRIMARY KEY (uid))ENGINE=InnoDB DEFAULT CHARSET=utf8";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql6);
	        	       pstmt.execute();
	        	       
	        	       //ResultSet rs = pstmt.executeQuery();
	        	   } catch (ClassNotFoundException e) {
	        	       e.printStackTrace();
	        	   } catch (SQLException e) {
	        	       e.printStackTrace();
	        	   }
	        	
	            //file.delete();
	            //response.sendRedirect("../index.html");
	        }
	    }
	}

}
