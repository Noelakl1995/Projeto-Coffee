package br.edu.uepb.coffee.controllers;

import br.edu.uepb.coffee.services.AuthorizationService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uepb.coffee.dtos.AuthenticationDTO;
import br.edu.uepb.coffee.dtos.RegisterDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
   
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDto){
        return authorizationService.login(authenticationDto);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody RegisterDTO registerDto){
        return authorizationService.register(registerDto);
    }
}
