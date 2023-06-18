package com.autobots.automanager.cadastradores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

@Service
public class CadastradorVeiculo {
    @Autowired
    VeiculoRepositorio veiculoRepositorio;

    public Veiculo cadastrar(Veiculo veiculo) {
        Veiculo veiculoCriado = veiculoRepositorio.save(veiculo);
        return veiculoCriado;}
}