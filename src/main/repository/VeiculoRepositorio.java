package repository;

import model.Veiculo;

public class VeiculoRepositorio extends RepositorioMemoria<Veiculo, String> {

    @Override
    public String getIdentificador(Veiculo veiculo) {
        return veiculo.getIdentificador();
    }

    public boolean existePlaca(String placa) {
        return lista.stream()
            .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));
    }
}
