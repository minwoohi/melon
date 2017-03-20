package com.melon.music.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.melon.music.service.MusicService;
import com.melon.music.service.MusicServiceImpl;
import com.melon.music.vo.MusicVO;
import com.melon.user.service.UserService;
import com.melon.user.service.UserServiceImpl;
import com.melon.user.vo.UserVO;

public class DoModifyLikeCountActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MusicService musicService;
	private UserService userService;

	public DoModifyLikeCountActionServlet() {
		musicService = new MusicServiceImpl();
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String likeCountStr = request.getParameter("likeCnt");
		String musicId = request.getParameter("musicId");

		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("_USER_");

		int likeCount = 0;
		MusicVO music = null;
		
		try {
			likeCount = Integer.parseInt(likeCountStr);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		boolean isSuccess = musicService.updateMusicCount(musicId, likeCount);
		music = musicService.getOneMusic(musicId, user);
		System.out.println("music.likeCount : " + music.getLikeCount());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", isSuccess ? "success" : "fail");
		map.put("music", music);

		Gson gson = new Gson();
		String json = gson.toJson(map);

		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	}
}
