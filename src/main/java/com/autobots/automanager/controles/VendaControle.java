package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorio.RepositorioVenda;
import com.autobots.automanager.servico.VendaServico;

@RestController
@RequestMapping("/vendas")
public class VendaControle {

  @Autowired
  private VendaServico servicoVenda;

  @Autowired
  private RepositorioVenda repositorioVenda;

  @PostMapping("/cadastrar/{idEmpresa}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
  public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda, @PathVariable Long idEmpresa) {
    try {
      servicoVenda.criarVenda(venda, idEmpresa);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public ResponseEntity<List<Venda>> obterVendas() {
    List<Venda> vendas = repositorioVenda.findAll();

    if (vendas.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(vendas, HttpStatus.FOUND);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
  public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
    Venda venda = servicoVenda.encontrarVenda(id);

    if (venda == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
  }

  @GetMapping("empresa/{idEmpresa}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
  public ResponseEntity<Set<Venda>> obterVendasEmpresa(@PathVariable Long idEmpresa) {
    Set<Venda> vendas = servicoVenda.encontrarVendaEmpresa(idEmpresa);

    if (vendas.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Set<Venda>>(vendas, HttpStatus.FOUND);
  }
}
