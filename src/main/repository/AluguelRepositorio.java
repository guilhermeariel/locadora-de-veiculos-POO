package repository;

import model.Aluguel;

import java.util.function.Predicate;

public class AluguelRepositorio extends RepositorioMemoria<Aluguel, Integer> {

    public AluguelRepositorio() {
        setAoSalvar(a ->
                System.out.printf("Aluguel salvo: %s - %s%n", a.getCliente().getNome(), a.getVeiculo().getIdentificador()));
        setAoRemover(a ->
                System.out.printf("Aluguel removido: %s - %s%n", a.getCliente().getNome(), a.getVeiculo().getIdentificador()));
        setAoAtualizar(a ->
                System.out.printf("Aluguel atualizado: %s - %s%n", a.getCliente().getNome(), a.getVeiculo().getIdentificador()));
    }

    @Override
    public Integer getIdentificador(Aluguel obj) {
        return obj.getIdentificador();
    }

    @Override
    public Repositorio<Aluguel, Integer> filtrar(String campo, String valor) {
        AluguelRepositorio filtrado = new AluguelRepositorio();

        Predicate<Aluguel> filtro = switch (campo.toLowerCase()) {
            case "cliente" -> a -> a.getCliente().getNome().toLowerCase().contains(valor.toLowerCase());
            case "veiculo" -> a -> a.getVeiculo().getIdentificador().toLowerCase().contains(valor.toLowerCase());
            case "data de aluguel" -> a -> a.getDataInicio().toString().contains(valor);
            case "data de devolucao" -> a -> a.getDataFim() != null && a.getDataFim().toString().contains(valor);
            default -> _ -> true;
        };

        lista.stream().filter(filtro).forEach(filtrado::salvar);
        return filtrado;
    }

    public Aluguel buscarPorItem(Object obj, String campo) {
        return switch (campo.toLowerCase()) {
            case "cliente" -> lista.stream()
                    .filter(a -> a.getCliente().equals(obj))
                    .findFirst().orElse(null);
            case "veiculo" -> lista.stream()
                    .filter(a -> a.getVeiculo().equals(obj))
                    .findFirst().orElse(null);
            default -> null;
        };
    }
}
