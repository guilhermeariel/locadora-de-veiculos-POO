package repository;

import model.Aluguel;

public class AluguelRepositorio extends RepositorioMemoria<Aluguel, Integer> {

    @Override
    public Integer getIdentificador(Aluguel aluguel) {
        return aluguel.getIdentificador();
    }
}
