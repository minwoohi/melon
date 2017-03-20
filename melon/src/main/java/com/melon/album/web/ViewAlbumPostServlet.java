package com.melon.album.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.melon.album.biz.AlbumBiz;
import com.melon.album.biz.AlbumBizImpl;
import com.melon.album.vo.AlbumVO;
import com.melon.common.web.DownloadUtil;

public class ViewAlbumPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AlbumBiz albumBiz;
    
    public ViewAlbumPostServlet() {
    	albumBiz = new AlbumBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String albumId = request.getParameter("albumId");
		
		AlbumVO album = albumBiz.getOneAlbum(albumId);
		
		String postPath ="D:\\uploadFiles\\post\\";
		
		postPath += album.getArtistId() + File.separator;
		postPath += album.getAlbumName();
		
		DownloadUtil util = DownloadUtil.getInstance(postPath);
		util.download(request, response, album.getPost(), album.getPost());
	}

}
