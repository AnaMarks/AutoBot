package com.autobots.automanager.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Empresa;

public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {

  Optional<Empresa> findByRazaoSocial(String razaoSocial);

}