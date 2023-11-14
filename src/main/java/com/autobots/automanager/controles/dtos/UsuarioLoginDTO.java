package com.autobots.automanager.controles.dtos;

import java.util.Set;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;

@Data
public class UsuarioLoginDTO {
  private Long id;
  private String email;
  private String nome;
  private String nomeSocial;
  private Set<PerfilUsuario> perfis;

  public UsuarioLoginDTO(Usuario usuario) {
    this.id = usuario.getId();
    this.email = usuario.getEmail();
    this.nome = usuario.getNome();
    this.nomeSocial = usuario.getNomeSocial();
    this.perfis = usuario.getPerfis();
  }

  public static UsuarioLoginDTO create(Usuario usuario) {
    return new UsuarioLoginDTO(usuario);
  }
}
