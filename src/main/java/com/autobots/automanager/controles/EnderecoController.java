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
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.Cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.Endereco.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.Endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.Endereco.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EnderecoController {
    @Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private EnderecoRepositorio repositorioEndereco;
	@Autowired
	private EnderecoSelecionador selecionador;
	@Autowired
	private ClienteSelecionador selecionadorCliente;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;

	
	@GetMapping("/endereco/{id}") 
	public ResponseEntity<?> obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		Endereco endereco = selecionador.selecionar(enderecos, id);
		if (endereco == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco não encontrado.");
		} else {
			adicionadorLink.adicionarLink(endereco);
			return ResponseEntity.ok(endereco);
		}

	}


	@GetMapping("/enderecos") 
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		if (enderecos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enderecos);
		} else {
			adicionadorLink.adicionarLink(enderecos);
			return ResponseEntity.ok(enderecos);
		}
	}

	@PostMapping("/cadastroEndereco") 
	public ResponseEntity<?> cadastrarDocumentos(@RequestBody Cliente cliente) {
		List<Cliente> clientes = repositorioCliente.findAll();
		Cliente clienteEndereco = selecionadorCliente.selecionar(clientes, cliente.getId());
		if (clienteEndereco != null) {
			clienteEndereco.setEndereco(cliente.getEndereco());
			repositorioCliente.save(clienteEndereco);
			return ResponseEntity.ok(clienteEndereco);
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
	} 

	@PutMapping("/atualizarEndereco") 
	public ResponseEntity<?> atualizarDocumentos(@RequestBody Cliente atualizacao) {
        
		List<Cliente> clientes = repositorioCliente.findAll();
		Cliente clienteEndereco = selecionadorCliente.selecionar(clientes, atualizacao.getId());
		if (clienteEndereco != null) {
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(clienteEndereco.getEndereco(), atualizacao.getEndereco());
			repositorioCliente.save(clienteEndereco);
			return ResponseEntity.ok(clienteEndereco);
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
	}

	@DeleteMapping("/excluirEndereco/{id}") 
	public ResponseEntity<?> excluirDocumento(@PathVariable long id) {

		List<Endereco> enderecos = repositorioEndereco.findAll();
		Endereco endereco = selecionador.selecionar(enderecos, id);
		if (endereco != null){
			Cliente cliente = repositorioCliente.findByEnderecoId(id);
			cliente.remove(endereco);
			repositorioCliente.save(cliente);
			return ResponseEntity.ok(cliente.getEndereco());
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco não encontrado.");
		}
	}
}
