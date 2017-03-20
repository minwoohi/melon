package com.melon.music.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.common.web.MultipartHttpServletRequest;
import com.melon.common.web.MultipartHttpServletRequest.MultipartFile;
import com.melon.music.biz.MusicBiz;
import com.melon.music.biz.MusicBizImpl;
import com.melon.music.vo.MusicVO;

public class ViewMusicWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private MusicBiz musicBiz;
	
    public ViewMusicWriteServlet() {
    	musicBiz = new MusicBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/music/write.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * <input type="text" name="title" placeholder="제목"/><br/>
		<input type="text" name="musician" placeholder="작곡가" /><br/>
		<input type="text" name="director"  placeholder="작사가"/><br/>
		<input type="file" name="mp3File" accept=".mp3" placeholder="음악 파일"/><br/>
		<textarea name="lyrics" placeholder="가사"></textarea><br/>
		<input type="button" value="등록"/>
		 */
		
		MultipartHttpServletRequest multipart =
				new MultipartHttpServletRequest(request);
		
		String albumId = request.getParameter("albumId");
		String title = multipart.getParameter("title");
		String musician = multipart.getParameter("musician");
		String director = multipart.getParameter("director");
		String lyrics = multipart.getParameter("lyrics");
		
		MultipartFile mp3File = multipart.getFile("mp3File");
		
		if(mp3File != null && mp3File.getFileSize() > 0){
			String postPath ="C:\\Users\\Admin\\Documents\\melon\\melon\\src\\main\\webapp\\mp3\\";
			postPath += albumId;
			
			
			
			File dir = new File(postPath);
			dir.mkdirs();
			
			mp3File.write(postPath + File.separator + mp3File.getFileName());
		}
		
		MusicVO music = new MusicVO();
		music.setAlbumId(albumId);
		music.setDirector(director);
		music.setLikeCount(0);
		music.setLyrics(lyrics);
		music.setMp3File(mp3File.getFileName());
		music.setMusician(musician);
		music.setTitle(title);
		
		if(musicBiz.addNewMusic(music)){
			
			PrintWriter writer = response.getWriter();
			StringBuffer buf = new StringBuffer();
			buf.append("<script type='text/javascript'>");
			buf.append("    opener.location.reload();");
			buf.append("    self.close();");
			buf.append("</script>");
			
			writer.write(buf.toString());
			writer.flush();
			writer.close();
		}else {
			response.sendRedirect("/melon/music/write?albumId=" + albumId);
		}
		
	}

}
