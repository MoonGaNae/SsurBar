package com.ssurbar.survey.db.repository;

import com.ssurbar.survey.db.entity.User;
import com.ssurbar.survey.db.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String email);

    public List<User> findUsersByUserType(UserRole userType);
}
