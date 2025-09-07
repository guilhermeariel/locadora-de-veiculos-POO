package controller;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import servicos.AluguelServiceImpl;

public class AluguelController {

    private final AluguelServiceImpl aluguelService;

    public AluguelController(AluguelServiceImpl aluguelService) {
        this.aluguelService = aluguelService;
    }

    public void alugarVeiculo(Cliente cliente, Veiculo veiculo) {
        try {
            aluguelService.alugar(cliente, veiculo);
            System.out.println("Veículo alugado com sucesso para " + cliente.getNome());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao alugar veículo: " + e.getMessage());
        }
    }

    public void devolverVeiculo(Cliente cliente, Veiculo veiculo) {
        try {
            aluguelService.devolver(cliente, veiculo);
            System.out.println("Veículo devolvido com sucesso por " + cliente.getNome());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao devolver veículo: " + e.getMessage());
        }
    }

    public void buscarAluguelPorVeiculo(Veiculo veiculo) {
        try {
            Aluguel aluguel = aluguelService.buscarAluguelPorVeiculo(veiculo);
            System.out.println("Aluguel encontrado: " + aluguel.getCliente().getNome() +
                    " alugou " + veiculo.getModelo());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar aluguel: " + e.getMessage());
        }
    }
}
