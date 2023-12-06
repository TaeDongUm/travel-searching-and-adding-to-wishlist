package com.ssafy.vue.board.model.service;

import java.util.List;
import java.util.Map;
import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.CommentDto;

public interface BoardService {

    List<BoardDto> listArticle(Map<String, Object> param);
    public void updateHit(int articleNo);
    public BoardDto getArticle(int articleNo);
	void registArticle(BoardDto boardDto);
	void modifyArticle(BoardDto boardDto);
	void deleteArticle(int articleno);
	void insertReply(BoardDto boardDto);
	void registComment(CommentDto commentDto);
	List<CommentDto> getComments(String articleno);
	void deleteComment(String comment_no);
	void updateComment(CommentDto commentDto);
}