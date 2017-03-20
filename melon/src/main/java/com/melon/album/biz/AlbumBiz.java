package com.melon.album.biz;

import java.util.List;

import com.melon.album.vo.AlbumSearchVO;
import com.melon.album.vo.AlbumVO;

public interface AlbumBiz {
	
	public boolean addNewAlbum(AlbumVO albumVO);
	
	public List<AlbumVO> getAllAlbums(AlbumSearchVO albumSearchVO);
	
	public AlbumVO getOneAlbum(String albumId);
	
	public boolean removeOneAlbum(String albumId);
	
}
