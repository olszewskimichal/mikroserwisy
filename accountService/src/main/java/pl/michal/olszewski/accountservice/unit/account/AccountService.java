package pl.michal.olszewski.accountservice.unit.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.accountservice.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    public AccountService(AccountRepository accountRepository, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.restTemplate = restTemplate;
    }

    public List<Account> getUserAccounts() {
        log.info("pobieram konta uzytkownika ");
        List<Account> accounts = new ArrayList<>();
        User user = restTemplate.getForObject("http://localhost:8787/user-service/api/v1/users/user/test", User.class);
        if (user == null) {
            return accounts;
        }
        accounts = accountRepository.findAccountsByUserName(user.getUserName());
        log.debug("Konta uzytkownika {} to {}", user, accounts);
        return accounts;
    }
}
