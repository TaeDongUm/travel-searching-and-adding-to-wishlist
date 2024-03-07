package com.ssafy.vue.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.CommentDto;

import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BoardMapper {

    List<BoardDto> listArticle(Map<String, Object> param);
    void updateHit(int articleNo);
    BoardDto getArticle(int articleNo);
	void registArticle(BoardDto boardDto);
	void modifyArticle(BoardDto boardDto);
	void deleteArticle(int articleno);
	void insertReply(BoardDto boardDto);
	void registComment(CommentDto commentDto);
	List<CommentDto> getComments(String articleno);
	void deleteComment(String comment_no);
	void updateComment(CommentDto commentDto);
}