package com.project.springBlog.Repository;

import com.project.springBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositry extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
}
