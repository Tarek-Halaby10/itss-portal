package com.itss.portal.authentication.security;

import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                  .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = u.getRole().getPermissions().stream()
            .map(p -> new SimpleGrantedAuthority(p.getName()))
            .collect(Collectors.toList());

        // explicitly use Spring Security's User class:
        return new org.springframework.security.core.userdetails.User(
            u.getUsername(),
            u.getPasswordHash(),
            u.isEnabled(),   // enabled
            true,            // accountNonExpired
            true,            // credentialsNonExpired
            true,            // accountNonLocked
            authorities
        );
    }
}
