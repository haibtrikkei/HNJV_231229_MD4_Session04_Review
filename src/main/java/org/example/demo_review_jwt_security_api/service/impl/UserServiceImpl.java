package org.example.demo_review_jwt_security_api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.demo_review_jwt_security_api.model.dto.request.FormLogin;
import org.example.demo_review_jwt_security_api.model.dto.request.FormRegister;
import org.example.demo_review_jwt_security_api.model.dto.response.JWTResponse;
import org.example.demo_review_jwt_security_api.model.entity.Role;
import org.example.demo_review_jwt_security_api.model.entity.User;
import org.example.demo_review_jwt_security_api.repository.RoleRepository;
import org.example.demo_review_jwt_security_api.repository.UserRepository;
import org.example.demo_review_jwt_security_api.security.jwt.JWTProvider;
import org.example.demo_review_jwt_security_api.security.principals.CustomUserDetail;
import org.example.demo_review_jwt_security_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public boolean register(FormRegister formRegister) {
        //chuyen FormRegister ve User de save vao database
        User user = User.builder()
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .fullName(formRegister.getFullName())
                .email(formRegister.getEmail())
                .phone(formRegister.getPhone())
                .status(true)
                .build();

        List<Role> roles = new ArrayList<>();
        if(formRegister.getRoles()!=null && !formRegister.getRoles().isEmpty()){
            formRegister.getRoles().forEach(role -> {
                switch (role){
                    case "ROLE_ADMIN":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()-> new NoSuchElementException("Khong ton tai role admin")));
                        break;
                    case "ROLE_USER":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()-> new NoSuchElementException("Khong ton tai role user")));
                        break;
                    case "ROLE_MODERATOR":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()-> new NoSuchElementException("Khong ton tai role moderator")));
                        break;
                }
            });
        }else{
            roles.add(roleRepository.findRoleByRoleName("ROLE_USER").orElseThrow(()-> new NoSuchElementException("Khong ton tai role user")));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponse login(FormLogin formLogin) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(),formLogin.getPassword()));
        }catch (AuthenticationException e){
            log.error("Sai username hoac password");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        //Tao token tu userDetail
        String token = jwtProvider.createToken(userDetail);

        return JWTResponse.builder()
                .fullName(userDetail.getFullName())
                .address(userDetail.getAddress())
                .email(userDetail.getEmail())
                .phone(userDetail.getPhone())
                .status(userDetail.getStatus())
                .authorities(userDetail.getAuthorities())
                .token(token)
                .build();
    }
}
