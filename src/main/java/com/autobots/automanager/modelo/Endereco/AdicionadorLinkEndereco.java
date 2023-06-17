package com.autobots.automanager.modelo.Endereco;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.autobots.automanager.controles.EnderecoController;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {

	@Override
	public void adicionarLink(List<Endereco> lista) {
		for (Endereco endereco : lista) {
			long id = endereco.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoController.class)
							.obterEndereco(id))
					.withSelfRel();
			endereco.add(linkProprio);
    
		}
	}

	@Override
	public void adicionarLink(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoController.class)
						.obterEnderecos())
				.withRel("enderecos");
		objeto.add(linkProprio);
	}


}