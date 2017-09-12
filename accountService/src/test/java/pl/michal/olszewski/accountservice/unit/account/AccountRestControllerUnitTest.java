package pl.michal.olszewski.accountservice.unit.account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class AccountRestControllerUnitTest {

    @Mock
    private AccountRestController restController;

    @Mock
    private AccountService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        restController = new AccountRestController(service);
    }

    @Test
    public void shouldReturnAccounts() throws Exception {
        Account account = new Account();
        given(service.getUserAccounts()).willReturn(singletonList(account));
        ResponseEntity responseEntity = restController.getUserAccount();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(singletonList(account));
    }

}
