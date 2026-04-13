package com.arthurllucass.projeto_helpdesk.repository;

import com.arthurllucass.projeto_helpdesk.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrderByIdAsc();
    Optional<User> findByNameIgnoreCase(String name);
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByPhone(String phone);

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
}
