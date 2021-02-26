package my.board;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.java.Log;
import my.board.domain.Board;
import my.board.persistence.BoardRepository;

@SpringBootTest
@Log
public class BoardTests {

	@Autowired
	BoardRepository repo;
	
	@Test
	public void insertdate() {
		
		
		IntStream.range(1, 100).forEach(i -> {

			Board bo=new Board();
			bo.setTitle("Free Board..." + i);
			bo.setContent("Free Content..." + i);
			bo.setWriter("user" + i % 10);

			repo.save(bo);
		});
	}
}
