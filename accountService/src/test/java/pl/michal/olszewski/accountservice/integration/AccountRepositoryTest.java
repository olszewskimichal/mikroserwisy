package pl.michal.olszewski.accountservice.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.accountservice.unit.account.Account;
import pl.michal.olszewski.accountservice.unit.account.AccountRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest extends IntegrationTestBase {

    @Autowired
    private AccountRepository repository;

    @Test
    public void shouldFindAccountByUserName() {
        Account account = new Account();
        account.setUserName("tescik");
        entityManager.persistAndFlush(account);

        List<Account> tescik = repository.findAccountsByUserName("tescik");

        assertThat(tescik.size()).isEqualTo(1);
    }
}
