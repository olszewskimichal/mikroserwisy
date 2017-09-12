package pl.michal.olszewski.userservice.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.userservice.user.User;
import pl.michal.olszewski.userservice.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByUserName() {
        User user = new User();
        user.setUserName("userName");

        entityManager.persistAndFlush(user);

        assertThat(userRepository.findByUserName("userName").isPresent()).isTrue();
    }

    @Test
    public void shouldFindUserByEmail() {
        User user = new User();
        user.setEmail("email2");

        entityManager.persistAndFlush(user);

        assertThat(userRepository.findByEmail("email2").isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindUserByUserNameWhereNotExists() {
        assertThat(userRepository.findByUserName("userName2").isPresent()).isFalse();
    }

    @Test
    public void shouldNotFindUserByEmailWhereNotExists() {
        assertThat(userRepository.findByEmail("email3").isPresent()).isFalse();
    }
}
