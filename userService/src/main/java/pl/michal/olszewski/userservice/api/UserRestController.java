package pl.michal.olszewski.userservice.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.userservice.user.User;
import pl.michal.olszewski.userservice.user.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/users/")
@Slf4j
@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable(value = "userName") String userName) {
        log.info("Próba pobrania uzytkownika {}", userName);
        Optional<User> userByUserName = userService.getUserByUserName(userName);
        return userByUserName.flatMap(v -> Optional.of(new ResponseEntity<>(v, OK))).orElseGet(() -> new ResponseEntity<>(new User(), NOT_FOUND));
    }

    @RequestMapping(path = "/me")
    public ResponseEntity<User> getCurrentUser(Principal user) throws UserPrincipalNotFoundException {
        if (user != null)
            return new ResponseEntity<>(userService.getUserByUserName(user.getName()).get(), OK);
        else throw new UserPrincipalNotFoundException("Uzytkownik nie zalogowany");
    }
}
