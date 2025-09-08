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
                    .forEach(filtrado::adicionar);
                break;
            case "veiculo":
                lista.stream()
                    .filter(a -> a.getVeiculo().getModelo().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::adicionar);
                break;
            case "data de aluguel":
                lista.stream()
                    .filter(a -> a.getDataInicio().toString().contains(valor))
                    .forEach(filtrado::adicionar);
                break;
            case "data de devolucao":
                lista.stream()
                    .filter(a -> a.getDataFim().toString().contains(valor))
                    .forEach(filtrado::adicionar);
                break;
            default:
                lista.forEach(filtrado::adicionar);
        }
        return filtrado;
    }
}
