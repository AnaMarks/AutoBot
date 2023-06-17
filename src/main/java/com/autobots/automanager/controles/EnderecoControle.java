package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EnderecoControle {
	@Autowired
	private ClienteRepositorio repositorio;

	@Autowired
	private ClienteSelecionador selecionador;

	@Autowired
	private EnderecoRepositorio repositorioEndereco;
	
	@Autowired
	private EnderecoSelecionador repositorioSelecionador;
	
	@GetMapping("/endereco/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		return repositorioSelecionador.selecionar(enderecos, id);
	}

	@GetMapping("/enderecos")
	public List<Endereco> obterEndereco() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		return enderecos;
	}

	@PutMapping("/atualizarEndereco")
	public void atualizarEndereco(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluirEndereco/{id}")
	public void excluirTelefone(@PathVariable long id) {
		Endereco endereco = repositorioEndereco.getById(id);
		Cliente cliente = repositorio.findByTelefonesId(endereco.getId());
		cliente.getEndereco().remove(endereco);
		repositorio.delete(cliente);
	}
}
