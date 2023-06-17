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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.Cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.Telefone.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.Telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.Telefone.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class TelefoneControle {
    @Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	@Autowired
	private TelefoneSelecionador selecionador;
	@Autowired
	private ClienteSelecionador selecionadorCliente;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	
	@GetMapping("/telefone/{id}") 
	public ResponseEntity<?> obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorioTelefone.findAll();
		Telefone telefone = selecionador.selecionar(telefones, id);
		if (telefone == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Telefone não encontrado.");
		} else {
			adicionadorLink.adicionarLink(telefone);
			return ResponseEntity.ok(telefone);
		}

	}


	@GetMapping("/telefones") 
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefone = repositorioTelefone.findAll();
		if (telefone.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(telefone);
		} else {
			adicionadorLink.adicionarLink(telefone);
			return ResponseEntity.ok(telefone);
		}
	}

	@PostMapping("/cadastroTelefone") 
	public ResponseEntity<?> cadastrarDocumentos(@RequestBody Cliente cliente) {

		List<Cliente> clientes = repositorioCliente.findAll();
		Cliente clienteTelefone = selecionadorCliente.selecionar(clientes, cliente.getId());
		if (clienteTelefone != null) {
			clienteTelefone.getTelefones().addAll(cliente.getTelefones());
			repositorioCliente.save(clienteTelefone);
			return ResponseEntity.ok(clienteTelefone);
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
		
	} 

	@PutMapping("/atualizarTelefone") 
	public ResponseEntity<?> atualizarDocumentos(@RequestBody Cliente atualizacao) {
      
		List<Cliente> clientes = repositorioCliente.findAll();
		Cliente clienteTelefone = selecionadorCliente.selecionar(clientes, atualizacao.getId());
		if (clienteTelefone != null) {
			TelefoneAtualizador atualizador = new TelefoneAtualizador();
			atualizador.atualizar(clienteTelefone.getTelefones(), atualizacao.getTelefones());
			repositorioCliente.save(clienteTelefone);
			return ResponseEntity.ok(clienteTelefone);
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
	}

	@DeleteMapping("/excluirTelefone/{id}") 
	public ResponseEntity<?> excluirDocumento(@PathVariable long id) {

		List<Telefone> telefones = repositorioTelefone.findAll();
		Telefone telefone = selecionador.selecionar(telefones, id);
		if (telefone != null) {
			Cliente cliente = repositorioCliente.findByTelefonesId(id);
			cliente.remove(telefone);
			repositorioCliente.save(cliente);
			return ResponseEntity.ok(cliente.getTelefones());
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
	}
}
