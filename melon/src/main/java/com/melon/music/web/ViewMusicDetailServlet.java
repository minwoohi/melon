package com.melon.music.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.melon.music.service.MusicService;
import com.melon.music.service.MusicServiceImpl;
import com.melon.music.vo.MusicVO;
import com.melon.user.vo.UserVO;

public class ViewMusicDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MusicService musicService;
    
    public ViewMusicDetailServlet() {
    	musicService = new MusicServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String musicId = request.getParameter("musicId");
		UserVO user = (UserVO) request.getAttribute("_USER_");
		
		MusicVO music = musicService.getOneMusic(musicId, user);
		
		request.setAttribute("music", music);
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/music/detail.jsp");
		dis.forward(request, response);
		
	}

}
