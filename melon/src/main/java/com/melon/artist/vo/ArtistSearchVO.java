package com.melon.artist.vo;

import com.melon.common.web.pager.Pager;
import com.melon.common.web.pager.PagerFactory;

public class ArtistSearchVO {

	private Pager pager;

	public Pager getPager() {
		if(pager == null){
			pager = PagerFactory.getPager(Pager.ORACLE);
		}
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	
	
}
