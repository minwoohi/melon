package com.melon.admin.user.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.admin.authorization.service.AuthorizationService;
import com.melon.admin.authorization.service.AuthorizationServiceImpl;
import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;
import com.melon.admin.common.web.pager.ClassicPageExplorer;
import com.melon.admin.common.web.pager.PageExplorer;
import com.melon.admin.user.service.UserService;
import com.melon.admin.user.service.UserServiceImpl;
import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public class ViewUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService;
    private AuthorizationService authService;
	
	public ViewUserListServlet() {
		userService = new UserServiceImpl();
		authService = new AuthorizationServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageNo = request.getParameter("pageNo");
		
		UserSearchVO userSearch = new UserSearchVO();
		userSearch.getPager().setPageNumber(pageNo);
		
		AuthorizationSearchVO authSearch = new AuthorizationSearchVO();
		authSearch.getPager().setPageNumber(0);
		
		List<UserVO> userList = userService.getAllUsers(userSearch);
		
		List<AuthorizationVO> authList = authService.getAllAuthorization(authSearch);
		
		int userCnt = userSearch.getPager().getTotalArticleCount();
		
		PageExplorer pageEx = new ClassicPageExplorer(userSearch.getPager());
		
		String pager = pageEx.getPagingList(pageNo, "< @ >", "Prev", "Next", "searchForm");
		
		request.setAttribute("userList", userList);
		request.setAttribute("count", userCnt);
		request.setAttribute("pager", pager);
		
		request.setAttribute("authList", authList);
		
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/user/list.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
