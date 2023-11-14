package com.autobots.automanager.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findByEmail(String email);

}
