package com.melon.admin.common.web;

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

import com.melon.admin.user.vo.UserVO;

public class LoginCheckFilter implements Filter {

    public LoginCheckFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest http = (HttpServletRequest) request;
		HttpSession session = http.getSession();
		
		UserVO user = (UserVO) session.getAttribute("_USER_");
		
		if(user == null){
			HttpServletResponse httpRes = (HttpServletResponse) response;
			httpRes.sendRedirect("/melon/user/login");
			return;
		} else {
			request.setAttribute("_USER_", user); // request에 set 해놓으면
			// 이후 서블릿에서 request.getAttribute()로 접근 가능
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
