package com.melon.music.vo;

import com.melon.common.web.pager.Pager;
import com.melon.common.web.pager.PagerFactory;

public class MusicSearchVO {

	private Pager pager;
	
	// url 파라미터 있다면 추가해야 한다.
	private String albumId;
	private String artistId;

	public Pager getPager() {
		if(pager == null)
			pager = PagerFactory.getPager(pager.ORACLE, 20, 10);
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	
}
