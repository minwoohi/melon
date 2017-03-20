package com.melon.admin.authorization.vo;

import com.melon.admin.common.web.pager.Pager;
import com.melon.admin.common.web.pager.PagerFactory;

public class AuthorizationSearchVO {
	private Pager pager;
	// 검색할 때 필요한 칼럼이 생긴다면 추가한다.
	
	public Pager getPager() {
		if(pager == null){
			pager = PagerFactory.getPager(Pager.ORACLE, 1000, 10);
		}
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
}
