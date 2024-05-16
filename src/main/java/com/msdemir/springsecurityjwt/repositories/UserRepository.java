package com.msdemir.springsecurityjwt.repositories;

import com.msdemir.springsecurityjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
