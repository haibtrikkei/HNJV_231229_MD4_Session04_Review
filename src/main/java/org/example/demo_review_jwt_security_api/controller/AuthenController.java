package org.example.demo_review_jwt_security_api.controller;

import org.example.demo_review_jwt_security_api.model.dto.request.FormLogin;
import org.example.demo_review_jwt_security_api.model.dto.request.FormRegister;
import org.example.demo_review_jwt_security_api.model.dto.response.JWTResponse;
import org.example.demo_review_jwt_security_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthenController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody FormRegister formRegister){
        boolean register = userService.register(formRegister);
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    @PostMapping("/public/login")
    public ResponseEntity<JWTResponse> login(@RequestBody FormLogin formLogin){
        //can ham trong UserService de xac thuc username,password
        return new ResponseEntity<>(userService.login(formLogin),HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<String>> userList(){
        return  new ResponseEntity<>(List.of("1","2","3","4"),HttpStatus.OK);
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<String>> adminList(){
        return  new ResponseEntity<>(List.of("11","21","31","41"),HttpStatus.OK);
    }
}
