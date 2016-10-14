package com.wsddata.service.inner;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 原考虑做为同一主机的内部服务调用，后来觉得可能没什么必要，随便放着
 */

@WebServlet("/inner/NewService")
public class NewService extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String clientIP=request.getRemoteAddr();
		String serverIP=request.getLocalAddr();
		
		if (!clientIP.equals(serverIP)){
			HttpServletResponse res = (HttpServletResponse)response;
			res.setStatus(404);
		}else{
			// TODO Auto-generated method stub
		}
	}

}
