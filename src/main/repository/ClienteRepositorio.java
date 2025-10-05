package repository;

import model.Cliente;

import java.util.function.Predicate;

public class ClienteRepositorio extends RepositorioMemoria<Cliente, String> {

    public ClienteRepositorio() {
        setAoSalvar(c -> System.out.printf("Cliente salvo: %s (%s)%n", c.getNome(), c.getIdentificador()));
        setAoRemover(c -> System.out.printf("Cliente removido: %s (%s)%n", c.getNome(), c.getIdentificador()));
        setAoAtualizar(c -> System.out.printf("Cliente atualizado: %s (%s)%n", c.getNome(), c.getIdentificador()));
    }

    @Override
    public String getIdentificador(Cliente cliente) {
        return cliente.getIdentificador();
    }

    @Override
    public ClienteRepositorio filtrar(String campo, String valor) {
        ClienteRepositorio filtrado = new ClienteRepositorio();

        Predicate<Cliente> filtro = switch (campo.toLowerCase()) {
            case "nome" -> c -> c.getNome().toLowerCase().contains(valor.toLowerCase());
            case "documento" -> c -> c.getIdentificador().toLowerCase().contains(valor.toLowerCase());
            default -> _ -> true;
        };

        lista.stream().filter(filtro).forEach(filtrado::salvar);
        return filtrado;
    }
}
