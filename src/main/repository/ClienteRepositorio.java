package repository;

import java.util.ArrayList;
import java.util.List;
import model.Aluguel;
import model.Cliente;
import model.Veiculo;

public class ClienteRepositorio extends RepositorioMemoria<Cliente, String> {

    @Override
    public String getIdentificador(Cliente cliente) {
        return cliente.getIdentificador();
    }

    public static class AluguelRepository {

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
                    aluguel.getDataFim() == null) {
                    return aluguel;
                }
            }
            return null;
        }

        public List<Aluguel> listar() {
            return new ArrayList<>(alugueis);
        }
    }
}
