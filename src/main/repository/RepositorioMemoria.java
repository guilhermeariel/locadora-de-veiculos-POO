package repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RepositorioMemoria<T, K> implements Repositorio<T, K> {

    protected List<T> lista = new ArrayList<>();

    public abstract K getIdentificador(T obj);

    @Override
    public void salvar(T obj) {
        lista.add(obj);
    }

    @Override
    public void atualizar(T obj) {
        K id = getIdentificador(obj);
        for (int i = 0; i < lista.size(); i++) {
            if (getIdentificador(lista.get(i)).equals(id)) {
                lista.set(i, obj);
                return;
            }
        }
    }

    @Override
    public T buscarPorIdentificador(K identificador) {
        for (T objeto : lista) {
            if (getIdentificador(objeto).equals(identificador)) {
                return objeto;
            }
        }

        return null;
    }

    @Override
    public List<T> getLista() {
        return Collections.unmodifiableList(lista);
    }

    @Override
    public void adicionarLista(List<T> novaLista) {
        this.lista.addAll(novaLista);
    }

    @Override
    public void limparLista() {
        lista.clear();
    }

    @Override
    public void removerItem(T item) {
        lista.remove(item);
    }
}
