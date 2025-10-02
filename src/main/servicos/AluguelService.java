package servicos;

import model.Cliente;
import model.Veiculo;

public interface AluguelService {

    void alugarVeiculo(Cliente cliente, Veiculo veiculo);
    void devolverVeiculo(Cliente cliente, Veiculo veiculo);
}
