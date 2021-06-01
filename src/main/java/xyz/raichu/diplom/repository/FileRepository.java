package xyz.raichu.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.raichu.diplom.entity.File;

/**
 * 24.05.2021
 * FileRepository
 * 04:17
 */
public interface FileRepository extends JpaRepository<File, Long> {
    Iterable<File> findAllByNameIsNotNullAndPhrasesIsNotNull ();
}
