package xyz.raichu.diplom.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.repository.UserRepository;

/**
 * 16.05.2021
 * AdminUserCommandLineRunner
 * 16:35
 */
@Component
@Slf4j
public class AdminUserCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserCommandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEnabled(true);
        admin.setAccountNonExpired(true);
        admin.setCredentialsNonExpired(true);
        admin.setAccountNonLocked(true);

        User savedUser = userRepository.findByUsername(admin.getUsername()).orElseGet(() -> userRepository.save(admin));
        log.info("Admin user created, id: {}", savedUser.getId());
    }
}
