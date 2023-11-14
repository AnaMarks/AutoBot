package com.autobots.automanager.servico;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorio.RepositorioEmpresa;
import com.autobots.automanager.repositorio.RepositorioServico;

@Service
public class ServicoEmpServico {

  @Autowired
  RepositorioServico repositorioServico;

  @Autowired
  RepositorioEmpresa repositorioEmpresa;

  @Autowired
  EmpresaServico servicoEmpresa;

  @Autowired
  UsuarioServico servicoUsuario;

  public void criarServico(Servico servico, String razaoSocial) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(razaoSocial);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    empresa.getServicos().add(servico);

    repositorioEmpresa.save(empresa);
  }

  public Servico encontrarServico(Long id) {
    Optional<Servico> servico = repositorioServico.findById(id);

    if (servico.isEmpty()) {
      return null;
    }

    return servico.get();
  }

  public void excluirServico(String razaoSocial, Long idServico) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(razaoSocial);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    Set<Servico> servicos = empresa.getServicos();

    Servico servico = null;
    for (Servico servicoIterado : servicos) {
      if (servicoIterado.getId() == idServico) {
        servico = servicoIterado;
      }
    }

    servicos.remove(servico);
    repositorioServico.deleteById(servico.getId());
    repositorioEmpresa.save(empresa);
  }
}
