package com.autobots.automanager.servico;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.dtos.CriarEmpresaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.models.empresa.AtualizadorEmpresa;
import com.autobots.automanager.repositorio.RepositorioEmpresa;

@Service
public class EmpresaServico {

  @Autowired
  private RepositorioEmpresa repositorioEmpresa;

  public Empresa criarEmpresa(CriarEmpresaDTO empresaDTO) {
    empresaDTO.getEmpresa().setCadastro(new Date());
    empresaDTO.getEmpresa().setEndereco(empresaDTO.getEndereco());

    empresaDTO.getTelefones().forEach(novoTelefone -> {
      empresaDTO.getEmpresa().getTelefones().add(novoTelefone);
    });

    Empresa novaEmpresa = repositorioEmpresa.save(empresaDTO.getEmpresa());
    return novaEmpresa;
  }

  public Empresa encontrarEmpresa(String razaoSocial) {
    Optional<Empresa> empresa = repositorioEmpresa.findByRazaoSocial(razaoSocial);

    if (empresa.isEmpty()) {
      return null;
    }
    return empresa.get();
  }

  public Empresa encontrarEmpresa(Long idEmpresa) {
    Optional<Empresa> empresa = repositorioEmpresa.findById(idEmpresa);

    if (empresa.isEmpty()) {
      return null;
    }
    return empresa.get();
  }

  public void atualizarEmpresa(Empresa empresaAtualizada) {
    Empresa empresa = encontrarEmpresa(empresaAtualizada.getId());

    if (empresa == null) {
      new Exception("Não foi possível localizar essa empresa");
    }

    AtualizadorEmpresa atualizador = new AtualizadorEmpresa();
    atualizador.atualizar(empresa, empresaAtualizada);

    repositorioEmpresa.save(empresa);
  }

  public void excluirEmpresa(Long id) {
    Empresa empresa = encontrarEmpresa(id);

    if (empresa == null) {
      new Exception("Não foi possível localizar essa empresa");
    }

    repositorioEmpresa.deleteById(empresa.getId());
  }
}
