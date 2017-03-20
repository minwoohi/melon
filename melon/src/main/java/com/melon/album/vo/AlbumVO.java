package com.melon.album.vo;

import com.melon.artist.vo.ArtistVO;

public class AlbumVO {

	private String albumId;
	private String artistId;
	private String releaseDate;
	private String publisher;
	private String entertainment;
	private String genre;
	private String post;
	private String albumName;
	private ArtistVO artistVO;
	
	public ArtistVO getArtistVO() {
		if(artistVO == null)
			artistVO = new ArtistVO();
		return artistVO;
	}

	public void setArtistVO(ArtistVO artistVO) {
		this.artistVO = artistVO;
	}
	
	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
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

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(String entertainment) {
		this.entertainment = entertainment;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	
	
}
