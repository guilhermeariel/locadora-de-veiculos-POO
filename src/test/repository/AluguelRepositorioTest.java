package repository;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PaginacaoUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;
import java.util.stream.Stream;

class AluguelRepositorioTest {

    private AluguelRepositorio aluguelRepo;
    private Cliente cliente;
    private Veiculo veiculo;
    private Supplier<Aluguel> aluguelSupplier;

    @BeforeEach
    void setup() {
        aluguelRepo = new AluguelRepositorio();
        cliente = new PessoaFisica("Paula", "11111111111");
        veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);

        aluguelSupplier = () -> new Aluguel(
                cliente,
                veiculo,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );
    }

    @Test
    void when_SalvarAluguel_Then_AluguelExistenteNaLista() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        assertEquals(1, aluguelRepo.getLista().size());
        assertEquals(aluguel, aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_AtualizarAluguelExistente_Then_AluguelAtualizado() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        LocalDateTime novaDataFim = LocalDateTime.now().plusDays(2);
        aluguel.setDataFim(novaDataFim);
        aluguelRepo.atualizar(aluguel);

        Aluguel buscado = aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador());
        assertEquals(novaDataFim, buscado.getDataFim());
    }

    @Test
    void when_AtualizarAluguelNaoExistente_Then_NaoAlteraLista() {
        Aluguel aluguel = aluguelSupplier.get();
        int tamanhoAntes = aluguelRepo.getLista().size();

        aluguelRepo.atualizar(aluguel);

        assertEquals(tamanhoAntes, aluguelRepo.getLista().size());
        assertNull(aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaAluguelCorreto() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        Aluguel aluguelBuscado = aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador());
        assertNotNull(aluguelBuscado);
        assertEquals("Paula", aluguelBuscado.getCliente().getNome());
    }

    @Test
    void when_BuscarPorIdentificadorNaoExistente_Then_RetornaNull() {
        Aluguel aluguelBuscado = aluguelRepo.buscarPorIdentificador(999);
        assertNull(aluguelBuscado);
    }

    @Test
    void when_getListaAlugueis_Then_RetornaListaComTodos() {
        Aluguel aluguel1 = aluguelSupplier.get();
        Aluguel aluguel2 = aluguelSupplier.get();

        aluguelRepo.salvar(aluguel1);
        aluguelRepo.salvar(aluguel2);

        assertEquals(2, aluguelRepo.getLista().size());
        assertTrue(aluguelRepo.getLista().containsAll(List.of(aluguel1, aluguel2)));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        Aluguel aluguel = aluguelSupplier.get();
        assertEquals(aluguel.getIdentificador(), aluguelRepo.getIdentificador(aluguel));
    }

    @Test
    void when_RemoverItemExistente_Then_ItemNaoEstaMaisNaLista() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        aluguelRepo.removerItem(aluguel);

        assertFalse(aluguelRepo.getLista().contains(aluguel));
        assertNull(aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_RemoverItemNaoExistente_Then_ListaPermaneceInalterada() {
        Aluguel aluguel = aluguelSupplier.get();
        int tamanhoAntes = aluguelRepo.getLista().size();

        aluguelRepo.removerItem(aluguel);

        assertEquals(tamanhoAntes, aluguelRepo.getLista().size());
    }

    @Test
    void when_LimparLista_Then_ListaFicaVazia() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        aluguelRepo.limparLista();

        assertTrue(aluguelRepo.getLista().isEmpty());
    }

    @Test
    void when_AdicionarListaDeAlugueis_Then_TodosSaoInseridos() {
        Aluguel aluguel1 = aluguelSupplier.get();
        Aluguel aluguel2 = aluguelSupplier.get();

        aluguelRepo.adicionarLista(List.of(aluguel1, aluguel2));

        assertTrue(aluguelRepo.getLista().containsAll(List.of(aluguel1, aluguel2)));
    }

    @Test
    void when_FiltrarPorCliente_Then_RetornaSomenteAlugueisDoCliente() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("cliente", "Paula");

        assertEquals(1, resultado.getLista().size());
        assertEquals(aluguel, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorVeiculo_Then_RetornaSomenteAlugueisDoVeiculo() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("veiculo", "ABC1234");

        assertEquals(1, resultado.getLista().size());
        assertEquals(aluguel, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarComCampoInvalido_Then_RetornaTodos() {
        Aluguel aluguel1 = aluguelSupplier.get();
        Aluguel aluguel2 = aluguelSupplier.get();

        aluguelRepo.salvar(aluguel1);
        aluguelRepo.salvar(aluguel2);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("invalido", "teste");

        assertEquals(2, resultado.getLista().size());
        assertTrue(resultado.getLista().containsAll(List.of(aluguel1, aluguel2)));
    }

    @Test
    void when_BuscarPorItemClienteExistente_Then_RetornaAluguel() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        Aluguel resultado = aluguelRepo.buscarPorItem(cliente, "cliente");

        assertEquals(aluguel, resultado);
    }

    @Test
    void when_BuscarPorItemVeiculoExistente_Then_RetornaAluguel() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        Aluguel resultado = aluguelRepo.buscarPorItem(veiculo, "veiculo");

        assertEquals(aluguel, resultado);
    }

    @Test
    void when_BuscarPorItemInvalido_Then_RetornaNull() {
        Aluguel resultado = aluguelRepo.buscarPorItem(cliente, "invalido");
        assertNull(resultado);
    }

    // --------------------------
    // Testes dos Consumers
    // --------------------------

    @Test
    void when_SalvarAluguel_Then_ConsumerAoSalvarEhExecutado() {
        StringBuilder log = new StringBuilder();
        aluguelRepo.setAoSalvar(a -> log.append("salvo:").append(a.getIdentificador()));

        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        assertTrue(log.toString().contains(String.valueOf(aluguel.getIdentificador())));
    }

    @Test
    void when_AtualizarAluguel_Then_ConsumerAoAtualizarEhExecutado() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        StringBuilder log = new StringBuilder();
        aluguelRepo.setAoAtualizar(a -> log.append("atualizado:").append(a.getIdentificador()));

        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguelRepo.atualizar(aluguel);

        assertTrue(log.toString().contains(String.valueOf(aluguel.getIdentificador())));
    }

    @Test
    void when_RemoverAluguel_Then_ConsumerAoRemoverEhExecutado() {
        Aluguel aluguel = aluguelSupplier.get();
        aluguelRepo.salvar(aluguel);

        StringBuilder log = new StringBuilder();
        aluguelRepo.setAoRemover(a -> log.append("removido:").append(a.getIdentificador()));

        aluguelRepo.removerItem(aluguel);

        assertTrue(log.toString().contains(String.valueOf(aluguel.getIdentificador())));
    }

    // ------------------------------
    // Teste de Paginaçao e Ordenaçao
    // ------------------------------

    @Test
    void when_BuscarAlugueisPaginadosEOrdenados_PaginacaoFuncionaCorretamente() {
        // Supplier de clientes
        Supplier<Aluguel> aluguelSupplier = getAluguelSupplier();

        // Cria 10 aluguéis de teste
        List<Aluguel> listaDeTeste = Stream.generate(aluguelSupplier)
                .limit(10)
                .toList();

        // Página 1, 3 itens por página, ascendente (data início)
        List<Aluguel> pagina1 = PaginacaoUtil.paginar(listaDeTeste, 1, 3, Aluguel::getDataInicio, true);
        assertEquals(3, pagina1.size());
        assertEquals(listaDeTeste.getFirst().getCliente().getNome(), pagina1.getFirst().getCliente().getNome());

        // Página 2, 3 itens por página, ascendente
        List<Aluguel> pagina2 = PaginacaoUtil.paginar(listaDeTeste, 2, 3, Aluguel::getDataInicio, true);
        assertEquals(3, pagina2.size());
        assertEquals(listaDeTeste.get(3).getCliente().getNome(), pagina2.getFirst().getCliente().getNome());

        // Página 4, 3 itens por página (última página com 1 item), ascendente
        List<Aluguel> pagina4 = PaginacaoUtil.paginar(listaDeTeste, 4, 3, Aluguel::getDataInicio, true);
        assertEquals(1, pagina4.size());
        assertEquals(listaDeTeste.get(9).getCliente().getNome(), pagina4.getFirst().getCliente().getNome());

        // Página 1, descendente (total 10 itens), descendente
        List<Aluguel> desc = PaginacaoUtil.paginar(listaDeTeste, 1, 3, Aluguel::getDataInicio, false);
        assertEquals(3, desc.size());
        assertEquals(listaDeTeste.get(9).getCliente().getNome(), desc.getFirst().getCliente().getNome());
    }

    private static Supplier<Aluguel> getAluguelSupplier() {
        Supplier<Cliente> clienteSupplier = new Supplier<>() {
            private int contador = 1;
            @Override
            public Cliente get() {
                String nome = String.format("Cliente%02d", contador);
                String cpf = String.format("%011d", contador++);
                return new PessoaFisica(nome, cpf);
            }
        };

        // Supplier de veículos
        Supplier<Veiculo> veiculoSupplier = new Supplier<>() {
            private int contador = 1;
            @Override
            public Veiculo get() {
                String placa = String.format("AAA%04d", contador);
                String modelo = "Modelo" + contador++;
                return new Veiculo(placa, TipoVeiculo.HATCH, modelo, true);
            }
        };

        // Supplier de aluguéis
        return new Supplier<>() {
            private int contador = 0;
            @Override
            public Aluguel get() {
                Cliente cliente1 = clienteSupplier.get();
                Veiculo veiculo1 = veiculoSupplier.get();
                LocalDateTime inicio = LocalDateTime.now().plusDays(contador);
                LocalDateTime fim = inicio.plusDays(1);
                contador++;
                return new Aluguel(cliente1, veiculo1, inicio, fim);
            }
        };
    }
}
