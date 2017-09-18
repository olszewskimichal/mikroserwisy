package pl.michal.olszewski.userservice.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
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

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        //assercje
        return Optional.ofNullable(userService.createUser(user))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        //asercje
        user.setId(id);
        return Optional.ofNullable(userService.updateUser(id, user))
                .map(result -> new ResponseEntity(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long id) {
        return Optional.ofNullable(userService.deleteUser(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
