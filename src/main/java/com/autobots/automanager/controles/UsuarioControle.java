package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.models.usuario.AdicionadorLinkUsuario;
import com.autobots.automanager.repositorio.RepositorioUsuario;
import com.autobots.automanager.servico.UsuarioServico;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {

  @Autowired
  RepositorioUsuario repositorioUsuario;

  @Autowired
  UsuarioServico servicoUsuario;

  @Autowired
  AdicionadorLinkUsuario adicionadorLink;

  @GetMapping("/")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public ResponseEntity<List<Usuario>> obterUsuarios() {
    List<Usuario> usuarios = repositorioUsuario.findAll();
    if (usuarios.isEmpty()) {
      ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionadorLink.adicionarLink(usuarios);
      ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
      return resposta;
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
    Usuario usuario = servicoUsuario.encontrarUsuario(id);

    if (usuario == null) {
      return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
    }
    adicionadorLink.adicionarLink(usuario);
    return new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
  }

  @PutMapping("/atualizar/{id}")
  @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
  public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
    try {
      servicoUsuario.atualizarUsuario(usuario);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/excluir/{idEmpresa}/{idUsuario}")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public ResponseEntity<Usuario> excluirUsuario(@PathVariable Long idEmpresa, @PathVariable Long idUsuario) {
    try {
      servicoUsuario.excluirUsuario(idEmpresa, idUsuario);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

}
