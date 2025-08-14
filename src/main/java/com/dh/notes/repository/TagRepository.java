package com.dh.notes.repository;

import com.dh.notes.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE t.userId = :user_id")
    Page<Tag> findByUserId(@Param("user_id") Long userId, Pageable pageable);

    @Query("SELECT t FROM Tag t WHERE t.userId = :user_id AND LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Tag> findByUserAndQuery(@Param("search") String search,
                                 @Param("user_id") Long userId,
                                 Pageable pageable);

    Optional<Tag> findByNameAndUserId(String name, Long userId);

}