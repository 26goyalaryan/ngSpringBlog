package com.project.springBlog.Controller;

import com.project.springBlog.Service.AuthService;
import com.project.springBlog.dto.LoginRequest;
import com.project.springBlog.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/get")
    public void hello(){
        System.out.println("hello, get it working");
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User SigneUp Successfully",HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);
    }
}
