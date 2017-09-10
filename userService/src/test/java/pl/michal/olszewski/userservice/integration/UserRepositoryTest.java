package pl.michal.olszewski.userservice.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michal.olszewski.userservice.user.User;
import pl.michal.olszewski.userservice.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

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
        user.setEmail("email");

        entityManager.persistAndFlush(user);

        assertThat(userRepository.findByEmail("email").isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindUserByUserNameWhereNotExists() {
        assertThat(userRepository.findByUserName("userName2").isPresent()).isFalse();
    }

    @Test
    public void shouldNotFindUserByEmailWhereNotExists() {
        assertThat(userRepository.findByEmail("email2").isPresent()).isFalse();
    }
}
