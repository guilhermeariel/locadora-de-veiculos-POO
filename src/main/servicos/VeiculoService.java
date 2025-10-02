package servicos;

import model.TipoVeiculo;
import model.Veiculo;
import repository.VeiculoRepositorio;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoService {

    private final VeiculoRepositorio veiculoRepositorio;

    public VeiculoService(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }

    public void cadastrar(Veiculo veiculo) {
        if (veiculoRepositorio.existePlaca(veiculo.getPlaca())) {
            System.out.println("Erro: Já existe um veículo com essa placa.");
            return;
        }

        veiculoRepositorio.salvar(veiculo);
        System.out.println("Veículo cadastrado com sucesso.");
    }

    public void cadastrarVeiculo(String placa, TipoVeiculo tipo, String modelo) {
        Veiculo veiculo = new Veiculo(placa, tipo, modelo, true);
        if (veiculoRepositorio.existePlaca(veiculo.getPlaca())) {
            throw new IllegalArgumentException("Já existe um veículo com essa placa.");
        } else {
            veiculoRepositorio.salvar(veiculo);
        }

    }

    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepositorio.buscarPorIdentificador(placa);
    }

    public List<Veiculo> listarVeiculosAlugados() {
        return veiculoRepositorio.getLista().stream()
                .filter(v -> !v.isDisponivel())
                .collect(Collectors.toList());
    }

    public void removerVeiculo(String placa) {
        Veiculo veiculo = buscarPorPlaca(placa);
        if (veiculo != null) {
            veiculoRepositorio.removerItem(veiculo);
        }
    }

    public void alterar(Veiculo veiculo) {
        veiculoRepositorio.atualizar(veiculo);
        System.out.println("Veículo alterado com sucesso.");
    }

    public List<Veiculo> buscarPorNome(String nome) {
        return veiculoRepositorio.getLista().stream()
                .filter(v -> v.getModelo().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean validarDisponibilidade(Veiculo veiculo) {
        return veiculo.isDisponivel();
    }
}
