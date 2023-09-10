package com.sensor.repository;

import com.sensor.config.TestConfig;
import com.sensor.entity.AppUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void checkIfUsernameISExist() {

        // given
        String username = "shefoo";
        String password = "123";
        AppUser user = new AppUser(username, password);
        userRepository.save(user);

        // when
        AppUser expected = userRepository.findByUsername(username);

        // then
        assertThat(expected).isNotNull();
    }

    @Test
    void checkIfUsernameDoesNotExist() {

        // given
        String username = "shefoo";

        // when
        AppUser expected = userRepository.findByUsername(username);

        // then
        assertThat(expected).isNull();
    }
}