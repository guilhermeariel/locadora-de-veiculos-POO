package servicos;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import repository.ClienteRepositorio;
import utils.ValidationPredicates;

import java.util.function.Consumer;
import java.util.function.Function;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositorio clienteRepositorio;

    // Consumer para log de cadastro de cliente
    private final Consumer<Cliente> imprimirCadastro = c ->
            System.out.printf("Cliente cadastrado: %s (%s)\n", c.getNome(), c.getIdentificador());

    // Consumer para log de busca de cliente
    Consumer<Cliente> imprimirBusca = c ->
            System.out.printf("Cliente encontrado: %s (%s)%n", c.getNome(), c.getIdentificador());

    // Consumer para log de atualizaçao de cliente
    Consumer<Cliente> imprimirAtualizacao = c ->
            System.out.printf("Cliente atualizado: %s (%s)%n", c.getNome(), c.getIdentificador());

    // Consumer para log de remoçao de cliente
    Consumer<Cliente> imprimirRemocao = c ->
            System.out.printf("Cliente removido: %s (%s)%n", c.getNome(), c.getIdentificador());

    public ClienteServiceImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public void cadastrarCliente(String nome, String documento, boolean isPessoaFisica) {
        if (clienteRepositorio.buscarPorIdentificador(documento) != null) {
            throw new IllegalArgumentException("Já existe cliente cadastrado com esse documento.");
        }

        Cliente cliente = getCliente(nome, documento, isPessoaFisica);
        clienteRepositorio.salvar(cliente);

        imprimirCadastro.accept(cliente);
    }

    private Cliente getCliente(String nome, String documento, boolean isPessoaFisica) {
        if (!ValidationPredicates.ehNomeValido(nome)) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        if (!ValidationPredicates.ehDocumentoValido(documento)) {
            throw new IllegalArgumentException(isPessoaFisica ? "CPF inválido." : "CNPJ inválido.");
        }

        // Function para criar o cliente
        Function<String, Cliente> criarCliente = doc -> isPessoaFisica
                ? new PessoaFisica(nome, doc)
                : new PessoaJuridica(nome, doc);

        return criarCliente.apply(documento);
    }

    @Override
    public Cliente buscarClientePorId(String documento) {
        Cliente cliente = clienteRepositorio.buscarPorIdentificador(documento);
        if (cliente == null) throw new IllegalArgumentException("Cliente não encontrado.");

        imprimirBusca.accept(cliente);

        return cliente;
    }

    @Override
    public void atualizarCliente(String documento, String novoNome) {
        Cliente cliente = buscarClientePorId(documento);

        if (!ValidationPredicates.ehNomeValido(novoNome)) throw new IllegalArgumentException("Nome inválido.");

        cliente.setNome(novoNome);
        clienteRepositorio.atualizar(cliente);

        imprimirAtualizacao.accept(cliente);
    }

    @Override
    public void removerCliente(String documento) {
        Cliente cliente = buscarClientePorId(documento);
        clienteRepositorio.removerItem(cliente);

        imprimirRemocao.accept(cliente);
    }
}
