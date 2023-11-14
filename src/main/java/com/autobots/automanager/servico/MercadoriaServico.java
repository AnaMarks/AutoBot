package com.autobots.automanager.servico;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.dtos.CriarMercadoriaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorio.RepositorioEmpresa;
import com.autobots.automanager.repositorio.RepositorioMercadoria;
import com.autobots.automanager.repositorio.RepositorioUsuario;

@Service
public class MercadoriaServico {

  @Autowired
  RepositorioEmpresa repositorioEmpresa;

  @Autowired
  RepositorioUsuario repositorioUsuario;

  @Autowired
  RepositorioMercadoria repositorioMercadoria;

  @Autowired
  EmpresaServico servicoEmpresa;

  @Autowired
  UsuarioServico servicoUsuario;

  public void criarMercadoria(CriarMercadoriaDTO mercadoriaDTO, String razaoSocial) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(razaoSocial);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    mercadoriaDTO.mercadoria.setCadastro(new Date());
    mercadoriaDTO.mercadoria.setFabricao(new Date(mercadoriaDTO.dataFabricacaoEmTexto));
    mercadoriaDTO.mercadoria.setValidade(new Date(mercadoriaDTO.dataValidadeEmTexto));

    empresa.getMercadorias().add(mercadoriaDTO.mercadoria);

    repositorioEmpresa.save(empresa);
  }

  public void criarMercadoria(CriarMercadoriaDTO mercadoriaDTO, Long fornecedorId) {
    Usuario usuario = servicoUsuario.encontrarUsuario(fornecedorId);

    if (usuario == null) {
      new Exception("Não foi possível encontrar fornecedor, tente novamente");
    }

    mercadoriaDTO.mercadoria.setCadastro(new Date());
    mercadoriaDTO.mercadoria.setFabricao(new Date(mercadoriaDTO.dataFabricacaoEmTexto));
    mercadoriaDTO.mercadoria.setValidade(new Date(mercadoriaDTO.dataValidadeEmTexto));

    usuario.getMercadorias().add(mercadoriaDTO.mercadoria);

    repositorioUsuario.save(usuario);
  }

  public Mercadoria encontrarMercadoria(Long id) {
    Optional<Mercadoria> mercadoria = repositorioMercadoria.findById(id);

    if (mercadoria.isEmpty()) {
      return null;
    }
    return mercadoria.get();
  }

  public void excluirMercadoria(String razaoSocial, Long idMercadoria) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(razaoSocial);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    Set<Mercadoria> mercadorias = empresa.getMercadorias();

    Mercadoria mercadoria = null;
    for (Mercadoria mercadoriaIterada : mercadorias) {
      if (mercadoriaIterada.getId() == idMercadoria) {
        mercadoria = mercadoriaIterada;
      }
    }

    mercadorias.remove(mercadoria);
    repositorioMercadoria.deleteById(mercadoria.getId());
    repositorioEmpresa.save(empresa);
  }

  public void excluirMercadoria(Long idFornecedor, Long idMercadoria) {
    Usuario usuario = servicoUsuario.encontrarUsuario(idFornecedor);

    if (usuario == null) {
      new Exception("Não foi possível encontrar fornecedor, tente novamente");
    }

    Set<Mercadoria> mercadorias = usuario.getMercadorias();

    Mercadoria mercadoria = null;
    for (Mercadoria mercadoriaIterada : mercadorias) {
      if (mercadoriaIterada.getId() == idMercadoria) {
        mercadoria = mercadoriaIterada;
      }
    }

    mercadorias.remove(mercadoria);
    repositorioMercadoria.deleteById(mercadoria.getId());
    repositorioUsuario.save(usuario);
  }
}
