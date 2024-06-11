package org.example.demo_review_jwt_security_api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormRegister {
    @NotBlank(message = "username is empty")
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private List<String> roles;
}
