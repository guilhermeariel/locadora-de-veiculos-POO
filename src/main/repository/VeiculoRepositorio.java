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

    public VeiculoRepositorio filtrar(String filtro, String valor) {
        VeiculoRepositorio filtrado = new VeiculoRepositorio();
        switch (filtro.toLowerCase()) {
            case "placa":
                lista.stream()
                    .filter(v -> v.getPlaca().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::adicionar);
                break;
            case "modelo":
                lista.stream()
                    .filter(v -> v.getModelo().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::adicionar);
                break;
            case "tipo":
                lista.stream()
                    .filter(v -> v.getTipo().toString().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::adicionar);
                break;
            default:
                lista.forEach(filtrado::adicionar);
        }
        return filtrado;
    }
}
