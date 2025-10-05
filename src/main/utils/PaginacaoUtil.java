package utils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginacaoUtil {

    /**
     * Retorna uma sublista paginada conforme os parâmetros.
     *
     * @param lista       lista original
     * @param pagina      número da página (1-based)
     * @param itensPorPagina quantidade de itens por página
     * @param campo       função que extrai o campo de ordenação
     * @param ascendente  true para ordem crescente, false para decrescente
     * @param <T>         tipo da lista
     * @param <U>         tipo do campo de ordenação (Comparable)
     * @return lista paginada
     */
    public static <T, U extends Comparable<? super U>> List<T> paginar(
            List<T> lista,
            int pagina,
            int itensPorPagina,
            Function<T, U> campo,
            boolean ascendente
    ) {
        if (lista == null || lista.isEmpty() || pagina < 1 || itensPorPagina < 1) {
            return List.of();
        }

        Comparator<T> comparator = Comparator.comparing(campo);
        if (!ascendente) {
            comparator = comparator.reversed();
        }

        return lista.stream()
                .sorted(comparator)
                .skip((long) (pagina - 1) * itensPorPagina)
                .limit(itensPorPagina)
                .collect(Collectors.toList());
    }
}
