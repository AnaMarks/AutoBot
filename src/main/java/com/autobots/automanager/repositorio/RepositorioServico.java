package com.autobots.automanager.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Servico;

public interface RepositorioServico extends JpaRepository<Servico, Long> {
}
