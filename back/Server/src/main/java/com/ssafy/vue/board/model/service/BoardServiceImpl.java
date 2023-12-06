package com.ssafy.vue.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.CommentDto;
import com.ssafy.vue.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper boardMapper;
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<BoardDto> listArticle(Map<String, Object> param) {
        return boardMapper.listArticle(param);
    }

    @Override
    public void updateHit(int articleNo) {
        boardMapper.updateHit(articleNo);
    }

    @Override
    public BoardDto getArticle(int articleNo) {
        return boardMapper.getArticle(articleNo);
    }

	@Override
	public void registArticle(BoardDto boardDto) {
		boardMapper.registArticle(boardDto);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int articleno) {
		boardMapper.deleteArticle(articleno);
	}

	@Override
	public void insertReply(BoardDto boardDto) {
		boardMapper.insertReply(boardDto);
	}

	@Override
	public void registComment(CommentDto commentDto) {
		boardMapper.registComment(commentDto);
	}

	@Override
	public List<CommentDto> getComments(String articleno) {
		return boardMapper.getComments(articleno);
	}

	@Override
	public void deleteComment(String comment_no) {
		boardMapper.deleteComment(comment_no);
	}

	@Override
	public void updateComment(CommentDto commentDto) {
		boardMapper.updateComment(commentDto);
	}

}