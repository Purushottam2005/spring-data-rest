package org.emaginalabs.example.springdata.repository;

import org.emaginalabs.example.springdata.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
