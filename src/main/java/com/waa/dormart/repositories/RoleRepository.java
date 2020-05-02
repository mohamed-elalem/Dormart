package com.waa.dormart.repositories;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT r from Role r where r.name IN (:roles)")
    List<Role> findAllIn(@Param("roles") List<String> roles);
}
