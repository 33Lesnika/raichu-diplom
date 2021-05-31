package xyz.raichu.diplom.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.exception.AlreadyExistsException;
import xyz.raichu.diplom.exception.NotFoundException;
import xyz.raichu.diplom.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 24.05.2021
 * UserService
 * 02:31
 */
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void delete(String username) {
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User register(User registration) {
        userRepository
                .findByUsername(registration.getUsername())
                .ifPresent(u -> {
                    throw new AlreadyExistsException("User with username '" + registration.getUsername() + "' already exists");
                });
        // Avoid malformed request
        registration.setId(null);
        registration.setAccountNonLocked(true);
        registration.setEnabled(true);
        registration.setAccountNonExpired(true);
        registration.setCredentialsNonExpired(true);
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));
        return userRepository.save(registration);
    }

    @Transactional
    public User toggle(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }
}
