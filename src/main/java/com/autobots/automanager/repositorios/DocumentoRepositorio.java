package com.autobots.automanager.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
    
}