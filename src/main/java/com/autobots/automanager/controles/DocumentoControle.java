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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class DocumentoControle {
	@Autowired
	private ClienteRepositorio repositorio;

	@Autowired
	private DocumentoSelecionador selecionador;
	
	@Autowired
	private DocumentoRepositorio repositorioDocumento;

	@GetMapping("/documento/{id}")
	public Documento obterDocumento(@PathVariable long id) {
	List<Documento> documentos = repositorioDocumento.findAll();
	return selecionador.selecionar(documentos, id);
	}

	@GetMapping("/documentos")
	public List<Documento> obterDocumentos() {
		List<Documento> documentos = repositorioDocumento.findAll();
		return documentos;
	}

	@PostMapping("/cadastroDocumentos")
	public void cadastrarCliente(@RequestBody Cliente clientes) {
		Cliente cliente = repositorio.getById(clientes.getId());
		cliente.getDocumentos().addAll(clientes.getDocumentos());
		repositorio.save(cliente);
	}

	@PutMapping("/atualizarDocumentos")
	public void atualizarDocumentos(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(cliente.getDocumentos(), atualizacao.getDocumentos());
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluirDocumento/{id}")
	public void excluirTelefone(@PathVariable long id) {
		Documento documento = repositorioDocumento.getById(id);
		Cliente cliente = repositorio.findByTelefonesId(documento.getId());
		cliente.getDocumentos().remove(documento);
		repositorio.delete(cliente);
	}
}
