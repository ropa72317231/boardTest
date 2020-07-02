package com.example.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

@SpringBootTest
class BoardRepositoryTests {
	@Autowired
	BoardRepository br;
	
	@Test
	void 게시글100개입력하기() {
		for(int i=1; i<=100; i++) {
			Board board = new Board();
			board.setTitle(i + "번 제목");
			board.setContent(i + "번 내용");
			board.setUserId("internia112@kakao.com");
			br.save(board);
			
		}
	}

	//Shift + Alt + O 형광펜 기능
	@Test
	void contextLoads1() {
		System.out.println("dddd");
	}
	@Test
	void contextLoads2() {
		System.out.println("aaaa");
	}

}
