package com.wsddata.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.context.WebApplicationContext;

import com.wsddata.dao.DataMapper;

@WebServlet("/search")
public class Search extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
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
		WebApplicationContext wac = (WebApplicationContext) request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		DataMapper dm=(DataMapper) wac.getBean("dataMapper");
		List<Map> ress=new ArrayList<Map>();
		String tableName=request.getParameter("table");
		ress=dm.selectAll(tableName,"submittime");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONArray ja=JSONArray.fromObject(ress);
		out.append(ja.toString());
		out.println();
		out.flush();
		out.close();
	}

}
