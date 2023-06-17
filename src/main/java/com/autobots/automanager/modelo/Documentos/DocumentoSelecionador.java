package com.autobots.automanager.modelo.Documentos;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;

@Component
public class DocumentoSelecionador {
    public Documento selecionar(List<Documento> clientes, long id) {
		Documento selecionado = null;
		for (Documento cliente : clientes) {
			if (cliente.getId() == id) {
				selecionado = cliente;
			}
		}
		return selecionado;
	}
}
