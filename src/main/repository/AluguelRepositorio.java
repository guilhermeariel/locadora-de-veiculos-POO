package repository;

import model.Aluguel;

public class AluguelRepositorio extends RepositorioMemoria<Aluguel, Integer> {

    @Override
    public Integer getIdentificador(Aluguel obj) {
        return obj.getIdentificador();
    }

    @Override
    public Repositorio<Aluguel, Integer> filtrar(String campo, String valor) {
        AluguelRepositorio filtrado = new AluguelRepositorio();
        switch (campo.toLowerCase()) {
            case "cliente":
                lista.stream()
                    .filter(a -> a.getCliente().getNome().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            case "veiculo":
                lista.stream()
                    .filter(a -> a.getVeiculo().getIdentificador().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            case "data de aluguel":
                lista.stream()
                    .filter(a -> a.getDataInicio().toString().contains(valor))
                    .forEach(filtrado::salvar);
                break;
            case "data de devolucao":
                lista.stream()
                    .filter(a -> a.getDataFim().toString().contains(valor))
                    .forEach(filtrado::salvar);
                break;
            default:
                lista.forEach(filtrado::salvar);
        }
        return filtrado;
    }

    public Aluguel buscarPorItem(Object obj, String campo) {
        return switch (campo.toLowerCase()) {
            case "cliente" -> lista.stream()
                    .filter(a -> a.getCliente().equals(obj))
                    .findFirst()
                    .orElse(null);
            case "veiculo" -> lista.stream()
                    .filter(a -> a.getVeiculo().equals(obj))
                    .findFirst()
                    .orElse(null);
            default -> null;
        };
    }
}
