package com.melon.album.dao;

import java.util.List;

import com.melon.album.vo.AlbumSearchVO;
import com.melon.album.vo.AlbumVO;

public interface AlbumDao {

	public int insertNewAlbum(AlbumVO albumVO);
	
	public int selectAllAlbumsCount(AlbumSearchVO albumSearchVO);
	
	public List<AlbumVO> selectAllAlbums(AlbumSearchVO albumSearchVO);
	
	public AlbumVO selectOneAlbum(String albumId);
	
	public int deleteOneAlbum(String albumId);
	
}
