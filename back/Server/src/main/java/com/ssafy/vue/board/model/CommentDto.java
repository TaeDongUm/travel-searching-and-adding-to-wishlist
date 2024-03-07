package com.ssafy.vue.board.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentDto {

	private String article_no, user_id, comment, comment_no;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date register_time;
	
	public CommentDto() {}
	
	public CommentDto(String article_no, String user_id, String comment) {
		this.article_no = article_no;
		this.user_id = user_id;
		this.comment = comment;
	}
	public String getArticle_no() {
		return article_no;
	}
	public void setArticle_no(String article_no) {
		this.article_no = article_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment_no() {
		return comment_no;
	}
	public void setComment_no(String comment_no) {
		this.comment_no = comment_no;
	}
	public Date getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	@Override
	public String toString() {
		return "CommentDto [article_no=" + article_no + ", user_id=" + user_id + ", comment=" + comment
				+ ", comment_no=" + comment_no + ", register_time=" + register_time + "]";
	}
	
	
	
}
