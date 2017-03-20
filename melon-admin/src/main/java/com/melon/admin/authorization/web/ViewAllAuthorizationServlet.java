package com.melon.admin.authorization.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.admin.authorization.service.AuthorizationService;
import com.melon.admin.authorization.service.AuthorizationServiceImpl;
import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;

public class ViewAllAuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthorizationService authorizationService;
	
	public ViewAllAuthorizationServlet() {
		authorizationService = new AuthorizationServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNo = request.getParameter("pageNo");
		if( pageNo == null || pageNo.length() == 0){ // pageNo가 없거나 길이 0이라면
			pageNo = "0"; // 0으로 초기화한다.
		}
		AuthorizationSearchVO authoSearch = new AuthorizationSearchVO();
		authoSearch.getPager().setPageNumber(pageNo);
		
		List<AuthorizationVO> authoList = 
				authorizationService.getAllAuthorization(authoSearch);
		
		StringBuffer json = new StringBuffer();
		
		json.append(" { ");
		json.append(" \"status\" : \"success\", ");
		json.append(" \"size\": " + authoList.size() + ", ");
		json.append(" \"pageNo\": " + pageNo + ", ");
		json.append(" \"authorizations\" : [ ");
		
		String authorizationData = "";
		for(AuthorizationVO authorization : authoList){
			authorizationData += 
					String.format("{ \"authorizationId\": \"%s\",\"authorizationName\": \"%s\", \"parentAuthorizationId\": \"%s\" },"
					, authorization.getAuthorizationId()
					, authorization.getAuthorizationName()
					, authorization.getParentAuthorizationId());
			
		}
		
		if(authorizationData.length() > 0) {
			authorizationData = 
					authorizationData.substring(0, authorizationData.length() - 1);
		}
		json.append(authorizationData);
		json.append(" ] ");
		json.append(" } ");
		
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.flush();
		writer.close();
		
	}

}
