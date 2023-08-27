package ru.natlex.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.natlex.authservice.config.UserInfo;
import ru.natlex.authservice.entity.Role;
import ru.natlex.authservice.entity.UserEntity;
import ru.natlex.authservice.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService, InitializingBean {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return new UserInfo(user, List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }

    public void createUser(UserEntity user) {
        String password = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(password);
        userRepository.save(user);
        //todo send event to kafka
    }

    public UserEntity getByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetails loadUserByPublicId(String userPublicId) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserEntity admin = new UserEntity();
        admin.setPasswordHash(passwordEncoder.encode("admin"));
        admin.setUsername("admin");
        admin.setRole(Role.ADMIN);
        UserEntity popug = new UserEntity();
        popug.setPasswordHash(passwordEncoder.encode("popug"));
        popug.setUsername("popug");
        popug.setRole(Role.POPUG);
        userRepository.saveAll(List.of(admin, popug));
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
