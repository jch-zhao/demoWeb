package com.wsddata.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.SessionListener;
import org.apache.log4j.Logger;
import com.wsddata.dao.UserMapper;
import com.wsddata.bean.User;

public class Login extends HttpServlet {
	private UserMapper user;

	public UserMapper getUm() {
		return user;
	}

	public void setUm(UserMapper um) {
		this.user = um;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		User u=user.selectUserByNameAndPassword(username,password);
		if(u!=null){
			request.getSession().setAttribute("user",u);
			response.sendRedirect("index.html");
		}else{
			response.sendRedirect("login.html");
		}
	}
}
