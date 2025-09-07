package servicos;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import repository.ClienteRepositorio;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteServiceImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public void cadastrarCliente(String nome, String documento, boolean isPessoaFisica) {
        Cliente cliente = isPessoaFisica ?
            new PessoaFisica(nome, documento) :
            new PessoaJuridica(nome, documento);

        clienteRepositorio.salvar(cliente);
    }

    @Override
    public Cliente buscarClientePorId(String documento) {
        return clienteRepositorio.buscarPorIdentificador(documento);
    }

    @Override
    public void atualizarCliente(String documento, String novoNome) {
        Cliente cliente = buscarClientePorId(documento);
        if (cliente != null) {
            cliente.setNome(novoNome);
            clienteRepositorio.atualizar(cliente);
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepositorio.listar(); // 👈 Aqui está a implementação
    }
}
