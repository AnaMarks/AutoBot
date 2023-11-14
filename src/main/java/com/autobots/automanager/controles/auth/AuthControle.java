package com.autobots.automanager.controles.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.config.security.token.TokenService;
import com.autobots.automanager.controles.dtos.LoginFormDTO;
import com.autobots.automanager.controles.dtos.TokenDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorio.RepositorioUsuario;

@RestController
@RequestMapping("/auth")
public class AuthControle {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private RepositorioUsuario repositorioUsuario;

  @PostMapping("/authentication")
  public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginFormDTO form) {
    UsernamePasswordAuthenticationToken payloadLogin = form.converter();

    try {
      Authentication authentication = authManager.authenticate(payloadLogin);

      Optional<Usuario> usuario = repositorioUsuario.findByEmail(authentication.getName());

      String token = tokenService.generateToken(authentication.getName());

      return ResponseEntity.ok(new TokenDTO(token, "Bearer", usuario.get()));
    } catch (AuthenticationException e) {
      return new ResponseEntity<TokenDTO>(HttpStatus.UNAUTHORIZED);
    }
  }
}
