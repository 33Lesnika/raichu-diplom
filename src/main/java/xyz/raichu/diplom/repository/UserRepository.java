package xyz.raichu.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.raichu.diplom.entity.User;

import java.util.Optional;

/**
 * 16.05.2021
 * UserRepository
 * 16:15
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
