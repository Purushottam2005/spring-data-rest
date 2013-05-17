package org.emaginalabs.example.springdata.repository;

import org.emaginalabs.example.springdata.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * User: jaluque
 * Date: 14/05/13
 * Time: 10:06
 */
@Repository(value = "todoRepository")
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findByTitleLike(@Param("title") String title, Pageable pageable);
    Page<Todo> findByDescriptionLike(@Param("description") String description, Pageable pageable);
    Page<Todo> findByTitleLikeAndDescriptionLike(@Param("title") String title,
                                                 @Param("description") String description, Pageable pageable);

    Page<Todo> findByDue(@Param("due") Date due, Pageable pageable);

    @Query("select t from todo t where t.priority.id = :priority")
    Page<Todo> findByPriority(@Param("priority") Long priority, Pageable pageable);

    @Query("select t from todo t where t.due between :initDate and :finalDate")
    Page<Todo> findByDueBeetween(@Param("initDate") Date initDate,
                                 @Param("finalDate") Date finalDate, Pageable pageable);




}
