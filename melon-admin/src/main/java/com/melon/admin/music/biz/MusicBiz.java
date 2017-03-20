package com.melon.admin.music.biz;

import java.util.List;

import com.melon.admin.music.vo.MusicVO;

public interface MusicBiz {

	public List<MusicVO> getTopTenMusic();
}
