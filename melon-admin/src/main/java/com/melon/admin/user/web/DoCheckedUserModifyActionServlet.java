package com.melon.admin.user.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.admin.authorization.biz.AuthorizationBiz;
import com.melon.admin.authorization.biz.AuthorizationBizImpl;
import com.melon.admin.user.service.UserService;
import com.melon.admin.user.service.UserServiceImpl;

public class DoCheckedUserModifyActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
    private AuthorizationBiz authBiz;
	
    public DoCheckedUserModifyActionServlet() {
    	userService = new UserServiceImpl();
    	authBiz = new AuthorizationBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] userList = request.getParameterValues("checkedUserId");
		String afterAuthId = request.getParameter("afterAuth");
		
		System.out.println("afterAuthId : " + afterAuthId);
		
		if(userList == null || userService.renewCheckedAuth(userList, afterAuthId) ){
			System.out.println("변경 완료");
			response.sendRedirect("/melon-admin/user/list");
		}else {
			System.out.println("변경 실패");
			response.sendError(500);
		}
		
		
	}
	
}
