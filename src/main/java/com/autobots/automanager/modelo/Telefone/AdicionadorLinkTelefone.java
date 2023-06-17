package com.autobots.automanager.modelo.Telefone;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {

	@Override
	public void adicionarLink(List<Telefone> lista) {
		for (Telefone endereco : lista) {
			long id = endereco.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.obterTelefone(id))
					.withSelfRel();
			endereco.add(linkProprio);
    
		}
	}

	@Override
	public void adicionarLink(Telefone objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.obterTelefones())
				.withRel("telefones");
		objeto.add(linkProprio);
	}


}