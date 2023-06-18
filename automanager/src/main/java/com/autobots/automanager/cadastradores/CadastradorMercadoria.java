package com.autobots.automanager.cadastradores;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

public class CadastradorMercadoria {
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario cadastroMercadoria(Usuario usuario, Mercadoria mercadoria) {
        Set<Mercadoria> listaMercadorias = usuario.getMercadorias();
        listaMercadorias.add(mercadoria);
        usuario.setMercadorias(listaMercadorias);
        mercadoriaRepositorio.save(mercadoria);
        return usuarioRepositorio.save(usuario);}

}