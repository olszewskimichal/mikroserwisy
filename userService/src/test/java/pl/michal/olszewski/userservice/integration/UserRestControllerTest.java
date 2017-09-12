package pl.michal.olszewski.userservice.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michal.olszewski.userservice.user.User;
import pl.michal.olszewski.userservice.user.UserRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @LocalServerPort
    public int port;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseURL;

    @Before
    public void setup() {
        baseURL = "http://localhost:" + port + "/api/v1/users/";
    }

    @Test
    public void shouldGetUserByUserName() {
        User user = new User();
        user.setUserName("test2");
        userRepository.saveAndFlush(user);

        ResponseEntity<User> userFromApi = thenGetUserByUserNameFromApi("test2");

        assertThat(userFromApi.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userFromApi.getBody()).isNotNull();
    }

    @Test
    public void shouldReturnErrorWhenTryGetUserWhichNotExist() {
        ResponseEntity<User> userFromApi = thenGetUserByUserNameFromApi("dupa");
        assertThat(userFromApi.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<User> thenGetUserByUserNameFromApi(String userName) {
        return restTemplate.getForEntity(baseURL + "/user/{userName}", User.class, userName);
    }
}

//TODO zrobic buildery itp do tych testów