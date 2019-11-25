package com.search.models;

import java.util.Date;
import java.util.List;

public class SummaryDTO {

	private Long id;
	private String title;
	private String creationDate;
	private String lastUpdateDate;
	private String summary;
	private String userName;
	private String bannerUrl;
	private List<String> tags;
	
	public SummaryDTO(Long id, String title, String creationDate, String lastUpdateDate, 
			String summary, String userName, String bannerUrl, List<String> tags) {
		super();
		this.id = id;
		this.title = title;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.summary = summary;
		this.userName = userName;
		this.bannerUrl = bannerUrl;
		this.tags = tags;
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	
	public String getCreationDate() {
		return creationDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public List<String> getTags() {
		return tags;
	}

	public String getSummary() {
		return summary;
	}
	public String getUserName() {
		return userName;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	@Override
	public String toString() {
		return "SummaryDTO [id=" + id + ", title=" + title + ", creationDate=" + creationDate + ", lastUpdateDate="
				+ lastUpdateDate + ", summary=" + summary + ", userName=" + userName + ", tags=" + tags + "]";
	}
	
}
