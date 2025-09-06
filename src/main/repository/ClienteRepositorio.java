package repository;

import model.Cliente;

public class ClienteRepositorio extends RepositorioMemoria<Cliente, String> {

    @Override
    public String getIdentificador(Cliente cliente) {
        return cliente.getIdentificador();
    }
}
