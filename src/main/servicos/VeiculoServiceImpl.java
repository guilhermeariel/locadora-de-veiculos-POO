package servicos;

import model.TipoVeiculo;
import model.Veiculo;
import repository.VeiculoRepositorio;
import utils.ValidationPredicates;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepositorio veiculoRepositorio;

    // Function para criaçao de veículo
    private final Function<String[], Veiculo> criarVeiculo = args ->
            new Veiculo(args[0], TipoVeiculo.valueOf(args[1]), args[2], true);

    // Consumer para log de cadastro de veículo
    private final Consumer<Veiculo> imprimirCadastro = v ->
            System.out.printf("Veículo cadastrado com sucesso: %s (%s)%n", v.getModelo(), v.getPlaca());

    // Consumer para log de busca de veículo
    private final Consumer<Veiculo> imprimirBusca = v ->
            System.out.printf("Veículo encontrado: %s (%s)%n", v.getModelo(), v.getPlaca());

    // Consumer para log de remoçao de veículo
    private final Consumer<Veiculo> imprimirRemocao = v ->
            System.out.printf("Veículo removido: %s (%s)%n", v.getModelo(), v.getPlaca());

    public VeiculoServiceImpl(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }

    @Override
    public void cadastrar(Veiculo veiculo) {
        if (veiculoRepositorio.buscarPorIdentificador(veiculo.getPlaca()) != null) {
            System.out.println("Erro: Já existe um veículo com essa placa.");
            return;
        }

        veiculoRepositorio.salvar(veiculo);
        imprimirCadastro.accept(veiculo);
    }

    @Override
    public void cadastrarVeiculo(String placa, TipoVeiculo tipo, String modelo) {
        if (!ValidationPredicates.ehPlacaValida(placa)) {
            throw new IllegalArgumentException("Placa inválida");
        }

        if (veiculoRepositorio.buscarPorIdentificador(placa) != null) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com essa placa.");
        }

        Veiculo veiculo = criarVeiculo.apply(new String[]{placa, tipo.name(), modelo});

        veiculoRepositorio.salvar(veiculo);
        imprimirCadastro.accept(veiculo);
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        Veiculo veiculo = veiculoRepositorio.buscarPorIdentificador(placa);

        if (veiculo == null) throw new IllegalArgumentException("Veículo não encontrado.");

        imprimirBusca.accept(veiculo);

        return veiculo;
    }

    @Override
    public List<Veiculo> listarVeiculosAlugados() {
        return veiculoRepositorio.getLista().stream()
                .filter(v -> !v.isDisponivel())
                .collect(Collectors.toList());
    }

    @Override
    public void removerVeiculo(String placa) {
        Veiculo veiculo = buscarPorPlaca(placa);
        if (veiculo != null) {
            veiculoRepositorio.removerItem(veiculo);

            imprimirRemocao.accept(veiculo);
        }
    }
}
