package pl.michal.olszewski.userservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.michal.olszewski.userservice.user.User;
import pl.michal.olszewski.userservice.user.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
@EnableHystrix
@Slf4j
public class UserserviceApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        User user = new User();
        user.setUserName("test");
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        userRepository.save(user);
    }
}
