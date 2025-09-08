package repository;

import java.util.List;

public interface Repositorio<T, K> {

    void salvar(T obj);
    void atualizar(T obj);
    Repositorio<T,K> filtrar(String campo, String valor);
    T buscarPorIdentificador(K identificador);
    List<T> listar();
    void adicionarLista(List<T> novaLista);
    void limpar();
}
