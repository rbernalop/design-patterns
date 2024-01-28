package org.rbernalop.strategy.repository;

import org.rbernalop.strategy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
