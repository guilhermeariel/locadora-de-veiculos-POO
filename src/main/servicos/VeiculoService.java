package servicos;

import model.Veiculo;

import java.util.List;

public interface VeiculoService {

    void cadastrarVeiculo(String placa, String modelo, String tipoVeiculoStr);
    void atualizarVeiculo(String placa, String novoTipoStr, Boolean novaDisponibilidade);
    List<Veiculo> buscarVeiculoPorModelo(String nome);
    Veiculo buscarVeiculoPorId(String placa);
    public boolean validarDisponibilidade(String placa);
}
