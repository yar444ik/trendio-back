package dev.trendio_back.service.auth;

import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.dto.auth.Role;
import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.entity.auth.PasswordEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        );

        return AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPasswordEntity().getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .enabled(user.isEnabled())
                .build();
    }

    @Transactional
    public void createUser(SignInRequest request) {
        if (!userRepository.findByUsername(request.getUsername()).isPresent()) {
            UserEntity entity = UserEntity.builder()
                    .username(request.getUsername())
                    .passwordEntity(new PasswordEntity(request.getPassword()))
                    .enabled(true)
                    .role(Role.User)
                    .build();

            userRepository.save(entity);
        } else {
            throw new UsernameNotFoundException(String.format("User %s not found", request.getUsername()));
        }
    }
}
