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
	        	String dbname="courseware";//用户端获取
	        	String dbuser="courseware";//用户端获取
	        	String dbpassword="courseware";//用户端获取
	        	String url = "jdbc:mysql://"+dbhost+":3306/"+dbname;
	        	
	        	Connection conn = null;
	        	try {
	        	       Class.forName(driver); //classLoader,加载对应驱动
	        	       conn = (Connection) DriverManager.getConnection(url, dbuser, dbpassword);
	        	       String sql="insert into photo(title,filepath)values('demo8','images/76977.jpg');";
	        	       PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
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
