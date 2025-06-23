package dev.trendio_back.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Builder
@Setter @Getter
public class AuthUser implements UserDetails {

    private Long id;
    private String password;
    private String username;
    private boolean enabled;

    private List<SimpleGrantedAuthority> authorities;
}