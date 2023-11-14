package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorio.RepositorioEmpresa;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    public static void main(String[] args) {
        SpringApplication.run(AutomanagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
        String senhaEncriptografada = toCriptografy.encode("1234");

        Empresa empresa = new Empresa();
        empresa.setNomeFantasia("Car service manutenção veicular");
        empresa.setRazaoSocial("Toyota");
        empresa.setCadastro(new Date());

        Endereco enderecoEmpresa = new Endereco();
        enderecoEmpresa.setEstado("São Paulo");
        enderecoEmpresa.setCidade("São Paulo");
        enderecoEmpresa.setBairro("Centro");
        enderecoEmpresa.setRua("Av. São João");
        enderecoEmpresa.setNumero("00");
        enderecoEmpresa.setCodigoPostal("01035-000");

        empresa.setEndereco(enderecoEmpresa);

        Telefone telefoneEmpresa = new Telefone();
        telefoneEmpresa.setDdd("011");
        telefoneEmpresa.setNumero("986454527");

        empresa.getTelefones().add(telefoneEmpresa);

        Usuario admin = new Usuario();
        admin.setNome("Admin");
        admin.setNomeSocial("admin");
        admin.setEmail("admin@example.com");
        admin.setSenha(senhaEncriptografada);
        admin.getPerfis().add(PerfilUsuario.ADMINISTRADOR);

        Endereco enderecoAdmin = new Endereco();
        enderecoAdmin.setEstado("São Paulo");
        enderecoAdmin.setCidade("São Paulo");
        enderecoAdmin.setBairro("Jardins");
        enderecoAdmin.setRua("Av. São Gabriel");
        enderecoAdmin.setNumero("0011");
        enderecoAdmin.setCodigoPostal("01435-111");

        admin.setEndereco(enderecoAdmin);

        Telefone telefoneAdmin = new Telefone();

		telefoneAdmin.setDdd("011");
		telefoneAdmin.setNumero("986454527");

        Documento cpfAdmin = new Documento();
        cpfAdmin.setTipo(TipoDocumento.CPF);
        cpfAdmin.setNumero("8564731222");
        cpfAdmin.setDataEmissao(new Date());

        admin.getDocumentos().add(cpfAdmin);

        empresa.getUsuarios().add(admin);

        Mercadoria mercadoria = new Mercadoria();
        mercadoria.setNome("Peça de reposição");
        mercadoria.setValor(100.50);

        Servico servico = new Servico();
        servico.setNome("Manutenção preventiva");
        servico.setValor(250.00);

        Veiculo veiculo = new Veiculo();
		veiculo.setTipo(TipoVeiculo.HATCH);
		veiculo.setPlaca("ABC-1234");
		veiculo.setModelo("Gol");
		veiculo.setProprietario(admin);

        empresa.getMercadorias().add(mercadoria);
        empresa.getServicos().add(servico);

        repositorioEmpresa.save(empresa);

		// Testes de consulta

		System.out.println("Empresas: " + empresa.getNomeFantasia());
		System.out.println("Endereço: " + empresa.getEndereco());
		System.out.println("Telefones: " + empresa.getTelefones());
		System.out.println("Usuários: " + empresa.getUsuarios());
		System.out.println("Mercadorias: " + empresa.getMercadorias());
		System.out.println("Serviços: " + empresa.getServicos());
		System.out.println("Veículos: " + admin.getVeiculos());
        System.out.println("Documentos: " + admin.getDocumentos());
    }
}