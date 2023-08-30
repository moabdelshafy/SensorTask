package com.sensor.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sensor.config.JwtConfig;
import com.sensor.entity.AppUser;
import com.sensor.exceptions.SensorTaskException;
import com.sensor.repository.AppUserRepository;
import com.sensor.service.AppUserService;

@Service
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user == null || !encoder.matches(jwtConfig.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("SENSOR1001");
        }

        return user;
    }

    @CacheEvict(value = {"findUserById", "findByUserName", "getAllUsers"}, allEntries = true)
    @Override
    public AppUser addUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    @Cacheable(cacheNames = "findUserById", unless = "#result == null")
    @Override
    public AppUser findUserById(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new SensorTaskException("SENSOR1000", new Object[]{id}));
        return user;
    }

    @Cacheable(cacheNames = "findByUserName", unless = "#result == null")
    @Override
    public AppUser findByUserName(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) {
            throw new SensorTaskException("SENSOR1000", new Object[]{username});
        }
        return user;
    }

    @Cacheable(cacheNames = "getAllUsers", unless = "#result == null")
    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

}
