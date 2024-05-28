package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TermsRepository extends JpaRepository<Terms, String> {
}
