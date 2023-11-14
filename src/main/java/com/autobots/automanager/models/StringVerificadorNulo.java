package com.autobots.automanager.models;

import java.util.Date;
import java.util.Set;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;

public class StringVerificadorNulo {

	public boolean verificar(String dado) {
		boolean nulo = true;
		if (!(dado == null)) {
			if (!dado.isBlank()) {
				nulo = false;
			}
		}
		return nulo;
	}

	public boolean verificar(TipoDocumento tipo) {
		boolean nulo = true;
		if (!(tipo == null)) {
			nulo = false;
		}
		return nulo;
	}

	public boolean verificar(TipoVeiculo tipo) {
		boolean nulo = true;
		if (!(tipo == null)) {
			nulo = false;
		}
		return nulo;
	}

	public boolean verificar(Endereco endereco) {
		boolean nulo = true;
		if (!(endereco == null)) {
			nulo = false;
		}
		return nulo;
	}

	public boolean verificar(Set<?> tipo) {
		boolean nulo = true;
		if (!(tipo == null)) {
			nulo = false;
		}
		return nulo;
	}

	public boolean verificar(Usuario proprietario) {
		boolean nulo = true;
		if (!(proprietario == null)) {
			nulo = false;
		}
		return nulo;
	}

	public boolean verificar(Date cadastro) {
		boolean nulo = true;
		if (!(cadastro == null)) {
			nulo = false;
		}
		return nulo;
	}
}