package com.autobots.automanager.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public class UserDetailsImpl implements UserDetails {

  private String email;
  private String senha;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Usuario usuario) {
    this.email = usuario.getEmail();
    this.senha = usuario.getSenha();

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (PerfilUsuario perfil : usuario.getPerfis()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_".concat(perfil.name())));
    }

    this.authorities = authorities;
  }

  public static UserDetailsImpl create(Usuario usuario) {
    return new UserDetailsImpl(usuario);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
