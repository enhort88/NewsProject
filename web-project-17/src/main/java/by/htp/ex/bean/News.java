package by.htp.ex.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer newsId;
	private String title;
	private String briefNews;
	private String content;
	private String newsDate;
	private Integer userId;
	
	

	public News() {
		super();
	}



	public News(String title, String briefNews, String content, String newsDate, Integer userId) {
		super();
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
		this.newsDate = newsDate;
		this.userId = userId;
	}
	public News(Integer newsId, String title, String briefNews, String content, String newsDate, Integer userId) {
		super();
		this.newsId = newsId;
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
		this.newsDate = newsDate;
		this.userId = userId;
	}



	public Integer getIdNews() {
		return newsId;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setIdNews(Integer idNews) {
		this.newsId = idNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBriefNews() {
		return briefNews;
	}

	public void setBriefNews(String briefNews) {
		this.briefNews = briefNews;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;

	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(briefNews, content, newsId, newsDate, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		News other = (News) obj;
		return Objects.equals(briefNews, other.briefNews) && Objects.equals(content, other.content)
				&& Objects.equals(newsId, other.newsId) && Objects.equals(newsDate, other.newsDate)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", title=" + title + ", briefNews=" + briefNews + ", content=" + content
				+ ", newsDate=" + newsDate + ", userId=" + userId + "]";
	}


}
