package servicos;

import model.TipoVeiculo;
import model.Veiculo;

import java.util.List;

public interface VeiculoService {

    void cadastrar(Veiculo veiculo);
    void cadastrarVeiculo(String placa, TipoVeiculo tipo, String modelo);
    Veiculo buscarPorPlaca(String placa);
    List<Veiculo> listarVeiculosAlugados();
    void removerVeiculo(String placa);
}
