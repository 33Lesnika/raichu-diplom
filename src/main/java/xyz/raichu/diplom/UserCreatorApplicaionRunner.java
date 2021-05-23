package xyz.raichu.diplom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.service.UserService;

/**
 * 24.05.2021
 * UserCreatorCommandLineRunner
 * 02:22
 */
@Component
@Slf4j
public class UserCreatorApplicaionRunner implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserCreatorApplicaionRunner(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getSourceArgs().length == 0){
            return;
        }
        if (args.containsOption("showUsers")){
            userService.getAll().forEach(u -> log.info("Username {} id {}", u.getUsername(), u.getId()));
            System.exit(0);
        }
        if (args.containsOption("deleteUser")){
            userService.delete(args.getOptionValues("deleteUser").get(0));
            System.exit(0);
        }
        if (args.containsOption("deleteUserID")){
            userService.delete(Long.valueOf(args.getOptionValues("deleteUserID").get(0)));
            System.exit(0);
        }

        try {
            ensureHasUserData(args);
        } catch (NotFoundException e){
            log.error(e.getMessage());
            System.exit(-1);
        }
        createUser(args.getOptionValues("username").get(0), args.getOptionValues("password").get(0));
        System.exit(0);
    }

    private void ensureHasUserData(ApplicationArguments arguments) throws NotFoundException {
        if (!arguments.containsOption("username") || !arguments.containsOption("password")) {
            throw new NotFoundException("Usage: java -jar raichu.jar --username=[USERNAME] --password=[PASSWORD]");
        }
    }

    private void createUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        userService.save(user);
        log.info("Created user: '{}'", username);
    }
}
