package com.autobots.automanager.servico;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.dtos.CriarFornecedorDTO;
import com.autobots.automanager.controles.dtos.CriarUsuarioDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorio.RepositorioEmpresa;
import com.autobots.automanager.repositorio.RepositorioUsuario;

@Service
public class CriarUsuarioServico {

  @Autowired
  private RepositorioEmpresa repositorioEmpresa;

  @Autowired
  private RepositorioUsuario repositorioUsuario;

  @Autowired
  private EmpresaServico servicoEmpresa;

  public CriarUsuarioDTO criarUsuario(CriarUsuarioDTO usuarioDTO, PerfilUsuario perfilUsuario) {
    BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
    String senhaEncriptografa = toCriptografy.encode(usuarioDTO.getUsuario().getSenha());

    usuarioDTO.getUsuario().setSenha(senhaEncriptografa);
    usuarioDTO.getUsuario().getPerfis().add(perfilUsuario);

    usuarioDTO.getUsuario().setEndereco(usuarioDTO.getEndereco());

    usuarioDTO.getTelefones().forEach(novoTelefone -> {
      usuarioDTO.getUsuario().getTelefones().add(novoTelefone);
    });

    usuarioDTO.getDocumentos().forEach(novoDocumento -> {
      Date dataEmissao = new Date();
      novoDocumento.setDataEmissao(dataEmissao);
      usuarioDTO.getUsuario().getDocumentos().add(novoDocumento);
    });

    return usuarioDTO;
  }

  public void criarAdministrador(CriarUsuarioDTO adminDTO) {
    Empresa empresa = encontrarEmpresa(adminDTO.getRazaoSocial());

    CriarUsuarioDTO novoUsuario = criarUsuario(adminDTO, PerfilUsuario.ADMINISTRADOR);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void criarGerente(CriarUsuarioDTO adminDTO) {
    Empresa empresa = encontrarEmpresa(adminDTO.getRazaoSocial());

    CriarUsuarioDTO novoUsuario = criarUsuario(adminDTO, PerfilUsuario.GERENTE);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void criarFuncionario(CriarUsuarioDTO funcionarioDTO) {
    Empresa empresa = encontrarEmpresa(funcionarioDTO.getRazaoSocial());

    CriarUsuarioDTO novoUsuario = criarUsuario(funcionarioDTO, PerfilUsuario.VENDEDOR);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void criarFornecedor(CriarFornecedorDTO fornecedorDTO) {
    Empresa empresa = encontrarEmpresa(fornecedorDTO.getRazaoSocial());

    CriarFornecedorDTO novoUsuario = (CriarFornecedorDTO) criarUsuario(fornecedorDTO, PerfilUsuario.FORNECEDOR);

    novoUsuario.getMercadorias().forEach(novaMercadoria -> {
      novaMercadoria.setCadastro(new Date());
      novaMercadoria.setFabricao(new Date());
      novaMercadoria.setValidade(new Date());

      novoUsuario.getUsuario().getMercadorias().add(novaMercadoria);
    });

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void criarCliente(CriarUsuarioDTO clienteDTO) {
    Empresa empresa = encontrarEmpresa(clienteDTO.getRazaoSocial());

    CriarUsuarioDTO novoUsuario = criarUsuario(clienteDTO, PerfilUsuario.CLIENTE);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public Empresa encontrarEmpresa(String razaoSocial) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(razaoSocial);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    return empresa;
  }

}
