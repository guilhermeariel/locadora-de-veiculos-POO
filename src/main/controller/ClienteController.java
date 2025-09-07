package controller;

import model.Cliente;
import servicos.ClienteServiceImpl;

public class ClienteController {

    private final ClienteServiceImpl clienteService;

    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    public void cadastrarCliente(String nome, String documento, boolean isPessoaFisica) {
        try {
            clienteService.cadastrarCliente(nome, documento, isPessoaFisica);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public void atualizarCliente(String documento, String novoNome) {
        try {
            clienteService.atualizarCliente(documento, novoNome);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void buscarCliente(String documento) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(documento);
            System.out.println("Cliente encontrado: " + cliente.getNome() +
                    " (" + (cliente.isPessoaFisica() ? "Pessoa Física" : "Pessoa Jurídica") + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }
}
