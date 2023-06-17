package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.Cliente.AdicionadorLinkCliente;
import com.autobots.automanager.modelo.Cliente.ClienteAtualizador;
import com.autobots.automanager.modelo.Cliente.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;

	@Autowired
	private ClienteSelecionador selecionador;
	
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> obterCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, id);
		if (cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		} else {
			adicionadorLink.adicionarLink(cliente);
			return ResponseEntity.ok(cliente);
		}
	}

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> obterClientes() {

		List<Cliente> clientes = repositorio.findAll();
		if (clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clientes);
		} else {
			adicionadorLink.adicionarLink(clientes);
			return ResponseEntity.ok(clientes);
		}
	}

	@PostMapping("/cadastro")
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		repositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarCliente(@RequestBody Cliente atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, atualizacao.getId());
		
		if (cliente != null) {
			ClienteAtualizador atualizador = new ClienteAtualizador();
			atualizador.atualizar(cliente, atualizacao);
			repositorio.save(cliente);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirCliente(@PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, id);
		if (cliente != null) {
			repositorio.delete(cliente);
			status = HttpStatus.OK;
		} else{
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
}
