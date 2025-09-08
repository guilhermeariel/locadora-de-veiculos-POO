package servicos;

import java.util.List;
import model.Cliente;
import model.Veiculo;

public interface ClienteService {

    void cadastrarCliente(String nome, String documento, boolean isPessoaFisica);
    void devolver(Cliente cliente, Veiculo veiculo);
    Cliente buscarClientePorId(String documento);
    void atualizarCliente(String documento, String novoNome);
    List<Cliente> listarClientes();
}
