package com.luizzbsa.carteira.controller;

import com.luizzbsa.carteira.model.dto.CriarUsuarioDTO;
import com.luizzbsa.carteira.model.dto.LoginDto;
import com.luizzbsa.carteira.model.dto.TokenJWTDTO;
import com.luizzbsa.carteira.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    UsuarioService service;
    @Autowired
    public UserController(UsuarioService service) {
        this.service = service;
    }
    @PostMapping
    ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid CriarUsuarioDTO dados){
        service.criarUsuario(dados);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenJWTDTO> autenticarUsuario(@RequestBody @Valid LoginDto loginUserDto) {
        TokenJWTDTO token = service.authenticarUsuario(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
