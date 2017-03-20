package com.melon.admin.user.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.admin.authorization.biz.AuthorizationBiz;
import com.melon.admin.authorization.biz.AuthorizationBizImpl;
import com.melon.admin.user.service.UserService;
import com.melon.admin.user.service.UserServiceImpl;
import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public class DoUserSelectModifyActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService;
    private AuthorizationBiz authBiz;
    
    public DoUserSelectModifyActionServlet() {
    	userService = new UserServiceImpl();
    	authBiz = new AuthorizationBizImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.sendError(404);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String beforeAuthId = request.getParameter("beforeAuth");
		String afterAuthId = request.getParameter("afterAuth");
		
			if(userService.renewAllAuth(beforeAuthId, afterAuthId)){
				System.out.println("변경 성공");
				response.sendRedirect("/melon-admin/user/list");
			} else {
				System.out.println("변경 실패");
				response.sendError(500);
			}
		/*System.out.println("beforeAuthId : " + beforeAuthId);
		System.out.println("afterAuthId : " + afterAuthId);*/
		
		/*UserSearchVO userSearch = new UserSearchVO();
		userSearch.getPager().setPageNumber(0);
		
		List<UserVO> userList = userService.getAllUsers(userSearch);
		
		if(userList.size() != 0){
			System.out.println("사이즈 0 아니다");
		}*/
		
		
		/*for(UserVO user : userList){
			System.out.println("beforeAuthId : " + beforeAuthId);
			System.out.println("user.getAuthorizationId : " + user.getAuthorizationId());
			if(user.getAuthorizationId() != null && // 권한 null 아니고
					user.getAuthorizationId().equals(beforeAuthId)){ // 이전 권한의 id와 같다면
				
				UserVO userTemp = new UserVO(); // 유저 새로 만들어
				
				userTemp.setUserId(user.getUserId()); // 값들 새 값으로 고친 후
				userTemp.setAuthorizationId(afterAuthId);  
				
				if(userService.updateUser(userTemp) == false){ // 업데이트
					System.out.println("userService.updateUser 실패했다");
					response.sendError(500);
				}
			} else if (beforeAuthId.equals("") && user.getAuthorizationId() == null ){
				UserVO userTemp = new UserVO(); // 유저 새로 만들어
				
				userTemp.setUserId(user.getUserId()); // 값들 새 값으로 고친 후
				userTemp.setAuthorizationId(afterAuthId);  
				
				if(userService.updateUser(userTemp) == false){ // 업데이트
					System.out.println("userService.updateUser 실패했다");
					response.sendError(500);
				}
			}
		}*/
		
		//response.sendRedirect("/melon-admin/user/list");
	}

}
