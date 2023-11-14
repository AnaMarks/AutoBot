package com.autobots.automanager.controles.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.controles.dtos.CriarFornecedorDTO;
import com.autobots.automanager.controles.dtos.CriarUsuarioDTO;
import com.autobots.automanager.servico.CriarUsuarioServico;

@RestController
@RequestMapping("/auth/criar")
@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
public class CriarUsuariosControle {
  @Autowired
  CriarUsuarioServico criarUsuarioServico;

  @PreAuthorize("hasRole('ADMINISTRADOR')")
  @PostMapping("/admin")
  public ResponseEntity<?> cadastrarAdministrador(@RequestBody CriarUsuarioDTO adminDTO) {
    try {
      criarUsuarioServico.criarAdministrador(adminDTO);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
  @PostMapping("/gerente")
  public ResponseEntity<?> cadastrarGerente(@RequestBody CriarUsuarioDTO gerenteDTO) {
    try {
      criarUsuarioServico.criarGerente(gerenteDTO);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
  @PostMapping("/fornecedor")
  public ResponseEntity<?> cadastrarFornecedor(@RequestBody CriarFornecedorDTO fornecedorDTO) {
    try {
      criarUsuarioServico.criarFornecedor(fornecedorDTO);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
  @PostMapping("/funcionario")
  public ResponseEntity<?> cadastrarFuncionario(@RequestBody CriarUsuarioDTO funcionarioDTO) {
    try {
      criarUsuarioServico.criarFuncionario(funcionarioDTO);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
  @PostMapping("/cliente")
  public ResponseEntity<?> cadastrarCliente(@RequestBody CriarUsuarioDTO clienteDTO) {
    try {
      criarUsuarioServico.criarCliente(clienteDTO);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }
}
