package com.library.app.repositories.repo;

import com.library.app.repositories.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmailAndDeletedIsNull(String email);
}
