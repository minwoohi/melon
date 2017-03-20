package com.melon.user.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.user.service.UserService;
import com.melon.user.service.UserServiceImpl;

public class DoCheckDuplicateUserIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService;   
	
	// 입력할때마다 호출되어 userId가 USR 테이블의 USR_ID 에 중복되는 값이 있는지 확인
	
    public DoCheckDuplicateUserIdServlet() {  
    	userService = new UserServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		boolean isDuplicated = userService.isDuplicatedUserId(userId);
		
		StringBuffer json = new StringBuffer();
		json.append(" { ");
		json.append(" \"status\" : \"success\", "); // 요청한 것 잘 처리했고,
		json.append(" \"duplicated\" : " + isDuplicated); // 그 결과는 isDuplicated이다.
		json.append(" } ");
		
		// json 내용을 브라우저에 전송
		// 지금 내용은 json 형태의 String을 브라우저에 전송하는 것뿐. <script 태그 통해 추가해줘야 받은 String을
		// json 형태로 변환
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.flush();
		writer.close();
	}
}
