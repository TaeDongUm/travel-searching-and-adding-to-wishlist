package com.ssafy.vue.board.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.CommentDto;
import com.ssafy.vue.board.model.service.BoardService;
import com.ssafy.vue.http.model.HttpResponseBody;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping()
    public ResponseEntity<?> list(@RequestParam Map<String, Object> map) {
        try {
            System.out.println("list");
            List<BoardDto> list=boardService.listArticle(map);
            System.out.println(list);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(list);
        }catch(Exception e) {
            return exceptionHandling(e);
        }
    }


    @GetMapping("/{articleno}")
    public ResponseEntity<BoardDto> getArticle(
            @PathVariable("articleno") String articleno)
            throws Exception {
        boardService.updateHit(Integer.parseInt(articleno));
        return new ResponseEntity<BoardDto>(boardService.getArticle(Integer.parseInt(articleno)), HttpStatus.OK);
    }
    
    //글등록
    @PostMapping()
    public ResponseEntity<HttpResponseBody> registArticle(
            @RequestBody BoardDto boardDto)
            throws Exception {
    	try {
    		System.out.println(boardDto);
    		boardDto.setGroupLayer(boardDto.getGroupLayer()+1);
    			boardService.registArticle(boardDto);
    		
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(500).body(new HttpResponseBody("insert error", null));
    		
    	}
    	return ResponseEntity.status(200).body(new HttpResponseBody("insert success", null));
    }
    
    //댓글 등록
    @PostMapping("comment")
    public ResponseEntity<?> registComment(
            @RequestBody CommentDto commentDto)
            throws Exception {
    	try {
    		System.out.println(commentDto);
    		boardService.registComment(commentDto);
    		List<CommentDto> commentDtoList = boardService.getComments(commentDto.getArticle_no());
    		return ResponseEntity.ok(commentDtoList);
//    		return ResponseEntity.status(200).body(new HttpResponseBody("insert success", commentDtoList));
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(500).body(new HttpResponseBody("insert error", null));
    		
    	}
//    	return ResponseEntity.status(200).body(new HttpResponseBody("insert success", null));
    }
//    @RequestParam("articleno") String articleno
    // 댓글 가져오기        
    @PostMapping("getComments")
    public ResponseEntity<?> getComments(@RequestParam("articleno") String articleno){
    	try {
    		System.out.println(articleno);
    		List<CommentDto> commentDto = boardService.getComments(articleno);
    		System.out.println(commentDto.get(0).getComment());
            HttpHeaders header = new HttpHeaders();
//            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//            return ResponseEntity.ok().headers(header).body(commentDto);
            return ResponseEntity.ok(commentDto);
    	}catch(Exception e) {
    		return exceptionHandling(e);
    	}
    }
    
    // 댓글 삭제
    @PostMapping("deleteComment")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDto commentDto){
    	try {
    		System.out.println(commentDto);
    		boardService.deleteComment(commentDto.getComment_no());
    		List<CommentDto> commentDtolist = boardService.getComments(commentDto.getArticle_no());
//    		System.out.println(commentDto.get(0).getComment());
            HttpHeaders header = new HttpHeaders();
//            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//            return ResponseEntity.ok().headers(header).body(commentDto);
//            return ResponseEntity.ok(commentDto);
            return ResponseEntity.ok(commentDtolist);
    	}catch(Exception e) {
    		return exceptionHandling(e);
    	}
    }
    
    // 댓글 수정
    @PostMapping("updateComment")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto){
    	try {
    		System.out.println(commentDto);
    		boardService.updateComment(commentDto);
    		List<CommentDto> commentDtolist = boardService.getComments(commentDto.getArticle_no());
//    		System.out.println(commentDto.get(0).getComment());
            HttpHeaders header = new HttpHeaders();
//            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//            return ResponseEntity.ok().headers(header).body(commentDto);
//            return ResponseEntity.ok(commentDto);
            return ResponseEntity.ok(commentDtolist);
    	}catch(Exception e) {
    		return exceptionHandling(e);
    	}
    }
    
    
    @DeleteMapping("/{articleno}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleno") int articleno){
    	// 로그인 한 회원만 접근 가능
    	// 로그인 한 회원은 자기꺼만 삭제 가능
    	// admin 관리자의 경우 모든 회원 글 삭제 가능
    	boardService.deleteArticle(articleno);
    	return null;
    }
    
    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}