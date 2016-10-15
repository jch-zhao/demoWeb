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
			if(judgeIP(clientIP,permitIP)){
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
	
	private boolean judgeIP(String clientIP,String permitIP){
		boolean permit=false;
		if(permitIP==null||clientIP==null||permitIP==""||clientIP=="")
			return permit;
		
		String[] ips=permitIP.trim().split(";");
		for(String ip:ips){
			if(ip.equals("*")){
				permit=true;
				break;
			}
			if(ip.contains("-")){
				String start=ip.substring(0,ip.indexOf("-"));
				String end=ip.substring(ip.indexOf("-")+1,ip.length());
				permit=permit||judgeIPRange(clientIP,start,end);
			}else{
				if(ip.contains("*")){
					String start=ip.replace("*","001");
					String end=ip.replace("*","255");
					permit=permit||judgeIPRange(clientIP,start,end);
				}else{
					if(ip.equals(clientIP)){
						permit=permit||true;
					}
				}
			}
		}
		return permit;
	}
	
	
	private boolean judgeIPRange(String clientIP,String startIP,String endIP){
		String[] clientIP_seg=clientIP.trim().split("\\.");
		String charClientIP="";
		for(int i=0;i<clientIP_seg.length;i++){
			int bits=clientIP_seg[i].length();
			if(bits==1){
				clientIP_seg[i]="00"+clientIP_seg[i];
			}else if(bits==2){
				clientIP_seg[i]="0"+clientIP_seg[i];
			}
			charClientIP=charClientIP+clientIP_seg[i];
		}
		long numClientIP=Long.parseLong(charClientIP);
		
		String[] start_seg=startIP.trim().split("\\.");
		String charStart="";
		for(int i=0;i<start_seg.length;i++){
			int bits=start_seg[i].length();
			if(start_seg[i].equals("*")){
				start_seg[i]="001";
			}else if(bits==1){
				start_seg[i]="00"+start_seg[i];
			}else if(bits==2){
				start_seg[i]="0"+start_seg[i];
			}
			charStart=charStart+start_seg[i];
		}
		long numStart=Long.parseLong(charStart);
		
		String[] end_seg=endIP.trim().split("\\.");
		String charEnd="";
		for(int i=0;i<end_seg.length;i++){
			int bits=end_seg[i].length();
			if(end_seg[i].equals("*")){
				end_seg[i]="255";
			}else if(bits==1){
				end_seg[i]="00"+end_seg[i];
			}else if(bits==2){
				end_seg[i]="0"+end_seg[i];
			}
			charEnd=charEnd+end_seg[i];
		}
		long numEnd=Long.parseLong(charEnd);
		if(numClientIP>=numStart&&numClientIP<=numEnd){
			return true;
		}else{
			return false;
		}
	}
}
