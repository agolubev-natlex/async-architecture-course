package ru.natlex.authservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.natlex.authservice.config.UserInfo;
import ru.natlex.authservice.entity.UserEntity;
import ru.natlex.authservice.messaging.KafkaProducer;
import ru.natlex.authservice.repository.UserRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService, InitializingBean {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafkaProducer;

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
        kafkaProducer.sendUserMessage(user);
    }

    public UserEntity getByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public void afterPropertiesSet() {
        UserEntity admin = new UserEntity();
        admin.setPasswordHash(passwordEncoder.encode("admin"));
        admin.setUsername("admin");
        admin.setRole(UserEntity.Role.ADMIN);
        UserEntity popug = new UserEntity();
        popug.setPasswordHash(passwordEncoder.encode("popug"));
        popug.setUsername("popug");
        popug.setRole(UserEntity.Role.POPUG);
        UserEntity manager = new UserEntity();
        manager.setPasswordHash(passwordEncoder.encode("manager"));
        manager.setUsername("manager");
        manager.setRole(UserEntity.Role.MANAGER);
        UserEntity accountant = new UserEntity();
        accountant.setPasswordHash(passwordEncoder.encode("accountant"));
        accountant.setUsername("accountant");
        accountant.setRole(UserEntity.Role.ACCOUNTANT);
        userRepository.saveAll(List.of(admin, popug, manager, accountant));
        kafkaProducer.sendUserMessage(admin); // can be batch
        kafkaProducer.sendUserMessage(popug);
        kafkaProducer.sendUserMessage(manager); // can be batch
        kafkaProducer.sendUserMessage(accountant);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
