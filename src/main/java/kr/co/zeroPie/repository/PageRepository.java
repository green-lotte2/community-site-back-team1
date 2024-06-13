package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Page;
import kr.co.zeroPie.repository.custom.PageRepositoryCustom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer>, PageRepositoryCustom {
}
