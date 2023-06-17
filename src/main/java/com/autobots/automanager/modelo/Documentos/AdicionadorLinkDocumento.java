package com.autobots.automanager.modelo.Documentos;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.autobots.automanager.controles.DocumentosControle;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {

	@Override
	public void adicionarLink(List<Documento> lista) {
		for (Documento documento : lista) {
			long id = documento.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentosControle.class)
							.obterDocumento(id))
					.withSelfRel();
			documento.add(linkProprio);
    
		}
	}

	@Override
	public void adicionarLink(Documento objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentosControle.class)
						.obterDocumentos())
				.withRel("documentos");
		objeto.add(linkProprio);
	}


}