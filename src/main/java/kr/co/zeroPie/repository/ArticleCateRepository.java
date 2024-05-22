package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ArticleCate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCateRepository extends CrudRepository<ArticleCate, Integer> {
}
