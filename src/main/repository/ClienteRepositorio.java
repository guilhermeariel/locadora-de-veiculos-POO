package repository;

import model.Cliente;

public class ClienteRepositorio extends RepositorioMemoria<Cliente, String> {

    @Override
    public String getIdentificador(Cliente cliente) {
        return cliente.getIdentificador();
    }

    @Override
    public ClienteRepositorio filtrar(String campo, String valor) {
        ClienteRepositorio filtrado = new ClienteRepositorio();
        switch (campo.toLowerCase()) {
            case "nome":
                lista.stream()
                    .filter(c -> c.getNome().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            case "documento":
                lista.stream()
                    .filter(c -> c.getIdentificador().toLowerCase().contains(valor.toLowerCase()))
                    .forEach(filtrado::salvar);
                break;
            default:
                lista.forEach(filtrado::salvar);
        }
        return filtrado;
    }
}
