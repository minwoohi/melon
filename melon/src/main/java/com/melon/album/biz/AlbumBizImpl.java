package com.melon.album.biz;

import java.util.ArrayList;
import java.util.List;

import com.melon.album.dao.AlbumDao;
import com.melon.album.dao.AlbumDaoImpl;
import com.melon.album.vo.AlbumSearchVO;
import com.melon.album.vo.AlbumVO;
import com.melon.common.web.pager.Pager;

public class AlbumBizImpl implements AlbumBiz{

	
	
	private AlbumDao albumDao;
	
	public AlbumBizImpl() {
		albumDao = new AlbumDaoImpl();
	}
	
	@Override
	public boolean addNewAlbum(AlbumVO albumVO) {
		
		return albumDao.insertNewAlbum(albumVO) > 0;
	}

	@Override
	public List<AlbumVO> getAllAlbums(AlbumSearchVO albumSearchVO) {
		int albumCount = albumDao.selectAllAlbumsCount(albumSearchVO);
		
		Pager pager = albumSearchVO.getPager();
		pager.setTotalArticleCount(albumCount);
		
		if(albumCount == 0){
			return new ArrayList<AlbumVO>();
		}
		
		return albumDao.selectAllAlbums(albumSearchVO);
	}

	@Override
	public AlbumVO getOneAlbum(String albumId) {
		return albumDao.selectOneAlbum(albumId);
	}

	@Override
	public boolean removeOneAlbum(String albumId) {
		return albumDao.deleteOneAlbum(albumId) > 0;
	}

}
