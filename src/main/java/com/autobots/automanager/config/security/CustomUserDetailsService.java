package com.autobots.automanager.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorio.RepositorioUsuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private RepositorioUsuario repositorioUsuario;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<Usuario> usuario = repositorioUsuario.findByEmail(username);

    if (usuario.isEmpty()) {
      throw new UsernameNotFoundException("User does not exists! " + username);
    }

    return UserDetailsImpl.create(usuario.get());
  }

}
