package com.melon.music.vo;

import com.melon.album.vo.AlbumVO;

public class MusicVO {

	private String musicId;
	private String albumId;
	private String title;
	private int likeCount;
	private String mp3File;
	private String musician;
	private String director;
	private String lyrics;
	
	private AlbumVO albumVO;

	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getMp3File() {
		return mp3File;
	}

	public void setMp3File(String mp3File) {
		this.mp3File = mp3File;
	}

	public String getMusician() {
		return musician;
	}

	public void setMusician(String musician) {
		this.musician = musician;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public AlbumVO getAlbumVO() {
		if(albumVO == null)
			albumVO = new AlbumVO();
		return albumVO;
	}

	public void setAlbumVO(AlbumVO albumVO) {
		this.albumVO = albumVO;
	}
	
	
}
