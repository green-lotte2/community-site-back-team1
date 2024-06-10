package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    int countByArticleNo(int articleNo);
    void deleteByArticleNo(int articleNo);
    List<File> findByArticleNo(int articleNo);
}
