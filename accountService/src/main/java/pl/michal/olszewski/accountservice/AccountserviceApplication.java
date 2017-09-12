package pl.michal.olszewski.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.accountservice.unit.account.Account;
import pl.michal.olszewski.accountservice.unit.account.AccountRepository;

@SpringBootApplication
@EnableJpaAuditing
public class AccountserviceApplication implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public RestTemplate loadBalancedOauth2RestTemplate() {
        return new RestTemplateBuilder().build();

    }

    public static void main(String[] args) {
        SpringApplication.run(AccountserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Account account = new Account();
        account.setUserName("test");
        accountRepository.save(account);
    }
}
