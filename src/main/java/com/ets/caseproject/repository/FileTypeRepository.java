package com.ets.caseproject.repository;

import com.ets.caseproject.domain.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType,Long> {
    Optional<FileType> findByName(String name);
}
