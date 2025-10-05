package repository;

import model.Veiculo;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VeiculoRepositorio extends RepositorioMemoria<Veiculo, String> {

    public VeiculoRepositorio() {
        setAoSalvar(v -> System.out.printf("Veículo salvo: %s (%s)%n", v.getModelo(), v.getPlaca()));
        setAoRemover(v -> System.out.printf("Veículo removido: %s (%s)%n", v.getModelo(), v.getPlaca()));
        setAoAtualizar(v -> System.out.printf("Veículo atualizado: %s (%s)%n", v.getModelo(), v.getPlaca()));
    }

    @Override
    public String getIdentificador(Veiculo veiculo) {
        return veiculo.getIdentificador();
    }

    @Override
    public VeiculoRepositorio filtrar(String campo, String valor) {
        VeiculoRepositorio filtrado = new VeiculoRepositorio();

        Predicate<Veiculo> filtro = switch (campo.toLowerCase()) {
            case "placa" -> v -> v.getPlaca().toLowerCase().contains(valor.toLowerCase());
            case "modelo" -> v -> v.getModelo().toLowerCase().contains(valor.toLowerCase());
            case "tipo" -> v -> v.getTipo().toString().toLowerCase().contains(valor.toLowerCase());
            default -> _ -> true;
        };

        lista.stream().filter(filtro).forEach(filtrado::salvar);
        return filtrado;
    }

    public List<Veiculo> listarDisponiveis() {
        return lista.stream()
            .filter(Veiculo::isDisponivel)
            .collect(Collectors.toList());
    }
}
