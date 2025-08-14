package com.dh.notes.repository;

import com.dh.notes.model.Note;
import com.dh.notes.util.enums.NoteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.status = :status AND n.user.username = :username")
    Page<Note> findAllByStatus(@Param("status") NoteStatus status,
                               @Param("username") String username,
                               Pageable pageable);

    @Query("SELECT DISTINCT n FROM Note n " +
            "LEFT JOIN n.tags t " +
            "WHERE (LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "   OR LOWER(n.content) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "   OR LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND n.status = :status " +
            "AND n.user.username = :username")
    Page<Note> searchNotes(@Param("search") String search,
                           @Param("status") NoteStatus status,
                           @Param("username") String username,
                           Pageable pageable);
}
