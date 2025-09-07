package servicos;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import repository.ClienteRepositorio;
import utils.Validator;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteServiceImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public void cadastrarCliente(String nome, String documento, boolean isPessoaFisica) {
        Cliente cliente;

        if (clienteRepositorio.buscarPorIdentificador(documento) != null) {
            throw new IllegalArgumentException("Já existe cliente cadastrado com esse documento.");
        }

        if (!Validator.validarNome(nome)) {
            throw new IllegalArgumentException("Nome inválido");
        }

        if (isPessoaFisica) {
            cliente = new PessoaFisica(nome, documento);
            if (!Validator.validarCPF(documento)) {
                throw new IllegalArgumentException("CPF inválido.");
            }
        } else {
            cliente = new PessoaJuridica(nome, documento);
            if (!Validator.validarCNPJ(documento)) {
                throw new IllegalArgumentException("CNPJ inválido.");
            }
        }

        clienteRepositorio.salvar(cliente);
    }

    @Override
    public Cliente buscarClientePorId(String documento) {
        Cliente cliente = clienteRepositorio.buscarPorIdentificador(documento);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        return cliente;
    }

    @Override
    public void atualizarCliente(String documento, String novoNome) {
        Cliente cliente = clienteRepositorio.buscarPorIdentificador(documento);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        if (!Validator.validarNome(novoNome)) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        cliente.setNome(novoNome);

        clienteRepositorio.atualizar(cliente);
    }
}
