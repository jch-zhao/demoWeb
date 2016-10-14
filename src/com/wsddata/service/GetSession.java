package com.wsddata.service;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wsddata.bean.User;
import net.sf.json.JSONArray;

@WebServlet("/GetSession")
public class GetSession extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		User u=(User) request.getSession().getAttribute("user");
		JSONArray ja=JSONArray.fromObject(u);
		out.append(ja.toString());
		out.flush();
		out.close();
	}

}
