package com.autobots.automanager.controles.dtos;

import com.autobots.automanager.entidades.Usuario;

import lombok.Data;

@Data
public class TokenDTO {
  private String token;
  private String type;
  private UsuarioLoginDTO usuario;

  public TokenDTO(String token, String type, Usuario usuario) {
    this.token = token;
    this.type = type;

    this.usuario = UsuarioLoginDTO.create(usuario);
  }
}
