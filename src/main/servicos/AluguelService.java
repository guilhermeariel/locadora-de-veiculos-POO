package servicos;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;

public interface AluguelService {

    void alugar(Cliente cliente, Veiculo veiculo);
    void devolver(Cliente cliente, Veiculo veiculo);
    Aluguel buscarAluguelPorVeiculo(Veiculo veiculo);
}
