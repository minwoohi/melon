package com.melon.artist.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.melon.artist.biz.ArtistBiz;
import com.melon.artist.biz.ArtistBizImpl;
import com.melon.artist.vo.ArtistVO;
import com.melon.common.constants.AuthConst;
import com.melon.user.vo.UserVO;

public class ViewArtistWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtistBiz artistBiz;
	
	
    public ViewArtistWriteServlet() {
    	artistBiz = new ArtistBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("_USER_");
		
		if(user.getAuthorizationId().equals(AuthConst.OPERATOR_USER)
				|| user.getAuthorizationId().equals(AuthConst.ADMIN_USER)){
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/artist/write.jsp");
		dis.forward(request, response);
		} else if(user.getAuthorizationId().equals(AuthConst.NORMAL_USER)){
			response.sendError(404);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String artistName = request.getParameter("artistName");
		String debutDate = request.getParameter("debutDate");
		String debutTitle = request.getParameter("debutTitle");
		
		ArtistVO artist = new ArtistVO();
		artist.setMember(artistName);
		artist.setDebutDate(debutDate);
		artist.setDebutTitle(debutTitle);
		
		if(artistBiz.addNewArtist(artist)){
			PrintWriter out = response.getWriter();
			out.write("OK");
			out.flush();
			out.close();
		}else{
			PrintWriter out = response.getWriter();
			out.write("FAIL");
			out.flush();
			out.close();
		}
	}

}
