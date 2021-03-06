package pl.michal.olszewski.userservice.integration;

import org.junit.Assert;
import org.junit.Test;
import pl.michal.olszewski.userservice.user.User;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserAuditingTest extends IntegrationTestBase {

    @Test
    public void shouldSetCreatedDateOnUserWhenPersistObject() {
        //given
        User user = new User();
        //when
        entityManager.persistAndFlush(user);
        //then
        assertThat(user.getCreatedDate()).isNotNull();
    }

    @Test
    public void shouldSetModifiedDateWhenUpdateAndPersistObject() {
        //given
        User user = new User();
        //when
        entityManager.persistAndFlush(user);
        //then
        long modifiedDate = user.getModifiedDate();
        assertThat(modifiedDate).isNotNull();
        long createdDate = user.getCreatedDate();
        assertThat(createdDate).isNotNull();

        //when
        user.setEmail("aaa@o2.pl");
        entityManager.persistAndFlush(user);
        //then
        assertThat(user.getCreatedDate()).isEqualTo(createdDate);
        assertThat(user.getModifiedDate()).isNotEqualTo(modifiedDate);
    }

    @Test
    public void shouldThrowExceptionWhenPersistNotUniqueEmail() {
        //given
        User user = new User();
        user.setEmail("email@o2.pl");

        User user2 = new User();
        user2.setEmail("email@o2.pl");
        //when
        entityManager.persistAndFlush(user);
        try {
            entityManager.persistAndFlush(user2);
            Assert.fail();
        } catch (PersistenceException e) {
            assertThat(e).isNotNull();
        }
    }

    @Test
    public void shouldThrowExceptionWhenPersistNotUniqueUserName() {
        //given
        User user = new User();
        user.setUserName("userName");

        User user2 = new User();
        user2.setUserName("userName");
        //when
        entityManager.persistAndFlush(user);
        try {
            entityManager.persistAndFlush(user2);
            Assert.fail();
        } catch (PersistenceException e) {
            assertThat(e).isNotNull();
        }
    }
}
