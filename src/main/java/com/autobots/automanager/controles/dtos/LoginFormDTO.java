package com.autobots.automanager.controles.dtos;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Setter;

@Setter
public class LoginFormDTO {
  private String email;
  private String senha;

  public UsernamePasswordAuthenticationToken converter() {
    return new UsernamePasswordAuthenticationToken(email, senha);
  }
}
