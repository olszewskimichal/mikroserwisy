package pl.michal.olszewski.userservice.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User result = null;
        if (userRepository.exists(id)) {
            result = this.userRepository.save(user);
        }
        return result;
    }

    public Boolean deleteUser(Long id) {
        boolean deleted = false;
        if (userRepository.exists(id)) {
            this.userRepository.delete(id);
            deleted = true;
        }
        return deleted;
    }
}
