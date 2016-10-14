package com.wsddata.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringServlet extends HttpServlet {
	private String targetBean;
	private Servlet proxy;
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		super.init();
		this.targetBean=getServletName();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		
		this.proxy=(Servlet) wac.getBean(targetBean);
		proxy.init(getServletConfig());
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		proxy.service(request, response);
	}

}
