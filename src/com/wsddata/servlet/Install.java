package com.wsddata.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String path=getServletContext().getRealPath("/");
		String installFileName=path+"install.html";
		String dbFileName=path+"/admin/init/db.sql";
		String jdbcFileName=path+"/WEB-INF/config/jdbc.properties";
		File installFile = new File(installFileName);   
	    //判断文件是否存在   
	    if (!installFile.exists()) {   
	        response.sendRedirect("../index.html");  
	    } else {
	        if (installFile.isFile()) {
	        	String driver = "com.mysql.jdbc.Driver";
	        	String dbhost=request.getParameter("dbhost");//表单获取数据库地址，通常是本机
	        	String dbname=request.getParameter("dbname");//表单获取要创建的数据库名称
	        	String dbauser=request.getParameter("dbauser");//表单获取dba用户，通常是root
	        	String dbapassword=request.getParameter("dbapassword");//表单获取dba密码	        	
	        	String url = "jdbc:mysql://"+dbhost+":3306/mysql";
	        	
	        	Connection conn = null;
	        	try {
	        	       Class.forName(driver);
	        	       conn = (Connection) DriverManager.getConnection(url, dbauser, dbapassword);
	        	       String sql="create database "+dbname+" character set 'utf8' collate 'utf8_general_ci'";
	        	       PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        	       pstmt.execute();
	        	       
	        	       String usernamme="wsddata";
	        	       String password="initialization";
	        	       String sql2="grant all privileges on "+dbname+".* to "+usernamme+"@localhost identified by '"+password+"'";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql2);
	        	       pstmt.execute();
	        	       
	        	       String sql3="flush privileges";
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql3);
	        	       pstmt.execute();
	        	       
	        	       String sql4="use "+dbname;
	        	       pstmt = (PreparedStatement)conn.prepareStatement(sql4);
	        	       pstmt.execute();
	        	       
	        	       //从初始化脚本文件db.sql中读取并执行
	        	       File dbFile = new File(dbFileName);
	        	       try(InputStreamReader ir=new InputStreamReader(new FileInputStream(dbFile),"UTF-8");
	        	    		BufferedReader reader=new BufferedReader(ir)) {
	        	    	   String tmp;
	        	    	   while ((tmp = reader.readLine()) != null) {
	        	    		   if(!tmp.startsWith("--")&&!tmp.equals("")){
	        	    			   sql4=tmp;
	        	    			   System.out.println(sql4);
	        	    			   pstmt = (PreparedStatement)conn.prepareStatement(sql4);
	        	    			   pstmt.execute();
	        	    		   }
	        	    	   }
	        	    	}catch (IOException e) {
	        	    		e.printStackTrace();
	        	    	}
	        	       
	        	       //写入jdbc.properties
	        	       FileWriter jdbcFile = new FileWriter(jdbcFileName);
	        	       StringBuffer porperties=new StringBuffer();
	        	       porperties.append("jdbc.driver="+driver+"\r\n");
	        	       porperties.append("jdbc.url="+url+"\r\n");
	        	       porperties.append("jdbc.username="+usernamme+"\r\n");
	        	       porperties.append("jdbc.password="+password);
	        	       try{
	        	    	   jdbcFile.write(porperties.toString());
	        	    	   jdbcFile.flush();
	        	    	   jdbcFile.close();
	        	       }catch(Exception e){
	        	    	   e.printStackTrace();
	        	       }
	        	       
	        	       installFile.delete();
	        	       out.println("<HTML>");
	        	       out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
	        	       out.println("  <BODY>");
	        	       out.print("    <p>安装成功，请重新启动系统</p> ");
	        	       out.println("  </BODY>");
	        	       out.println("</HTML>");
	        	       out.flush();
	        	       out.close();
	        	   } catch (ClassNotFoundException e) {
	        	       e.printStackTrace();
	        	   } catch (SQLException e) {
	        		   out.println("<HTML>");
	        	       out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
	        	       out.println("  <BODY>");
	        	       out.print("    <p>安装失败，请检查数据库信息是否正确</p> ");
	        	       out.println("  </BODY>");
	        	       out.println("</HTML>");
	        	       out.flush();
	        	       out.close();
	        	   }
	        }
	    }
	}
}
