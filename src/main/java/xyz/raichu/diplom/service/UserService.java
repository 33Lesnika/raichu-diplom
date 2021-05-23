package xyz.raichu.diplom.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 24.05.2021
 * UserService
 * 02:31
 */
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id){
        return userRepository.getOne(id);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void delete(String username){
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
    }
}
