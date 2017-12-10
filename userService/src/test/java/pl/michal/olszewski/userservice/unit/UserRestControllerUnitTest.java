package pl.michal.olszewski.userservice.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.userservice.api.UserRestController;
import pl.michal.olszewski.userservice.user.User;
import pl.michal.olszewski.userservice.user.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserRestControllerUnitTest {

    @Mock
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userRestController = new UserRestController(userService);
    }

    @Test
    public void shouldReturnUser() throws UserPrincipalNotFoundException {
        User user = new User();
        given(userService.getUserByUserName("user")).willReturn(Optional.of(user));
        ResponseEntity<User> userResponseEntity = userRestController.getUserByUserName("user");

        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponseEntity.getBody()).isEqualTo(user);
    }

    @Test
    public void shouldFailedWhenUserNotExist() throws UserPrincipalNotFoundException {
        given(userService.getUserByUserName("userss")).willReturn(Optional.empty());
        ResponseEntity<User> userResponseEntity = userRestController.getUserByUserName("userss");
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}
