package my.board.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import my.board.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	
}
