package com.melon.album.vo;

import com.melon.common.web.pager.Pager;
import com.melon.common.web.pager.PagerFactory;

public class AlbumSearchVO {

	private Pager pager;
	private String artistId; // 검색할 칼럼 추가 후 getter, setter 작성
	
	public Pager getPager(){
		if(pager == null){
			pager = PagerFactory.getPager(Pager.ORACLE);
		}
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	
}
