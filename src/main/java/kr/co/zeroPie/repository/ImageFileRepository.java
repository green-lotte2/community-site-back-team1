package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    List<ImageFile> findByArticleNo(int articleNo);
    void deleteByArticleNo(int articleNo);
}
