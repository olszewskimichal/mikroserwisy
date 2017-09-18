package pl.michal.olszewski.userservice.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByUserName(String userName) {
        log.info("Pobieram uzytkownika po nazwie {}", userName);
        return userRepository.findByUserName(userName);
    }

    public User createUser(User user) {
        log.info("Tworze uzytkownika {]", user);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        log.info("Aktualizuje uzytkownika o id {} {}", id, user);
        User result = null;
        if (userRepository.exists(id)) {
            result = this.userRepository.save(user);
        }
        return result;
    }

    public Boolean deleteUser(Long id) {
        log.info("Usuwam uzytkownika o id {}", id);
        boolean deleted = false;
        if (userRepository.exists(id)) {
            this.userRepository.delete(id);
            deleted = true;
        }
        return deleted;
    }
}
