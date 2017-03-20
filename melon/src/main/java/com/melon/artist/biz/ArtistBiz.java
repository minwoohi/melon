package com.melon.artist.biz;

import java.util.List;

import com.melon.artist.vo.ArtistSearchVO;
import com.melon.artist.vo.ArtistVO;

public interface ArtistBiz {
	
	public boolean addNewArtist(ArtistVO artistVO);
	
	public List<ArtistVO> getAllArtists(ArtistSearchVO artistSearchVO);
	
	public ArtistVO getOneArtist(String artistId);
	
	public boolean deleteOneArtist(String artistId);

}
