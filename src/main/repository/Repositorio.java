package repository;

import java.util.List;

public interface Repositorio<T, K> {

    void salvar(T obj);
    void atualizar(T obj);
    T buscarPorIdentificador(K identificador);
    List<T> listar();
}
