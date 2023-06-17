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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.Cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.Documentos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.Documentos.DocumentoAtualizador;
import com.autobots.automanager.modelo.Documentos.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
public class DocumentosControle {
    @Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private DocumentoRepositorio repositorioDocumento;
	@Autowired
	private DocumentoSelecionador selecionador;
	@Autowired
	private ClienteSelecionador selecionadorCliente;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;

	@GetMapping("/documento/{id}") 
	public ResponseEntity<?> obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorioDocumento.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		if (documento == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
		} else {
			adicionadorLink.adicionarLink(documento);
			return ResponseEntity.ok(documento);
		}
	}

	@GetMapping("/documentos") 
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = repositorioDocumento.findAll();
		if (documentos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(documentos);
		} else {
			adicionadorLink.adicionarLink(documentos);
			return ResponseEntity.ok(documentos);
		}
	}

	@PostMapping("/cadastroDocumentos") 
	public ResponseEntity<?>  cadastrarDocumentos(@RequestBody Cliente cliente) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente clienteDocumento = selecionadorCliente.selecionar(clientes, cliente.getId());
		if (clienteDocumento != null) {
			clienteDocumento.getDocumentos().addAll(cliente.getDocumentos());
			repositorio.save(clienteDocumento);
			return ResponseEntity.ok(clienteDocumento);
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
	} 

	@PutMapping("/atualizarDocumentos") 
	public ResponseEntity<?> atualizarDocumentos(@RequestBody Cliente atualizacao) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente clienteDocumento = selecionadorCliente.selecionar(clientes, atualizacao.getId());
		if (clienteDocumento != null) {
			DocumentoAtualizador atualizador = new DocumentoAtualizador();
			atualizador.atualizar(clienteDocumento.getDocumentos(), atualizacao.getDocumentos());
			repositorio.save(clienteDocumento);
			return ResponseEntity.ok(clienteDocumento);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
	}
	@DeleteMapping("/excluirDocumento/{id}") 
	public ResponseEntity<?> excluirDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorioDocumento.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		if (documento != null) {
		Cliente cliente = repositorio.findByDocumentosId(id);
        cliente.getDocumentos().remove(documento);
        repositorio.save(cliente);
		return ResponseEntity.ok(cliente.getDocumentos());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
		}
	}
}
