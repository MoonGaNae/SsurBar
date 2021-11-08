package com.ssurbar.survey.db.repository;

import com.ssurbar.survey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
