package repository;

import model.Veiculo;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoRepositorio extends RepositorioMemoria<Veiculo, String> {

    @Override
    public String getIdentificador(Veiculo veiculo) {
        return veiculo.getIdentificador();
    }

    public boolean existePlaca(String placa) {
        return lista.stream()
            .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));
    }

    @Override
    public VeiculoRepositorio filtrar(String filtro, String valor) {
        VeiculoRepositorio filtrado = new VeiculoRepositorio();
        switch (filtro.toLowerCase()) {
            case "placa":
                lista.stream()
                    .filter(v -> v.getPlaca().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            case "modelo":
                lista.stream()
                    .filter(v -> v.getModelo().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            case "tipo":
                lista.stream()
                    .filter(v -> v.getTipo().toString().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            default:
                lista.forEach(filtrado::salvar);
        }
        return filtrado;
    }

    public List<Veiculo> listarDisponiveis() {
        return lista.stream()
            .filter(Veiculo::isDisponivel)
            .collect(Collectors.toList());
    }
}
