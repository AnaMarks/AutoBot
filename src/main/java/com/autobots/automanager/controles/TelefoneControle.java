package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class TelefoneControle {
	@Autowired
	private ClienteRepositorio repositorio;

	@Autowired
	private ClienteSelecionador selecionador;

	@Autowired
	private TelefoneSelecionador selecionadorTelefone;
	
	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	

	@GetMapping("/telefone/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
	List<Telefone> telefones = repositorioTelefone.findAll();
	return selecionadorTelefone.selecionar(telefones, id);
	}

	@GetMapping("/telefones")
	public List<Telefone> obterTelefones() {
		List<Telefone> telefones = repositorioTelefone.findAll();
		return telefones;
	}

	@PostMapping("/cadastroTelefone")
	public void cadastrarCliente(@RequestBody Cliente clientes) {
		Cliente cliente = repositorio.getById(clientes.getId());
		cliente.getTelefones().addAll(clientes.getTelefones());
		repositorio.save(cliente);
	}

	@PutMapping("/atualizarTelefone")
	public void atualizarTelefone(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(cliente.getTelefones(), atualizacao.getTelefones());
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluirTelefone/{id}")
	public void excluirTelefone(@PathVariable long id) {
		Telefone telefone = repositorioTelefone.getById(id);
		Cliente cliente = repositorio.findByTelefonesId(telefone.getId());
		cliente.getTelefones().remove(telefone);
		repositorio.delete(cliente);
	}
}
