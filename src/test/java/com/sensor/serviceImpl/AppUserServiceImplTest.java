package com.sensor.serviceImpl;

import com.sensor.entity.AppUser;
import com.sensor.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AppUserServiceImpl appUserService;


    @Test
    public void addUser() {

        // given
        String username = "shefoo";
        String password = "123";
        String encodedPassword = "encodedPassword";
        AppUser user = new AppUser(username, password);

        // when
        when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn(encodedPassword);
        when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(user);
        AppUser expected = appUserService.addUser(user);

        // then
        assertThat(expected).isNotNull();
        assertThat(expected.getUsername()).isEqualTo("shefoo");
    }

    @Test
    void findUserById() {

        // given
        String username = "shefoo";
        String password = "123";
        Long id = 1L;
        AppUser user = new AppUser(id,username, password);

        // when
        when(appUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        AppUser expected = appUserService.findUserById(1L);

        // then
        assertThat(expected).isNotNull();
        assertThat(expected.getUsername()).isEqualTo("shefoo");
    }

    @Test
    void findByUserName() {

        // given
        String username = "shefoo";
        String password = "123";
        AppUser user = new AppUser(username, password);

        // when
        when(appUserRepository.findByUsername(username)).thenReturn(user);
        AppUser expected = appUserService.findByUserName(username);

        // then
        assertThat(expected).isNotNull();
        assertThat(expected.getUsername()).isEqualTo("shefoo");
    }

    @Test
    void getAllUsers() {
        // given
        AppUser user = new AppUser("shefoo", "123");

        // when
        when(appUserRepository.findAll()).thenReturn(Arrays.asList(user));
        List<AppUser> expected = appUserService.getAllUsers();

        //then
        assertThat(expected).asList().isNotEmpty();
    }
}