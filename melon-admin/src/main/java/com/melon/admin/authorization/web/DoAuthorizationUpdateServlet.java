package com.melon.admin.authorization.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.melon.admin.authorization.service.AuthorizationService;
import com.melon.admin.authorization.service.AuthorizationServiceImpl;
import com.melon.admin.authorization.vo.AuthorizationVO;

public class DoAuthorizationUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthorizationService authorizationService;
	
    public DoAuthorizationUpdateServlet() {
    	authorizationService = new AuthorizationServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(404);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authId = request.getParameter("authorizationId");
		String authName = request.getParameter("authorizationName");
		String parentAuthId = request.getParameter("parentAuthorizationId");
		
		AuthorizationVO auth = new AuthorizationVO();
		
		auth.setAuthorizationId(authId);
		auth.setAuthorizationName(authName);
		auth.setParentAuthorizationId(parentAuthId);
		
		boolean isSuccess = authorizationService.renewAuthorizationInfo(auth);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", isSuccess ? "success" : "fail");
		map.put("authorizationId", authId);
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	}

}
