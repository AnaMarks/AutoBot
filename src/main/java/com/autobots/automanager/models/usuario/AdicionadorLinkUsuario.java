package com.autobots.automanager.models.usuario;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.models.AdicionadorLink;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario> {

  @Override
  public void adicionarLink(List<Usuario> lista) {
    for (Usuario empresa : lista) {
      long id = empresa.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder
              .methodOn(UsuarioControle.class)
              .obterUsuario(id))
          .withSelfRel();
      empresa.add(linkProprio);
    }

  }

  @Override
  public void adicionarLink(Usuario usuario) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioControle.class)
            .obterUsuarios())
        .withRel("usuarios");
    usuario.add(linkProprio);
  }

}
