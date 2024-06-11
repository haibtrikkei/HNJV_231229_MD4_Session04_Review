package org.example.demo_review_jwt_security_api.model.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTResponse {
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private Boolean status;
    private Collection<? extends GrantedAuthority> authorities;
    private String token;
}
