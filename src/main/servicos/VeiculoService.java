package servicos;

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

  public void alterar(Veiculo veiculo) {
    veiculoRepositorio.atualizar(veiculo);
    System.out.println("Veículo alterado com sucesso.");
  }

  public List<Veiculo> buscarPorNome(String nome) {
    return veiculoRepositorio.listar().stream()
        .filter(v -> v.getModelo().toLowerCase().contains(nome.toLowerCase()))
        .collect(Collectors.toList());
  }

  public boolean validarDisponibilidade(Veiculo veiculo) {
    return veiculo.isDisponivel();
  }
}
