package repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class RepositorioMemoria<T, K> implements Repositorio<T, K> {

    protected List<T> lista = new ArrayList<>();

    // Consumers padrão para logs
    protected Consumer<T> aoSalvar = t -> {};
    protected Consumer<T> aoAtualizar = t -> {};
    protected Consumer<T> aoRemover = t -> {};

    public abstract K getIdentificador(T obj);

    // Permite configurar os consumers externamente
    public void setAoSalvar(Consumer<T> aoSalvar) {
        this.aoSalvar = aoSalvar;
    }

    public void setAoAtualizar(Consumer<T> aoAtualizar) {
        this.aoAtualizar = aoAtualizar;
    }

    public void setAoRemover(Consumer<T> aoRemover) {
        this.aoRemover = aoRemover;
    }

    @Override
    public void salvar(T obj) {
        lista.add(obj);
        aoSalvar.accept(obj);
    }

    @Override
    public void atualizar(T obj) {
        Function<T, Boolean> atualizarFunc = item -> getIdentificador(item).equals(getIdentificador(obj));

        lista.stream()
                .filter(atualizarFunc::apply)
                .findFirst()
                .ifPresent(item -> {
                    lista.set(lista.indexOf(item), obj);
                    aoAtualizar.accept(obj);
                });
    }

    @Override
    public T buscarPorIdentificador(K identificador) {
        return lista.stream()
                .filter(item -> getIdentificador(item).equals(identificador))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> getLista() {
        return Collections.unmodifiableList(lista);
    }

    @Override
    public void adicionarLista(List<T> novaLista) {
        lista.addAll(novaLista);
    }

    @Override
    public void limparLista() {
        lista.clear();
    }

    @Override
    public void removerItem(T item) {
        boolean removido = lista.removeIf(i -> i.equals(item));
        if (removido) {
            aoRemover.accept(item);
        }
    }
}
