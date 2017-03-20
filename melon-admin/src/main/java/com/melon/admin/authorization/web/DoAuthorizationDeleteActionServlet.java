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

public class DoAuthorizationDeleteActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AuthorizationService authService;
	
    public DoAuthorizationDeleteActionServlet() {
        authService = new AuthorizationServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(404);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authId = request.getParameter("authorizationId");
		
		boolean isSuccess = authService.removeOneAuthorization(authId);

		Map<String, String> map = new HashMap<String, String>();
		map.put("status", isSuccess ? "success" : "failed" );
		
		Gson gson = new Gson();
		
		String json = gson.toJson(map);
		
		PrintWriter writer = response.getWriter();
		writer.write(json); // 버퍼에만, print는 flush()까지
		writer.flush();
		writer.close();
	}

}
