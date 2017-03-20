package com.melon.album.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.melon.album.biz.AlbumBiz;
import com.melon.album.biz.AlbumBizImpl;
import com.melon.album.vo.AlbumVO;
import com.melon.common.constants.AuthConst;
import com.melon.common.web.MultipartHttpServletRequest;
import com.melon.common.web.MultipartHttpServletRequest.MultipartFile;
import com.melon.user.vo.UserVO;

public class ViewAlbumWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AlbumBiz albumBiz;

	public ViewAlbumWriteServlet() {
		albumBiz = new AlbumBizImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("_USER_");

		if (user.getAuthorizationId().equals(AuthConst.NORMAL_USER)) {
			response.sendError(404);
		} else if (user.getAuthorizationId().equals(AuthConst.OPERATOR_USER)
				|| user.getAuthorizationId().equals(AuthConst.ADMIN_USER)) {
			RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/album/write.jsp");
			dis.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("_USER_");

		if (user.getAuthorizationId().equals(AuthConst.NORMAL_USER)) {
			response.sendError(404);
		} else if (user.getAuthorizationId().equals(AuthConst.OPERATOR_USER)
				|| user.getAuthorizationId().equals(AuthConst.ADMIN_USER)) {
			MultipartHttpServletRequest multipart = new MultipartHttpServletRequest(request);

			String artistId = request.getParameter("artistId");
			String albumName = multipart.getParameter("albumName");
			String releaseDate = multipart.getParameter("releaseDate");
			String publisher = multipart.getParameter("publisher");
			String entertainment = multipart.getParameter("entertainment");
			String genre = multipart.getParameter("genre");

			// post로 전송된 파일. 매개변수로 jsp파일의 타입file에 해당하는 태그의 name 넣는다.
			MultipartFile post = multipart.getFile("post");
			if (post != null && post.getFileSize() > 0) { // 파일을 올리지 않았다면
				// postFileName = post.getFileName();

				File dir = new File("D:\\uploadFiles\\post\\" + artistId + File.separator + albumName);
				dir.mkdirs();
				post.write(dir.getAbsolutePath() + File.separator + post.getFileName());
			}

			AlbumVO albumVO = new AlbumVO();

			albumVO.setAlbumName(albumName);
			albumVO.setArtistId(artistId);
			albumVO.setEntertainment(entertainment);
			albumVO.setGenre(genre);
			albumVO.setPost(post.getFileName());
			albumVO.setPublisher(publisher);
			albumVO.setReleaseDate(releaseDate);

			if (albumBiz.addNewAlbum(albumVO)) {
				StringBuffer script = new StringBuffer();
				script.append("<script type='text/javascript'>");
				script.append(" opener.location.reload();");
				script.append("self.close();"); // 스스로 닫는 쿼리문.
				script.append("</script>"); // 작성해

				PrintWriter writer = response.getWriter(); // resopnse 객체에 담는다.
				writer.write(script.toString()); // 버퍼에 담긴 상태
				writer.flush(); // 버퍼 내용 흘려보내기
				writer.close(); // 닫기
			} else {
				response.sendRedirect("/melon/album/write");
			}
		}
	}

}
