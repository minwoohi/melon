package com.melon.artist.dao;

import java.util.List;

import com.melon.artist.vo.ArtistSearchVO;
import com.melon.artist.vo.ArtistVO;

public interface ArtistDao {
	
	public int insertNewArtist(ArtistVO artist);
	
	public int selectAllArtistsCount(ArtistSearchVO artistSearchVO);

	public List<ArtistVO> selectAllArtists(ArtistSearchVO artistSearchVO);
	
	public ArtistVO selectOneArtist(String artistId);
	
	public int deleteOneArtist(String artistId);
}
