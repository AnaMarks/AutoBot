package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.controles.dtos.CriarEmpresaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.models.empresa.AdicionadorLinkEmpresa;
import com.autobots.automanager.repositorio.RepositorioEmpresa;
import com.autobots.automanager.servico.EmpresaServico;

@RestController
@RequestMapping("/empresa")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class EmpresaControle {

  @Autowired
  public EmpresaServico servicoEmpresa;

  @Autowired
  RepositorioEmpresa repositorioEmpresa;

  @Autowired
  AdicionadorLinkEmpresa adicionadorLink;

  @PostMapping("/criar")
  public ResponseEntity<Empresa> criarEmpresa(@RequestBody CriarEmpresaDTO empresa) {

    try {
      Empresa novaEmpresa = servicoEmpresa.criarEmpresa(empresa);

      return new ResponseEntity<Empresa>(novaEmpresa, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<Empresa>(HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("/{id}")
  public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(id);

    if (empresa == null) {
      return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
    }
    adicionadorLink.adicionarLink(empresa);
    return new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
  }

  @GetMapping("/")
  public ResponseEntity<List<Empresa>> obterEmpresas() {
    List<Empresa> empresas = repositorioEmpresa.findAll();
    if (empresas.isEmpty()) {
      ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionadorLink.adicionarLink(empresas);
      ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.FOUND);
      return resposta;
    }
  }

  @PutMapping("/atualizar")
  public ResponseEntity<Empresa> atualizar(@RequestBody Empresa empresaAtualizada) {
    try {
      servicoEmpresa.atualizarEmpresa(empresaAtualizada);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("excluir/{id}")
  public ResponseEntity<Empresa> excluir(@PathVariable Long id) {
    try {
      servicoEmpresa.excluirEmpresa(id);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

}
