package com.autobots.automanager.controles.dtos;

import java.util.List;

import com.autobots.automanager.entidades.Mercadoria;

import lombok.Data;

@Data
public class CriarFornecedorDTO extends CriarUsuarioDTO {
  List<Mercadoria> mercadorias;
}
