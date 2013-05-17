package org.emaginalabs.example.springdata.repository;

import org.emaginalabs.example.springdata.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: jaluque
 * Date: 14/05/13
 * Time: 10:46
 */
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

}
