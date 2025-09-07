package servicos;

import java.util.List;
import model.Cliente;

public interface ClienteService {

    void cadastrarCliente(String nome, String documento, boolean isPessoaFisica);
    Cliente buscarClientePorId(String documento);
    void atualizarCliente(String documento, String novoNome);
    List<Cliente> listarClientes();
}
