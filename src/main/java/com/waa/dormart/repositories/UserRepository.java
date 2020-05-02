package com.waa.dormart.repositories;

import com.waa.dormart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u from User u WHERE u.role.name = :role AND u.active = :active")
    List<User> findAllByRoleAndActive(@Param("role") String role, @Param("active") Boolean active);
}
