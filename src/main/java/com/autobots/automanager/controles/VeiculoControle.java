package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;
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

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorio.RepositorioUsuario;
import com.autobots.automanager.repositorio.RepositorioVeiculo;
import com.autobots.automanager.servico.UsuarioServico;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {

  @Autowired
  private UsuarioServico servicoUsuario;

  @Autowired
  private RepositorioVeiculo repositorioVeiculo;

  @Autowired
  private RepositorioUsuario repositorioUsuario;

  @PostMapping("/cadastrar/{idUsuario}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
  public ResponseEntity<?> criarVeiculo(@RequestBody List<Veiculo> veiculos, @PathVariable Long idUsuario) {
    try {
      servicoUsuario.cadastrarVeiculo(veiculos, idUsuario);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
  public ResponseEntity<List<Veiculo>> obterVeiculos() {
    List<Veiculo> veiculos = repositorioVeiculo.findAll();

    if (veiculos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<List<Veiculo>>(veiculos, HttpStatus.FOUND);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
  public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long id) {
    Optional<Veiculo> veiculo = repositorioVeiculo.findById(id);

    if (veiculo.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Veiculo>(veiculo.get(), HttpStatus.FOUND);
  }

  @GetMapping("/usuario/{id}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
  public ResponseEntity<Set<Veiculo>> obterVeiculosUsuario(@PathVariable Long id) {
    Usuario usuario = servicoUsuario.encontrarUsuario(id);

    if (usuario == null || usuario.getVeiculos().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Set<Veiculo>>(usuario.getVeiculos(), HttpStatus.FOUND);
  }

  @DeleteMapping("/excluir/{idUsuario}/{placa}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
  public ResponseEntity<?> excluirVeiculo(@PathVariable Long idUsuario, @PathVariable String placa) {
    Usuario usuario = servicoUsuario.encontrarUsuario(idUsuario);

    Veiculo veiculo = servicoUsuario.encontrarVeiculo(usuario, placa);

    if (usuario == null || veiculo == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    try {
      usuario.getVeiculos().remove(veiculo);
      repositorioVeiculo.deleteById(veiculo.getId());
      repositorioUsuario.save(usuario);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
