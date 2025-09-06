package repository;

import model.Aluguel;
import model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class AluguelRepositorio {

  private List<Aluguel> alugueis = new ArrayList<>();

  public void salvar(Aluguel aluguel) {
    alugueis.add(aluguel);
  }

  public void atualizar(Aluguel aluguel) {
    for (int i = 0; i < alugueis.size(); i++) {
      if (alugueis.get(i).equals(aluguel)) {
        alugueis.set(i, aluguel);
        return;
      }
    }
  }

  public Aluguel buscarPorVeiculo(Veiculo veiculo) {
    for (Aluguel aluguel : alugueis) {
      if (aluguel.getVeiculo().equals(veiculo) &&
          aluguel.getDataFim() == null) {  // <-- CORRETO AQUI
        return aluguel;
      }
    }
    return null;
  }

  public List<Aluguel> listar() {
    return new ArrayList<>(alugueis);
  }
}
