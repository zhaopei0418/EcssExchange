package com.cneport.ecss.user;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;
	String servletPath = request.getServletPath();
	HttpSession session = request.getSession();

	if (servletPath != null && "/services".equals(servletPath)) {
	    chain.doFilter(req, resp);
	} else if (servletPath.endsWith("index.jsp")
		|| servletPath.endsWith("quit.jsp")
		|| servletPath.endsWith("exe") || servletPath.endsWith("css")
		|| servletPath.endsWith("js") || servletPath.endsWith("jpg")
		|| servletPath.endsWith("ico") || servletPath.endsWith("gif")
		|| servletPath.endsWith("png") || servletPath.endsWith("doc")
		|| servletPath.endsWith("xls") || servletPath.endsWith("xlsx")
		|| servletPath.endsWith("login")
		|| servletPath.endsWith("menus")
		|| servletPath.contains("loginByIcNo")) {

	    chain.doFilter(req, resp);
	} else {
	    if (session.getAttribute("user") == null) {
		response.sendRedirect("index.jsp");
	    } else {
		chain.doFilter(req, resp);
	    }

	}
    }

    public void init(FilterConfig arg0) throws ServletException {
	// TODO Auto-generated method stub

    }

}
