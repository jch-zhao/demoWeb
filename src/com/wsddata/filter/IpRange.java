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
 * Servlet Filter implementation IP范围限制
 * 注解方式实现filter，其执行顺序不可控(有说按类名的字母顺序执行)。
 * 总之如果希望可靠的控制各个过滤器的执行顺序，最好在web.xml中定义。
 * 或者确保不同的过滤器应用到不同的路径上，以避免执行不符合期望。
 */

@WebFilter("/download/*")
public class IpRange implements Filter {

    /**
     * Default constructor. 
     */
    public IpRange() {
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
		//ip范围检查
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		User user=(User) req.getSession().getAttribute("user");
		if(user!=null){
			String clientIP=request.getRemoteAddr();
			String permitIP=user.getIpRange();
			if(judgeIP(permitIP,clientIP)){
				chain.doFilter(request, response);
			}else{
				resp.sendRedirect("../iperror.html");
			}
		}else{
			resp.sendRedirect("../iperror.html");
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private boolean judgeIP(String permitIP,String clientIP){
		if(permitIP.equals(clientIP)){
			return true;
		}else{
			return false;
		}
	}

}
