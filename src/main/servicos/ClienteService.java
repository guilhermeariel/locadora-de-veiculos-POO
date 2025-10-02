package servicos;

import model.Cliente;

public interface ClienteService {

    void cadastrarCliente(String nome, String documento, boolean isPessoaFisica);
    Cliente buscarClientePorId(String documento);
    void atualizarCliente(String documento, String novoNome);
    void removerCliente(String documento);
}
