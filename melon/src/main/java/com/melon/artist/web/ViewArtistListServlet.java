package com.melon.artist.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.melon.artist.biz.ArtistBiz;
import com.melon.artist.biz.ArtistBizImpl;
import com.melon.artist.vo.ArtistSearchVO;
import com.melon.artist.vo.ArtistVO;
import com.melon.common.constants.AuthConst;
import com.melon.common.web.pager.ClassicPageExplorer;
import com.melon.common.web.pager.PageExplorer;
import com.melon.user.vo.UserVO;

public class ViewArtistListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArtistBiz artistBiz;
	
    public ViewArtistListServlet() {
    	artistBiz = new ArtistBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String pageNo = request.getParameter("pageNo");
		
		ArtistSearchVO artistSearchVO = new ArtistSearchVO();
		
		artistSearchVO.getPager().setPageNumber(pageNo);
		
		List<ArtistVO> artistList = artistBiz.getAllArtists(artistSearchVO);
		
		PageExplorer pager = new ClassicPageExplorer(artistSearchVO.getPager());
		
		request.setAttribute("artistList", artistList);
		
		// 전체 artist 갯수 jsp에 전달
		request.setAttribute("artistCount", artistSearchVO.getPager().getTotalArticleCount());
		// pager 정보 jsp에 전달		
		request.setAttribute("pager", pager.getPagingList(pageNo, "[@]", "Prev", "Next", "searchForm"));

		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("_USER_");
		
		request.setAttribute("isNormalUser", user.getAuthorizationId().equals(AuthConst.NORMAL_USER));
		request.setAttribute("isOperatorUser", user.getAuthorizationId().equals(AuthConst.OPERATOR_USER));
		request.setAttribute("isAdminUser", user.getAuthorizationId().equals(AuthConst.ADMIN_USER));
		
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/artist/list.jsp");
		dis.forward(request, response);
		
	}

}