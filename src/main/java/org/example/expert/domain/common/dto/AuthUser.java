package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserRole userRole;

    public AuthUser(Long id, String email, String nickname, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()-> "ROLE_" + userRole.name());
    }
}
