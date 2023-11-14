package com.autobots.automanager.controles;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.controles.dtos.CriarMercadoriaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servico.EmpresaServico;
import com.autobots.automanager.servico.MercadoriaServico;
import com.autobots.automanager.servico.UsuarioServico;

@RestController
@RequestMapping("/mercadoria")
@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('FORNECEDOR')")
public class MercadoriaControle {

  @Autowired
  public MercadoriaServico servicoMercadoria;

  @Autowired
  public EmpresaServico servicoEmpresa;

  @Autowired
  public UsuarioServico servicoUsuario;

  @PostMapping("/criar")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('FORNECEDOR')")
  public ResponseEntity<Mercadoria> cadastrarMercadoria(@RequestBody CriarMercadoriaDTO mercadoria) {
    try {
      if (mercadoria.getRazaoSocial() == null) {
        servicoMercadoria.criarMercadoria(mercadoria, mercadoria.getUsuarioId());
      } else {
        servicoMercadoria.criarMercadoria(mercadoria, mercadoria.getRazaoSocial());
      }

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
  @GetMapping("/{id}")
  public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
    Mercadoria mercadoria = servicoMercadoria.encontrarMercadoria(id);

    if (mercadoria == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
  @GetMapping("/empresa/{idEmpresa}")
  public ResponseEntity<Set<Mercadoria>> obterMercadorias(@PathVariable Long idEmpresa) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(idEmpresa);

    if (empresa == null || empresa.getMercadorias().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Set<Mercadoria>>(empresa.getMercadorias(), HttpStatus.FOUND);
  }

  @PreAuthorize("hasRole('FORNECEDOR')")
  @GetMapping("/fornecedor/{idFornecedor}")
  public ResponseEntity<Set<Mercadoria>> obterMercadoriasPorFornecedor(@PathVariable Long idFornecedor) {
    Usuario usuario = servicoUsuario.encontrarUsuario(idFornecedor);

    if (usuario == null || usuario.getMercadorias().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Set<Mercadoria>>(usuario.getMercadorias(), HttpStatus.FOUND);
  }

  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
  @DeleteMapping("empresa/excluir/{razaoSocial}/{idMercadoria}")
  public ResponseEntity<Mercadoria> excluirMercadoriaEmpresa(
      @PathVariable String razaoSocial,
      @PathVariable Long idMercadoria) {

    try {
      servicoMercadoria.excluirMercadoria(razaoSocial, idMercadoria);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }

  @PreAuthorize("hasRole('FORNECEDOR')")
  @DeleteMapping("fornecedor/excluir/{idFornecedor}/{idMercadoria}")
  public ResponseEntity<Mercadoria> excluirMercadoriaFornecedor(
      @PathVariable Long idFornecedor,
      @PathVariable Long idMercadoria) {

    try {
      servicoMercadoria.excluirMercadoria(idFornecedor, idMercadoria);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }
}
