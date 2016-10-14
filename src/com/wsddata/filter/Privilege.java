package com.wsddata.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsddata.bean.User;


/**
 * Servlet Filter implementation 权限控制
 * 注解方式实现filter，其执行顺序不可控(有说按类名的字母顺序执行)。
 * 总之如果希望可靠的控制各个过滤器的执行顺序，最好在web.xml中定义。
 * 或者确保不同的过滤器应用到不同的路径上，以避免执行不符合期望。
 */
@WebFilter("/admin/*")
public class Privilege implements Filter {

    /**
     * Default constructor. 
     */
    public Privilege() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		User u=(User) req.getSession().getAttribute("user");
		if (u.getIsAdmin()==1){
			chain.doFilter(request, response);
		}else{
			resp.sendRedirect("../privilegeerror.html");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
