package com.wsddata.servlet;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

@WebServlet("/servlet/savehtml")
public class SaveRichText extends HttpServlet {

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
		String content=request.getParameter("content");
		//抽取图片数据
		String src="(<img src=\"data:image/png;base64,)([a-z0-9A-Z+/=]*)(\"+?)";
		Pattern p=Pattern.compile(src);
		Matcher m = p.matcher(content);
		Random random = new Random(); 
		
		String mainpath=getServletContext().getRealPath("/");
		System.out.println(mainpath);
		
		while (m.find()){
			int start=m.group().indexOf("base64,");
			int end=m.group().indexOf("\"",start);
			String base64=m.group().substring(start+7,end);  //取得图片数据
			
			String s1=String.valueOf(random.nextInt(1000000));
			String s2=String.valueOf(System.currentTimeMillis());
			String filename=s1+s2+".jpg";
			String path="upload/images/"+filename;
			savefile(base64,mainpath+path);  //保存文件
			content=content.replace("data:image/png;base64,"+base64,"images/"+filename);  //替换数据为文件路径
		}
		Date now=new Date(System.currentTimeMillis());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		String htmlName=sdf.format(now)+".html";
		String absHtmlName=mainpath+"upload/"+htmlName;
		BufferedOutputStream out;
		out=new BufferedOutputStream(new FileOutputStream(absHtmlName));
		out.write(content.getBytes("UTF-8"));
		out.flush();
		out.close();
		System.out.println(content);
		response.sendRedirect("../upload/"+htmlName);
	}

	
	private void savefile(String base64,String filename){
		BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
